<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>
<%
    session = request.getSession(false);

    boolean loggedIn = session != null && session.getAttribute("userName") != null;
    if(loggedIn)
        response.sendRedirect("Index.do");
%>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Import Readers - login</title>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <!-- Bootstrap core CSS -->
  <link href="css/login/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/login/mdb.min.css" rel="stylesheet">

  <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script type="text/javascript">
    $(function() {
      $('#loginForm').modal('show');

      $(document).on("submit", "#loginForm", function(event) {
        // Stop form from submitting normally
        event.preventDefault();

        var userName = $("#name").val();
            password = $("#password").val();


        $.ajax({
            type : "POST",
            url : "/Login.do",
            data : {
                userName : userName,
                password : password
            },
            success: function (result) {
                if(result.status){
                    $("#status").text(result.info);
                    $("#login").prop("disabled", true);
                    window.setTimeout(function(){
                        window.location.href = "/Index.do";
                    }, 2000);
                }else{
                    $("#status").text(result.info);
                }
            },
            error: function (e) {
              // $("#result").text(e.responseText);
              console.log("ERROR : ", e);
              $("#submit").prop("disabled", false);
            }
        });
      }); // end form method

    });
  </script>
</head>

<body>
  <div class="modal fade" id="loginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <form autocomplete = "on" method = "post" action = "Login.do" enctype = "multipart/form-data">
          <div class="modal-content">
              <div class="modal-header text-center">
                  <h4 class="modal-title w-100 font-weight-bold">Sign in</h4>
              </div>
              <div class="modal-body mx-3">
                  <div class="md-form mb-5">
                      <i class="fa fa-user prefix grey-text"></i>
                      <input type="text"  class="form-control" id = "name" required>
                      <label data-error="wrong" data-success="right" for="name">User name</label>
                  </div>

                  <div class="md-form mb-4">
                      <i class="fa fa-lock prefix grey-text"></i>
                      <input type="password"  class="form-control" id = "password" required>
                      <label data-error="wrong" data-success="right" for="password">Password</label>
                  </div>
              </div>
              <div class="modal-footer d-flex justify-content-center">
                  <button type="submit" class="btn btn-default" id="login">Login</button>
              </div>
              <p class="text-center text-monospace" id="status">
              </p>
          </div> 
        </form>
    </div><!-- dialog -->
  </div> <!-- modal form-->
</body>

  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/login/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/login/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/login/mdb.min.js"></script>

</html>
