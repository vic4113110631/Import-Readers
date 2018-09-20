<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>
<!-- form content -->
<div style="display:flex;width:100%;">
    <form autocomplete = "on" method = "post" action = "Insert.do" enctype = "multipart/form-data" id = "form">
        <div style="width:100%;">
            <p>1.有效期限</p>
            <input name = "date" type = "date" required>
        </div>
        <div style="width:100%;">
            <p>2.資料來源</p>
            <select name = "source" id = "source">
                <option value = "國立臺灣科技大學">國立臺灣科技大學</option>
                <option value = "國立臺灣師範大學">國立臺灣師範大學</option>
                <option value = "other">其他</option>
            </select>
        </div>
        <div style="width:100%;">
            <p>3.匯入檔案</p>
            <input name = "excel" type = "file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <p>
            <input type = "submit" value = "submit" id = "submit"/>
        </p>
    </form>
</div>