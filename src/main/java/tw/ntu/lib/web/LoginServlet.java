package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import tw.ntu.lib.model.History;
import tw.ntu.lib.model.PasswordUtils;
import tw.ntu.lib.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // Read user file
        String path = getServletContext().getRealPath("/WEB-INF/classes/user.json");
        try {
            Gson gson = new Gson();
            BufferedReader buffer = new BufferedReader(new FileReader(path));
            Type type = new TypeToken<List<User>>(){}.getType();
            List<User> userList = gson.fromJson(buffer, type);

            boolean status = false;
            String info = "User name or password is error!";
            for (User user: userList) {
                if(userName.equals(user.getUserName())){
                    boolean passwordMatch = PasswordUtils.verifyUserPassword(password, user.getPassword(), user.getSalt());
                    if(passwordMatch) {
                        System.out.println("Provided user password " + password + " is correct.");
                        HttpSession session = request.getSession();
                        session.setAttribute("userName", user.getUserName());
                        session.setAttribute("permission", user.getPermission());

                        status = true;
                        info  = "Hello, " + userName;
                    }
                }
            } // end for-loop

            JsonObject result = new JsonObject();
            result.addProperty("status", status);
            result.addProperty("info", info);

            response.getWriter().write(result.toString());
        } catch (FileNotFoundException e) {
            System.out.println("user.json is not found");
        }
    }

}
