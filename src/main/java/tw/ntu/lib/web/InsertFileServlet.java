package tw.ntu.lib.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.record.OldStringRecord;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tw.ntu.lib.model.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import static java.lang.Boolean.FALSE;

@WebServlet(name = "InsertFileServlet")
public class InsertFileServlet extends HttpServlet {
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");

        // Open a connection - 原本要用沒用到
        // DataBase db = (DataBase) getServletContext().getAttribute("database");
        // Connection conn = db.getConnection();
        // Statement stmt = conn.createStatement();

        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath(File.separator) + File.separator + UPLOAD_DIRECTORY;
        File folder = new File(uploadPath);
        if (!folder.exists())
            folder.mkdir();

        ServletFileUpload upload = new ServletFileUpload();
        upload.setHeaderEncoding("UTF-8");

        Boolean status = FALSE;
        List<String> info = new ArrayList<>();
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            String source = "";
            for(FileItem item: items){
                if(item.isFormField()){
                    // Process normal fields here.
                    String fieldName = item.getFieldName();
                    String value  = item.getString("utf-8");
                    switch (fieldName){
                        case "source":
                            source = value;
                            break;
                        default:
                            System.out.printf("%s:%s - ", fieldName, value);
                            System.out.println("Form field input can't catch");
                    }
                }else {
                    // Process excle file
                    InputStream file = item.getInputStream();
                    Workbook workbook = null;

                    if (item.getName().endsWith(".xlsx")) {
                        workbook = new XSSFWorkbook(file);
                    } else if (item.getName().endsWith(".xls")) {
                        // TODO　conversion MS EXCEL 5.0/95
                        workbook = new HSSFWorkbook(file);
                    } else {
                        info.add("File format is wrong!");
                    }

                    if(workbook != null) {
                        FileOutputStream out = new FileOutputStream(uploadPath + File.separator + item.getName());
                        String fileName = item.getName();
                        workbook.write(out);

                        Excel excel = new Excel(source);
                        if(excel.checkSource(workbook)) {
                            status = excel.process(workbook);
                            info.addAll(excel.getInfo());

                            String editor = (String) request.getSession().getAttribute("userName");
                            History.writeHistory(fileName, status, info, editor);
                        }
                    }

                } // end process excel file
            } // end items loop

        }catch (FileUploadException e){
            info.add("Can't upload file!請洽管理員!");
            e.printStackTrace();
        }catch (OldExcelFormatException e){
            info.add("Excel版本過於老舊，請另存為更新版本再上傳");
        }finally {
            // Return result by Json format
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject result = new JsonObject();
            result.addProperty("status", status);
            result.addProperty("info", gson.toJson(info));
            response.getWriter().write(result.toString());
        }
    }

}
