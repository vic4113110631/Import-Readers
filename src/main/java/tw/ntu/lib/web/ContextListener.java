package tw.ntu.lib.web;

import tw.ntu.lib.model.DataBase;
import tw.ntu.lib.model.HibernateUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import java.net.MalformedURLException;
import java.sql.SQLException;

@WebServlet(name = "ContextListener")
public class ContextListener implements ServletContextListener {
    private static ServletContext servletContext;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();

        DataBase db = new DataBase();
        getServletContext().setAttribute("database", db);

        System.out.println(getServletContext().getClassLoader().getResource("history.json"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DataBase db = (DataBase) getServletContext().getAttribute("database");

        try {
            db.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ServletContext getServletContext(){
        return servletContext;
    }
}
