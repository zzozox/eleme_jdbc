package com.eleme.dao;
import com.eleme.po.Admin;
public interface AdminDao {
    public Admin getAdminByNameByPass(String adminName,String password);
}
