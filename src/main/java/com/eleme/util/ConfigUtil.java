package com.eleme.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties conf=new Properties();
    //静态代码块加载时自动运行，且该文件只运行一次
    static{
        //获取配置文件
        InputStream in=ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try{
            conf.load(in);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String getProp(String key){
        return conf.getProperty(key);
    }
}
