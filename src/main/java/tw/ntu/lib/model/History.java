package tw.ntu.lib.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static tw.ntu.lib.web.ContextListener.getServletContext;

public class History {
    private String fileName;
    private String time;
    private String status;
    private String editor;
    private List<String> note;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public List<String> getNote() {
        return note;
    }

    public void setNote(List<String> note) {
        this.note = note;
    }

    public static void writeHistory(String fileName, Boolean status, List<String> info, String editor) {
        String historyPath = getServletContext().getRealPath("/WEB-INF/classes/history.json");
        History history = new History();

        history.setFileName(fileName);
        history.setNote(info);
        history.setEditor(editor);

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
            info.add("Can't write in history file!");
            e.printStackTrace();
        }

    }
}
