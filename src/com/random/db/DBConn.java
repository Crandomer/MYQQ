package com.random.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    public static String driver="com.mysql.cj.jdbc.Driver";
    public static String url="jdbc:mysql://localhost:3306/qq2016?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static String username="root";
    public static String password="258001";
    public static Connection conn=null;
    //静态代码块连接数据库
    static {
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,username,password);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数据库连接成功");

    }

    //连接数据库
    public static Connection openDB(){
        try {
            if(conn.isClosed())
            {
                conn= DriverManager.getConnection(url,username,password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }


}
