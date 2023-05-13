package com.eleme.view.impl;

import com.eleme.dao.AdminDao;
import com.eleme.dao.impl.AdminDaoImpl;
import com.eleme.po.Admin;
import com.eleme.view.AdminView;

import java.util.Scanner;

public class AdminVewImpl implements AdminView {
    private Scanner input = new Scanner(System.in);
    @Override
    public Admin login() {
        System.out.println("请输入管理员名称：");
        String adminName = input.next();
        System.out.println("请输入密码：");
        String password = input.next();

        AdminDao dao = new AdminDaoImpl();
        return dao.getAdminByNameByPass(adminName, password);
    }
}