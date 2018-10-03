<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<html>
<head>
    <link rel="shortcut icon" href="http://www.lib.ntu.edu.tw/sites/all/themes/libweb/favicon.ico" type="image/vnd.microsoft.icon" />

    <title>Import Readers - history</title>
    <meta http-equiv = "Content-Type" content = "text/html" charset = "utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="css/mdb.min.css" rel="stylesheet">

    <link href = "css/header-style.css" rel = "stylesheet" type = "text/css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type='text/javascript'>
        $(function() {
            $("tr:contains('fail')").addClass("table-danger");

        });
    </script>
</head>

<body>
    <jsp:include page = "header-2.jsp"/>

    <div class="container-fluid">
        <div class="container table-responsive">
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
                    <td>${item.editor}</td>
                    <td>
                        <c:forEach items = "${item.note}" var = "info">
                            ${info}</br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div> <!-- container -->
    </div> <!-- container-fluid -->
</body>

    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>
</html>
