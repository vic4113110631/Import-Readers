package tw.ntu.lib;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "InsertFileServlet")
public class InsertFileServlet extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://140.112.114.60:3306?useUnicode=true" +
                                                            "&characterEncoding=utf" +
                                                            "8&serverTimezone=GMT" +
                                                            "&useSSL=false"+
                                                            "&allowPublicKeyRetrieval=true";
    //  Database credentials
    static final String USER = "patron_test";
    static final String PASS = "dbTest@4.60";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Open a connection
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
