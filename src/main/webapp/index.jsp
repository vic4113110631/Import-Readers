<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>

<html>
    <head>
    </head>

    <body>
        <a href = "http://www.lib.ntu.edu.tw/">
            <img src = "${initParam['LogoURL']}" alt = "National Taiwan University Library">
        </a>

        <a href = "">History</a>
        <a href = "">Import</a>

        <form autocomplete = "on" method = "post" action = "Insert.do" enctype = "multipart/form-data" name = "postForm">
            <p>1.有效期限:<label><input name = "date" type = "date"></label></p>
            <p>2.資料來源:<label><input name = "source" type = "text"></label></p>
            <p>3.匯入檔案:<input name = "excel" type = "file"></p>
            <p><input type = "submit" value = "送出" /></p>
        </form>

    </body>
</html>
