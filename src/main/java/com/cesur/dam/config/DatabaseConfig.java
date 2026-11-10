package com.cesur.dam.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class DatabaseConfig {

    private static MysqlDataSource dataSource;

    private DatabaseConfig() {
    }

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null) {
                url = "jdbc:mysql://localhost:3307/ad";
            }
            if (user == null) {
                user = "root";
            }
            if (password == null) {
                password = "root";
            }

            dataSource = new MysqlDataSource();
            dataSource.setURL(url);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        }
        return dataSource;
    }
}