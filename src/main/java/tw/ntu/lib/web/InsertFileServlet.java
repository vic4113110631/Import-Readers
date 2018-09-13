package tw.ntu.lib.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import tw.ntu.lib.model.DataBase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet(name = "InsertFileServlet")
public class InsertFileServlet extends HttpServlet {
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            // Open a connection
            DataBase db = (DataBase) getServletContext().getAttribute("database");
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();

            // constructs the directory path to store upload file
            String uploadPath = getServletContext().getRealPath(File.separator) + File.separator + UPLOAD_DIRECTORY;;

            ServletFileUpload upload = new ServletFileUpload();
            upload.setHeaderEncoding("UTF-8");
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item: items){
                    if(item.isFormField()){
                        // Process normal fields here.
                        System.out.println("Field name: " + item.getFieldName());
                    }else {
                        // Process excle file
                        System.out.println("Field name: " + item.getName());
                        InputStream file = item.getInputStream();
                        Workbook workbook = new HSSFWorkbook(file);


                    }
                }
            }catch (FileUploadException fue){
                fue.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
