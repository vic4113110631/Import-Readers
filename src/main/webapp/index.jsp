<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>

<html>
    <head>
        <title>Import Readers</title>
        <meta http-equiv = "Content-Type" content = "text/html" charset = "utf-8">
        <link rel = stylesheet type = "text/css" href = "css/normalize.css">
        <link rel = stylesheet type = "text/css" href = "css/skeleton.css">
        <link rel = stylesheet type = "text/css" href = "css/style.css">
    </head>

    <body>
        <div class = "header-top"></div>
        <div class = "header">
            <a href = "http://www.lib.ntu.edu.tw/">
                <img src = "${initParam['LogoURL']}" alt = "National Taiwan University Library" class = "logo">
            </a>
        </div>

        <ul class="menu">
            <li><a href = "#">History</a></li>
            <li><a href = "#">Import</a></li>
            <li><a href = "#">Login></a><li>
        </ul>

        <form autocomplete = "on" method = "post" action = "Insert.do" enctype = "multipart/form-data" name = "postForm">
            <p>1.有效期限:<label><input name = "date" type = "date"></label></p>
            <p>2.資料來源:</p>
            <label>
                <select>
                    <option value="NTUST">國立臺灣科技大學</option>
                    <option value="NTNU">國立臺灣師範大學</option>
                </select>
            </label>
            <p>3.匯入檔案:<input name = "excel" type = "file"></p>
            <p><input type = "submit" value = "送出" /></p>
        </form>

        <section class = "footer u-full-width">
            <div class = "container">
                <div class = "row">
                    <div class = "twelve columns">
                        <p>借還書服務：02-33662353 <a href = "mailto:tulcir@ntu.edu.tw">tulcir@ntu.edu.tw</a>
                            ｜參考諮詢：02-33662326 <a href = "mailto:tul@ntu.edu.tw">tul@ntu.edu.tw </a>
                            ｜臺北市10617羅斯福路四段一號 國立臺灣大學圖書館
                        </p>
                        <p><a href="http://www.lib.ntu.edu.tw/node/697">本站聲明</a></p>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
