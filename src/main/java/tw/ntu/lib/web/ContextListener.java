package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tw.ntu.lib.model.DataBase;

import tw.ntu.lib.model.History;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ContextListener")
public class ContextListener implements ServletContextListener {
    private static ServletContext servletContext;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();

        // Saving DB connection
        DataBase db = new DataBase();
        getServletContext().setAttribute("database", db);

        // Read histroy file
        String historyPath = getServletContext().getRealPath("/WEB-INF/classes/history.json");
        try {
            Gson gson = new Gson();
            BufferedReader buffer = new BufferedReader(new FileReader(historyPath));
            Type type = new TypeToken<List<History>>(){}.getType();
            List<History> historyList = gson.fromJson(buffer, type);
            getServletContext().setAttribute("history", historyList);
        } catch (FileNotFoundException e) {
            System.out.println("history.json is not found");
        }


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
