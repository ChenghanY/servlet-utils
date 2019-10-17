package com.james.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JDBCUtil {
    private static DataSource dataSource;
    static {
        //通过工厂类创建Druid连接池
        Properties properties = new Properties();
        //加载配置文件
        InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("druidconfig.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection conn, Statement stm, ResultSet rst) throws SQLException {

        if (rst != null) {
            rst.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public static void close(Connection conn, Statement stm) throws SQLException {
        close(conn,stm,null);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
