package com.eleme.dao.impl;

import com.eleme.dao.BusinessDao;
import com.eleme.po.Business;
import com.eleme.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessDaoImpl implements BusinessDao {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;


    //用户可以输入businessName或者businessAddress来查询商家
    @Override
    public List<Business> listBusiness(String businessName, String businessAddress) {
        List<Business> list = new ArrayList<>();
        //定义两个布尔型变量标志用户书否输入商家名和地址
//        boolean enableName =false;
//        boolean enableAddress=false;
        //"1=1"防备两个值都不输入
        String sqlTemplate = "select * from business where 1=1";
        if (businessName != null && !"".equals(businessName)) {
            //模糊查询
            sqlTemplate = sqlTemplate + "and businessName like '%'+businessName+'%'";
//            enableName=true;
        }
        if (businessAddress != null && !"".equals(businessAddress)) {
            //模糊查询
            sqlTemplate = sqlTemplate + "and businessAddresslike '%'+businessAddress+'%'";
//            enableAddress=true;
        }
        try {
            con = DBUtil.getConnection();
            pst = con.prepareStatement(sqlTemplate);
//            if(enableName&&enableAddress){
//                pst.setString(1,businessName);
//                pst.setString(2,businessAddress);
//            }
//            else if(enableName&&!enableAddress){
//                pst.setString(1,businessName);
//            }
//            else if(!enableName&&enableAddress){
//                pst.setString(1,businessAddress);
//            }
            rs = pst.executeQuery();
            while (rs.next()) {
                Business business = new Business();
                business.setBusinessId(rs.getInt("businessId"));
                business.setBusinessName(rs.getString("businessName"));
                business.setPassword(rs.getString("password"));
                business.setBusinessAddress(rs.getString("businessAddress"));
                business.setBusinessExplain(rs.getString("businessExplain"));
                business.setStarPrice(rs.getDouble("starPrice"));
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
                list.add(business);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return list;
    }

    @Override
    public int saveBusiness(String businessName) {
        //保留初值,返回插入的用户的ID
        int businessId = 0;
        String sql = "inser into business(businessName,password) values (?,'123')";
        try {
            con = DBUtil.getConnection();
            //设置返回自增长列值
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //防止SQL注入
            pst.setString(1, businessName);
            pst.executeUpdate();
            //获取自增长列值（一行一列）
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                businessId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return businessId;
    }

    @Override
    public int removeBusiness(int businessId) {
        //为删除失败，为1删除呈贡成功
        //或者把result换成布尔型
        //boolean result=false;
        int result = 0;
        String delFootSql = "delete from food where businessId=?";
        String delBusinessSql = "delete from business where businessId=?";
        try {
            //创立连接
            con = DBUtil.getConnection();
            //开启一个事务
            con.setAutoCommit(false);

            //删除对应商家的食物
            pst = con.prepareStatement(delFootSql);
            pst.setInt(1, businessId);
            pst.executeUpdate();

            //删除商家
            pst = con.prepareStatement(delBusinessSql);
            pst.setInt(1, businessId);
            pst.executeUpdate();

            con.commit();
            //布尔型的result=true
            result=1;
        } catch (SQLException e) {
            //回滚
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return result;
    }

    @Override
    public Business getBusinessByIdByPass(Integer businessId, String password) {
        Business business = null;
        String sql = "select * from business where businessId=? and password=?";
        try {
            con = DBUtil.getConnection();
            pst = con.prepareStatement(sql.toString());
            pst.setInt(1, businessId);
            pst.setString(2, password);
            rs = pst.executeQuery();
            while (rs.next()) {
                business = new Business();
                business.setBusinessId(rs.getInt("businessId"));
                business.setPassword(rs.getString("password"));
                business.setBusinessName(rs.getString("businessName"));
                business.setBusinessAddress(rs.getString("businessAddress"));
                business.setBusinessExplain(rs.getString("businessExplain"));
                business.setStarPrice(rs.getDouble("starPrice"));
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return business;
    }

    @Override
    public Business getBusinessById(Integer businessId) {
        Business business = null;
        String sql = "select * from business where businessId=?";
        try {
            con = DBUtil.getConnection();
            pst = con.prepareStatement(sql.toString());
            pst.setInt(1, businessId);
            rs = pst.executeQuery();
            while (rs.next()) {
                business = new Business();
                business.setBusinessId(rs.getInt("businessId"));
                business.setPassword(rs.getString("password"));
                business.setBusinessName(rs.getString("businessName"));
                business.setBusinessAddress(rs.getString("businessAddress"));
                business.setBusinessExplain(rs.getString("businessExplain"));
                business.setStarPrice(rs.getDouble("starPrice"));
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return business;
    }

    @Override
    public int updateBusiness(Business business) {
        int result = 0;
        String sql = "update business set businessName=?,businessAddress=?,businessExplain=?,starPrice=?,deliveryPrice=? where businessId=?";
        try {
            con = DBUtil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, business.getBusinessName());
            pst.setString(2, business.getBusinessAddress());
            pst.setString(3, business.getBusinessExplain());
            pst.setDouble(4, business.getStarPrice());
            pst.setDouble(5, business.getDeliveryPrice());
            pst.setInt(6, business.getBusinessId());
            pst.executeUpdate();
            result=1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return result;
    }

    @Override
    public int updateBusinessByPassword(Integer businessId, String password) {
        int result = 0;
        String sql = "update business set password=? where businessId=?";
        try {
            con = DBUtil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, password);
            pst.setInt(2, businessId);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs, pst, con);
        }
        return result;
    }
}
