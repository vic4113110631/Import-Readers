package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import tw.ntu.lib.model.DataBase;
import tw.ntu.lib.model.HibernateUtil;
import tw.ntu.lib.model.History;
import tw.ntu.lib.model.Reader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.FALSE;

@WebServlet(name = "InsertFileServlet")
public class InsertFileServlet extends HttpServlet {
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Open a connection
            DataBase db = (DataBase) getServletContext().getAttribute("database");
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();

            // constructs the directory path to store upload file
            String uploadPath = getServletContext().getRealPath(File.separator) + File.separator + UPLOAD_DIRECTORY;
            File folder = new File(uploadPath);
            if (!folder.exists())
                folder.mkdir();

            ServletFileUpload upload = new ServletFileUpload();
            upload.setHeaderEncoding("UTF-8");

            Boolean status = FALSE;
            String source = "";
            String expire = "";
            String fileName = "";
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item: items){
                    if(item.isFormField()){
                        // Process normal fields here.
                        String fieldName = item.getFieldName();
                        String value  = item.getString("utf-8");
                        switch (fieldName){
                            case "date":
                                expire = value;
                                break;
                            case "source":
                                source = value;
                                break;
                        }
                    }else {
                        // Process excle file
                        InputStream file = item.getInputStream();

                        Workbook workbook = null;
                        if(item.getName().contains(".xlsx")) {
                            workbook = new XSSFWorkbook(file);
                        }else if(item.getName().contains(".xls")){
                            workbook = new HSSFWorkbook(file);
                        }else{
                            System.out.println("File format is wrong!");
                        }

                        if(workbook != null) {
                            FileOutputStream out = new FileOutputStream(uploadPath + File.separator + item.getName());
                            fileName = item.getName();
                            workbook.write(out);
                            status = processExcel(workbook, expire, source);
                        }

                    }
                }
                writeHistory(fileName, status);
                JsonObject status_json = new JsonObject();
                status_json.addProperty("status", status);
                response.getWriter().write(status_json.toString());

            }catch (FileUploadException | ParseException fue){
                fue.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeHistory(String fileName, Boolean status) {
        String historyPath = getServletContext().getRealPath("/WEB-INF/classes/history.json");
        History history = new History();

        history.setFileName(fileName);
        if(status) {
            history.setStatus("success");
        }else {
           history.setStatus("fail");
        }
        // Get time
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(date);
        history.setTime(time);

        // Write to file
        try (Writer writer = new FileWriter(historyPath)) {

            List<History> historyList = (List<History>) getServletContext().getAttribute("history");
            historyList.add(history);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(historyList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static final DateTimeFormatter dateFormater = new DateTimeFormatterBuilder()
                                                                .appendYear(4,4)
                                                                .appendLiteral(".")
                                                                .appendMonthOfYear(2)
                                                                .appendLiteral(".")
                                                                .appendDayOfMonth(2)
                                                                .toFormatter();

    private static boolean processExcel(Workbook workbook, String expire, String source) throws ParseException {
        Sheet sheet = workbook.getSheetAt(0); // Default to fisrt sheet

        List<Reader> readerList = new ArrayList<>();
        boolean status = true;
        for(int i = 1; i <= sheet.getLastRowNum(); i++){ // Pass first row
            Row row = sheet.getRow(i);
            if(row == null){
                System.out.println("Row " + i + " is empty");
                status = false;
                break;
            }

            Reader reader = new Reader();
            for (Cell cell : row) {
                cell.setCellType(CellType.STRING);
                String value = cell.getStringCellValue();
                switch (cell.getColumnIndex()) {
                    case 0:
                        reader.setSeq(value);
                        break;
                    case 1:
                        reader.setCname(value);
                        break;
                    case 2:
                        reader.setCollege(value);
                        break;
                    case 3:
                        reader.setDeptname(value);
                        break;
                    case 4:
                        reader.setTitle(value);
                        break;
                    case 5:
                        reader.setGender(value);
                        break;
                    case 6:
                        String [] year_month_day = value.split("\\.");
                        int year = Integer.parseInt(year_month_day[0]) + 1911;

                        break;
                    case 7:
                        reader.setIdno(value);
                        break;
                    case 8:
                        reader.setBarcode(value);
                        break;
                    case 9:
                        if(value.equals(""))
                            reader.setExpire((byte) 0);
                        break;
                    case 10:
                        reader.setNtuemail(value);
                        break;
                    case 11:
                        reader.setContemail(value);
                        break;
                    case 12:
                        reader.setPhonehome(value);
                        break;
                    case 13:
                        reader.setMobile(value);
                        break;
                    case 14:
                        reader.setPhone3(value);
                        break;
                    case 15:
                        reader.setPermPostco(value);
                        break;
                    case 16:
                        reader.setPermadd(value);
                        break;
                    case 17:
                        reader.setContPostco(value);
                        break;
                    case 18:
                        reader.setContadd(value);
                        break;
                    case 19:
                        reader.setUsrNote(value);
                        break;
                } // end switch
            } // end loop for cells
            readerList.add(reader);
        } // end for-loop for rows

        Date expire_date = new SimpleDateFormat("yyyy-MM-dd").parse(expire);

        if(status) {
            Session session = HibernateUtil.openSession();
            Transaction transaction = session.beginTransaction();

            java.util.Date date = Calendar.getInstance().getTime();
            java.sql.Date now = new java.sql.Date(date.getTime());

            for (Reader item : readerList) {

                item.setBegindate(now);
                item.setEnddate(new java.sql.Date(expire_date.getTime()));
                item.setSrc(source);

                try {
                    session.save(item);
                }catch (ConstraintViolationException e){
                    System.out.println(item.getCname()+ "is duplicate");
                }
            }
            try {
                transaction.commit();
            }catch (Exception e){
                status = false;
                e.printStackTrace();
            }
        }
        return status;
    } // end method

}
