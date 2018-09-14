package tw.ntu.lib.Model;

import tw.ntu.lib.web.ContextListener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {
    private Connection conn = null;

    public DataBase(){
        Properties property = new Properties();
        try {
            // Load db file
            property.load(ContextListener.getServletContext().getResourceAsStream( "/WEB-INF/db.properties"));

            // JDBC driver name and database URL
            String URL = property.getProperty("url");
            String JDBC_DRIVER = property.getProperty("driver");

            //  Database credentials
            String USER = property.getProperty("user");
            String PASS = property.getProperty("password");


            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(URL, USER, PASS);
            if(!conn.isClosed())
                System.out.println("資料庫連線成功");
        }catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

}
