<%--suppress JSUnusedGlobalSymbols --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<html>
<head>
    <!--
    <link rel="shortcut icon" href="http://www.lib.ntu.edu.tw/sites/all/themes/libweb/favicon.ico" type="image/vnd.microsoft.icon" />
    -->
    <title>Import Readers</title>
    <meta http-equiv = "Content-Type" content = "text/html" charset = "utf-8">
    <link rel = "stylesheet" type = "text/css" href = "css/bootstrap.min.css">
    <link rel = stylesheet type = "text/css" href = "css/header-style.css">
    <link rel = stylesheet type = "text/css" href = "css/form-style.css">

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
                }
            });

            // Retrieve to select option
            var schools = [
                {val : "NTUST", text: "國立臺灣科技大學"},
                {val : "NTNU", text: "國立臺灣師範大學"},
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
            });

            $("#form").submit(function(event) {
                // Stop form from submitting normally
                event.preventDefault();

                var form = $("#form")[0];
                var data = new FormData(form);

                // disabled the submit button
                $("#submit").prop("disabled", true);
                $(".progress").show('slow');
                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "/Insert.do",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {

                        console.log("SUCCESS : ", data);
                        $("#submit").prop("disabled", false);

                    },
                    error: function (e) {
                        // $("#result").text(e.responseText);
                         console.log("ERROR : ", e);
                        $("#submit").prop("disabled", false);

                    },
                    progress: function(e) {
                        if(e.lengthComputable) {
                            var progress = e.loaded / e.total * 100;
                            $(".progress-bar").width(progress + "%");
                            var content = e.srcElement.responseText;
                        }
                        else {
                            // TODO add message error 'Content Length not reported!';
                        }
                    }
                }); // end ajax post
            }); // end form method
        });

        <!-- dynamic bootstrap 3 navbar fixed top overlapping content -->
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
            <jsp:include page = "form.jsp"/>
        </div>  <!-- container -->
    </div>

</body>
</html>
