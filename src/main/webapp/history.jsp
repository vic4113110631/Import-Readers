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
    <script src = "js/jquery.ajax-progress.js"></script>
    <script type='text/javascript'>
        $(function() {
            $("tr:contains('fail')").addClass("table-danger");

            var config =  {
                "mode": "range",
                "plugins": [new confirmDatePlugin({
                    confirmIcon: "<i class='fa fa-check'></i>",
                    confirmText: "OK ",
                    showAlways: false,
                    theme: "dark"
                })],
                onChange: function(selectedDates, dateStr, instance){
                    if(selectedDates.length > 1){
                        console.log(fp.formatDate(fp.selectedDates[0], 'Y-m-d'));
                        console.log(fp.formatDate(fp.selectedDates[1], 'Y-m-d'));
                        instance.open();
                    }
                }
            };

             var fp = flatpickr('#calendar', config);

             $(".flatpickr-confirm").click(function(){
                 if(fp.selectedDates.length > 1) {

                     var start = fp.formatDate(fp.selectedDates[0], 'Y-m-d');
                     var end = fp.formatDate(fp.selectedDates[1], 'Y-m-d');

                     $.ajax({
                         type : "GET",
                         url : "Range.do",
                         data : {
                             start : start,
                             end : end
                         },
                         success: function (historyList) {
                             if(historyList.length > 0){
                                 $("#tablebody").empty();
                                 drawTable(historyList);
                             }else{
                                 $("#noRecord").fadeIn('slow')
                                               .delay(2000)
                                               .fadeOut('slow');
                             }
                             // remove the loading indicator and restore submit button
                             $('#table').preloader('remove');
                         },
                         error: function (e) {
                             console.log("ERROR : ", e);
                         }
                         ,progress: function(e) {
                             // Loading status
                             $("#table").preloader();
                         }
                     }); // end Ajax method
                 }
             });

             function drawTable(data){
                 data.forEach(function(history){
                     var info = "";

                     $.each(history.note, function(key, value) {
                         info += value + "<br/>";
                     });

                     var $tr = $('<tr>').append(
                         $('<td>').text(history.fileName),
                         $('<td>').text(history.status),
                         $('<td>').text(history.time),
                         $('<td>').text(history.editor),
                         $('<td>').html(info)
                     );
                     $("#tablebody").append($tr);
                 });
             }
        });
    </script>
    <style>
        .flatpickr-confirm {
            height: 40px;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            background: rgba(0,0,0,0.06)
        }

        .flatpickr-confirm:hover {
            color: #00acc1;
            transition: color 400ms linear;
        }
    </style>
</head>

<body>
    <jsp:include page = "header-2.jsp"/>

    <div class="container-fluid" style="margin-top:20px;">
        <div class="container">
            <div class="md-form text-center float-right">
                <input class="form-control" type="text" id="calendar">
                <label for="calendar">Data range</label>
            </div>
        </div>

        <div class="container table-responsive">
            <table class = "table">
            <thead>
                <tr>
                    <th>File Name</th>
                    <th>Status</th>
                    <th>Time</th>
                    <th>Editor</th>
                    <th>Note</th>
                </tr>
            </thead>
            <tbody id = "tablebody">
            <c:forEach items = "${history}" var = "item">
                <tr>
                    <td>${item.fileName}</td>
                    <td>${item.status}</td>
                    <td>${item.time}</td>
                    <td>${item.editor}</td>
                    <td>
                        <c:forEach items = "${item.note}" var = "info">
                            ${info}<br/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            </table>

        </div> <!-- container -->

        <div class="container text-center" style="margin-top:10px;">
            <div class="alert alert-info" role="alert" style="display: none" id="noRecord">
                查詢的歷史範圍沒有匯入檔案的紀錄。
            </div>
        </div>
    </div> <!-- container-fluid -->
</body>

    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>

    <!-- Flatpickr module -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/airbnb.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/plugins/confirmDate/confirmDate.js"></script>

    <!-- Preloader plugin -->
    <link rel="stylesheet" type="text/css" href="preloader/css/preloader.css">
    <script src="preloader/js/jquery.preloader.min.js"></script>
</html>
