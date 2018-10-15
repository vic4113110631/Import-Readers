package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.time.DateUtils;
import tw.ntu.lib.model.History;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "HistoryServlet")
public class HistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        String start = request.getParameter("start");
        String end = request.getParameter("end");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = format.parse(start);
            Date endDate  = format.parse(end);

            // 取得當日最大值
            Calendar calendar = DateUtils.toCalendar(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            endDate = calendar.getTime();

            List<History> historyList = (List<History>) getServletContext().getAttribute("history");
            List<History> filter = new ArrayList<>();

            SimpleDateFormat formatHistory = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for(History item :historyList){
                Date itemDate = formatHistory.parse(item.getTime());
                if(itemDate.after(startDate) && itemDate.before(endDate))
                    filter.add(item);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response.getWriter().write(gson.toJson(filter));
        }catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

}
