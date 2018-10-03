<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<html>
<head>
    <link rel="shortcut icon" href="http://www.lib.ntu.edu.tw/sites/all/themes/libweb/favicon.ico" type="image/vnd.microsoft.icon" />

    <title>Import Readers - Account</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <link href="css/mdb.min.css" rel="stylesheet">

    <link href = "css/header-style.css" rel = "stylesheet" type = "text/css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src = "https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $(document).on("submit", "#registerForm", function(event) {
                // Stop form from submitting normally
                event.preventDefault();

                var userName = $("#userName").val();
                    password = $("#password").val();
                    passwordCheck = $("#passwordCheck").val();

                if(password != passwordCheck) {
                    $("#status").text("Password does not match the confirm password.");
                    return;
                }

                $.ajax({
                    type : "POST",
                    url : "Register.do",
                    data : {
                        userName : userName,
                        password : password
                    },
                    success: function (result) {
                        if(result.status){
                            $("#status").text(result.info);

                            $("#signUp").prop("disabled", true);
                            setTimeout(function(){
                                $('#registerForm').modal('hide')
                            }, 2000);

                            setTimeout(function() {
                                $("#signUp").prop("disabled", false);
                                $("#register").find("input[type=text], textarea").val("");
                                $("#register").find("input[type=password], textarea").val("");
                                $("#status").text("");
                            }, 3000);
                        }else{
                            $("#status").text(result.info);
                        }
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                        $("#submit").prop("disabled", false);
                    }
                });
            }); // end form

        });
    </script>
</head>

<body>
    <jsp:include page = "header-2.jsp"/>

    <div class="container-fluid" style="margin-top:20px;">
        <div class="container">
            <div class="text-center float-right">
                <a href="" class="btn btn-default btn-rounded mb-4" data-toggle="modal" data-target="#registerForm">New User</a>
            </div>

            <div class="modal fade" id="registerForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form autocomplete = "on" method = "post" action = "Register.do" enctype = "multipart/form-data" id="register">
                        <div class="modal-content">
                            <div class="modal-header text-center">
                                <h4 class="modal-title w-100 font-weight-bold">Sign up</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body mx-3">
                                <div class="md-form mb-4">
                                    <i class="fa fa-user prefix grey-text"></i>
                                    <input type="text" id="userName" class="form-control" required>
                                    <label data-error="wrong" data-success="right" for="userName">User name</label>
                                </div>

                                <div class="md-form mb-4">
                                    <i class="fa fa-lock prefix grey-text"></i>
                                    <input type="password" name="password" id="password" class="form-control" required>
                                    <label data-error="wrong" data-success="right" for="password">Password</label>
                                </div>

                                <div class="md-form mb-4">
                                    <i class="fa fa-lock prefix grey-text"></i>
                                    <input type="password" name="passwordCheck" id="passwordCheck" class="form-control" required>
                                    <label data-error="wrong" data-success="right" for="passwordCheck">Confirm password</label>
                                </div>
                            </div>

                            <div class="modal-footer d-flex justify-content-center">
                                <button class="btn btn-default" id="signUp">Sign up</button>
                            </div>
                            <p class="text-center text-monospace" id="status">
                            </p>
                        </div>
                    </form>
                </div>
            </div><!-- Register modal -->

            <!-- <c:if test="${fn:length(userList) gt 0}"> -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">User Name</th>
                    <th scope="col">Permission</th>
                    <!-- <th scope="col">Delete</th> -->
                    <!-- TODO delete function-->
                </tr>
                </thead>
                <tbody>
                <c:forEach items = "${userList}" var = "user" varStatus="loop">
                    <tr>
                        <td>${loop.index}</td>
                        <td>${user.userName}</td>
                        <td>${user.permission}</td>
                        <!-- <td><button type="button" class="btn btn-unique btn-sm m-0">Delete</button></td> -->
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- </c:if> -->

        </div><!-- Container -->
    </div>
</body>

    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>.

</html>
