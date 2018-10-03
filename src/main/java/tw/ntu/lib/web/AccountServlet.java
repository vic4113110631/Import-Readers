package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tw.ntu.lib.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "AccountServlet")
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read user file
        String path = getServletContext().getRealPath("/WEB-INF/classes/user.json");
        try {
            Gson gson = new Gson();
            BufferedReader buffer = new BufferedReader(new FileReader(path));
            Type type = new TypeToken<List<User>>(){}.getType();
            List<User> userList = gson.fromJson(buffer, type);

            userList.removeIf(user -> user.getPermission().equals("admin"));
            request.setAttribute("userList", userList);

            RequestDispatcher view = request.getRequestDispatcher("account.jsp");
            view.forward(request,response);

        } catch (FileNotFoundException e) {
            System.out.println("user.json is not found");
        }
    }
}
