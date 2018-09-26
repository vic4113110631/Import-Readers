<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<html>
<head>
    <!--
    <link rel="shortcut icon" href="http://www.lib.ntu.edu.tw/sites/all/themes/libweb/favicon.ico" type="image/vnd.microsoft.icon" />
    -->
    <title>Import Readers</title>
    <meta http-equiv = "Content-Type" content = "text/html" charset = "utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel = "stylesheet" type = "text/css" href = "css/bootstrap.min.css">
    <link rel = stylesheet type = "text/css" href = "css/header-style.css">


    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type='text/javascript'>
        function adjust_body_offset() {
            $('body').css('padding-top', $('.navbar-fixed-top').outerHeight(true) + 'px' );
        }

        $(window).resize(adjust_body_offset);

        $(document).ready(adjust_body_offset);
    </script>
</head>

<body>
    <div class="container-fluid">
        <jsp:include page = "header.jsp"/>
    </div>

    <div class="container-fluid">
        <div class="container">
        <table class = "table">
            <thead>
                <tr>
                    <th>File Name</th>
                    <th>Status</th>
                    <th>Time</th>
                    <th>Note</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items = "${history}" var = "item">
                <tr>
                    <td>${item.fileName}</td>
                    <td>${item.status}</td>
                    <td>${item.time}</td>
                    <td>${item.note}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div> <!-- container -->
    </div> <!-- container-fluid -->
</body>
</html>
