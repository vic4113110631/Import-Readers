package tw.ntu.lib.web;

import tw.ntu.lib.model.DataBase;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ContextListener")
public class ContextListener implements ServletContextListener {
    private static ServletContext servletContext;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();

        DataBase db = new DataBase();
        getServletContext().setAttribute("database", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public static ServletContext getServletContext(){
        return servletContext;
    }
}
