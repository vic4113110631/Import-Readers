package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import tw.ntu.lib.model.PasswordUtils;
import tw.ntu.lib.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // Read user file
        String path = getServletContext().getRealPath("/WEB-INF/classes/user.json");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();;
            BufferedReader buffer = new BufferedReader(new FileReader(path));
            Type type = new TypeToken<List<User>>(){}.getType();
            List<User> userList = gson.fromJson(buffer, type);

            boolean status = true;
            String info = "";
            for (User user: userList) {
                if(userName.equals(user.getUserName())){
                    info = "User Name 已經存在!";
                    status = false;
                }
            } // end for-loop

            // Generate user
            if(status){
                User user = new User();

                String salt = PasswordUtils.getSalt(30);
                String securePassword = PasswordUtils.generateSecurePassword(password, salt);

                user.setUserName(userName);
                user.setSalt(salt);
                user.setPassword(securePassword);
                user.setPermission("user");

                userList.add(user);

                // Write to file
                try (Writer writer = new FileWriter(path)) {
                    gson.toJson(userList, writer);
                    info = "success new a user!";
                } catch (IOException e) {
                    info = ("Can't write in user file!");
                    userList.remove(user);
                }
            }

            JsonObject result = new JsonObject();
            result.addProperty("status", status);
            result.addProperty("info", info);

            response.getWriter().write(result.toString());
        } catch (FileNotFoundException e) {
            System.out.println("user.json is not found");
        }
    }

}
