<%--suppress JSUnusedGlobalSymbols --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<html>
<head>
    <link rel="shortcut icon" href="http://www.lib.ntu.edu.tw/sites/all/themes/libweb/favicon.ico" type="image/vnd.microsoft.icon" />

    <title>Import Readers</title>
    <meta http-equiv = "Content-Type" content = "text/html" charset = "utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="css/mdb.min.css" rel="stylesheet">

    <link href = "css/header-style.css" rel = "stylesheet" type = "text/css">
    <link href = "css/form-style.css" rel = "stylesheet" type = "text/css">
    <link href = "css/attention-style.css" rel = "stylesheet" type = "text/css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src = "./js/jquery.ajax-progress.js"></script>
    <script type='text/javascript'>
        $(function() {
            // Change select option to input text
            $(document).on("change","#source",function() {
                if($(this).val() === "other"){
                    $(this).replaceWith("<input name = 'source' type = 'text' id = 'source' required>");
                    $("#source").after("<div style='text-align:center;'>" +
                                            "<button type='button' id = 'back'>back</button>" +
                        "              </div>");
                    $("#type_other").parent().show('linear');
                    $("#typeCode_other").parent().show('linear');

                    $("#type_list").hide();
                    $("#typeCode_list").hide();

                }else if($(this).val() === "國立臺灣科技大學"){
                    $("#type_NTUST").prop('checked', true);
                    $("#typeCode_NTUST").prop('checked', true);

                    $("#type_NTNU").prop('checked', false);
                    $("#typeCode_NTNU").prop('checked', false);

                }else{
                    $("#type_NTNU").prop('checked', true);
                    $("#typeCode_NTNU").prop('checked', true);

                    $("#type_NTUST").prop('checked', false);
                    $("#typeCode_NTUST").prop('checked', false);

                }
            });

            // Retrieve to select option
            var schools = [
                {val : "國立臺灣科技大學", text: "國立臺灣科技大學"},
                {val : "國立臺灣師範大學", text: "國立臺灣師範大學"},
                {val : "other", text: "其他"}
            ];
            $(document).on("click","#back",function() {
                $(this).parent().prev().remove();

                $(this).parent().after('<select>');
                var select = $(this).parent().next();
                select.attr("name", "source");
                select.attr("id", "source");
                $(schools).each(function() {
                    select.append($("<option>").attr('value',this.val).text(this.text));
                });

                $(this).parent().remove();

                $("#type_other").parent().hide();
                $("#typeCode_other").parent().hide();

                $("#source").trigger('change');

                $("#type_list").show('linear');
                $("#typeCode_list").show('linear');
            });

            $(document).on("submit", "#form", function(event) {
                // Stop form from submitting normally
                event.preventDefault();

                // Generate post data
                var checkbox = $("input[type='checkbox']");
                var otherType = $("#type_other");
                var otherTypeCode = $("#typeCode_other");

                if($("#source").prop("tagName").toLowerCase() !== 'input') {
                    checkbox.prop("disabled", false);   // disabled 不能傳資料，暫時先取消
                    otherType.prop("disabled", true);
                    otherTypeCode.prop("disabled", true);
                }
                var form = $("#form")[0];
                var data = new FormData(form);

                checkbox.prop( "disabled", true);
                otherType.prop( "disabled", false);
                otherTypeCode.prop( "disabled", false);

                $("#info").empty(); // Clear process information

                // Check file format
                if(data.get("excel").name == ""){
                    $("#info").append($('<p>').text("Select a file!"));
                    return;
                }
                var isCorrectForamt = checkFormat(data.get("excel").name);
                if(!isCorrectForamt){
                    $("#info").append($('<p>').text("File format is wrong!"));
                    return;
                }

                // disabled the submit button
                $("#submit").prop("disabled", true);
                $(".progress").fadeIn();
                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "Insert.do",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        var info = $.parseJSON(data.info);
                        if(data.status){
                            $("#status").children('p').first().text("success");
                        }else{
                            $("#status").children('p').first().text("fail");
                        }

                        // Append information about file
                        for (var i = 0; i < info.length; i++){
                            $message = $("<p>" + info[i] + "</p>");
                            $("#info").append($message);
                            if(data.status){
                                $message.addClass("alert alert-success");
                            }else{
                                $message.addClass("alert alert-danger")
                            }
                        } // end loop

                        $("#submit").prop("disabled", false);
                        $(".progress").delay(800).fadeOut();
                    },
                    error: function (e) {
                        // $("#result").text(e.responseText);
                         console.log("ERROR : ", e);

                        $("#submit").prop("disabled", false);
                        $(".progress").delay(800).fadeOut();
                    },
                    progress: function(e) {
                        if(e.lengthComputable) {
                            var progress = e.loaded / e.total * 100;
                            $(".progress-bar").width(progress + "%");
                            $(".progress-bar").css({'background':"#6699A1"});
                            var content = e.srcElement.responseText;
                        }
                        else {
                            // TODO add message error 'Content Length not reported!';
                        }
                    }
                }); // end ajax post
            }); // end form method

            function checkFormat(fileName) {
                var ext = fileName.substr(fileName.lastIndexOf('.') + 1);
                switch (ext.toLowerCase()) {
                    case 'xls':
                    case 'xlsx':
                        //etc
                        return true;
                }
                return false;
            } // end function check excel format

        });

    </script>
</head>

<body>
    <jsp:include page = "header-2.jsp"/>


    <div class = "container-fluid" style="margin-top:20px;">
        <div class = "container">
            <jsp:include page = "form.jsp"/>
        </div>  <!-- container -->
    </div>

    <div class = "container-fluid">
        <div class = "container">
            <div id = "info" class="text-center d-flex justify-content-center"></div>
        </div>  <!-- container -->
    </div>

    <div class = "container-fluid">
        <div class = "container">
            <div class="attention">
                <a class="btn btn-info float-right text-lowercase" href="rule.xlsx" role="button">rule.xlsx</a>
                <blockquote class="blockquote bq-primary">
                    <p class="bq-title">下載範例檔案</p>
                    <p>匯入檔案前請先過目"rule.xlsx"該檔案，依照第一列的欄位對應資料才可正確匯入資料</p>
                    <p>匯入檔案需MS Excel 97-2003以上，若版本過舊請另存新檔再上傳</p>
                </blockquote>

                <p class="font-weight-bold">欄目格式</p>
                <ol>
                    <li><strong>條碼</strong> - 請確定檔案中條碼唯一，這是資料識別唯一的欄位</li>
                    <li><strong>Email</strong> - 有多個請以','逗號分隔</li>
                    <li><strong>性別</strong> - 男生填入'男'、'm'或'M'，女生填入'女'、'f'或'F'</li>
                    <li><strong>狀態</strong> - 若該列「學生或職員」為「在學或在職」，請填入'-'(dash)，其餘填入目前設定為過期</li>
                    <li><strong>到期日</strong> - 格式為yyyyMMddHHmmss</li>
                </ol>
            </div>
        </div>  <!-- container -->
    </div>

</body>

    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>
</html>
