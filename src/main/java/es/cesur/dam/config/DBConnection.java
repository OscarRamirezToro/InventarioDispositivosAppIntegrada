package es.cesur.dam.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3310/ad";
    private static MysqlDataSource dataSourceInstance;

    private DBConnection() {}

    public static synchronized DataSource getDataSource() {
        if (dataSourceInstance == null) {
            String user = System.getenv("USER");
            String pass = System.getenv("PASSWORD");

            if (user == null || pass == null) {
                user = "root";
                pass = "root";
            }

            dataSourceInstance = new MysqlDataSource();
            dataSourceInstance.setURL(URL);
            dataSourceInstance.setUser(user);
            dataSourceInstance.setPassword(pass);
        }
        return dataSourceInstance;
    }
}