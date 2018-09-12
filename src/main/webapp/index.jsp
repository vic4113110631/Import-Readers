<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>

<html>
    <head>
    </head>

    <body>
        <img src = "${initParam['LogoURL']}" alt = "National Taiwan University Library">


        <form autocomplete = "on" method = "post" action = "InsertFile.do" enctype = "multipart/form-data" name = "postForm">
            <p>1.有效期限:<input name = "date" type = "date"></p>
            <p>2.資料來源:<input name = "location" type = "text"></p>
            <p>3.匯入檔案:<input name = "cover" type = "file"></p>
            <p><input type = "submit" value = "送出" /></p>
        </form>

    </body>
</html>
