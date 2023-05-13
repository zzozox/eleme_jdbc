package com.eleme.util;

import java.sql.*;

public class DBUtil {
    // 根据配置文件返回连接
    public static Connection getConnection(){
        Connection conn=null;
        try{
            Class.forName(ConfigUtil.getProp("driver"));
            conn= DriverManager.getConnection(ConfigUtil.getProp("url"),ConfigUtil.getProp("user"),ConfigUtil.getProp("password"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  conn;
    }
    //关闭连接：resultSet preparedStatement Connection
    //preparedStatement防止依赖注入
    public static void closeConnection(ResultSet rs,PreparedStatement pst, Connection con){
        if(rs!=null){
            try{
                rs.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(pst!=null){
            try{
                pst.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(con!=null){
            try{
                con.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
