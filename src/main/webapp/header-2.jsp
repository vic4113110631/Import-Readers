<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--Main Navigation-->
<header>
    <!--   <div class="nav-top"></div>  -->
    <div class="container-fluid">
        <div class="container">
            <nav class="navbar navbar-expand-md navbar-light">
                <div class="w-100 d-flex">
                    <a class="navbar-brand mr-md-auto" href="#">
                        <img src = "http://www.lib.ntu.edu.tw/sites/default/files/NTUlibrary-01_0_0.png" alt = "National Taiwan University Library" class = "img-fluid logo">
                    </a>
                </div>
            </nav>
            <nav class="navbar navbar-expand-md navbar-light">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item"><a class="nav-link" href="Index.do">Import</a></li>
                    <li class="nav-item"><a class="nav-link" href="History.do">History</a></li>
                    <!-- <li class="nav-item"><a class="nav-link" href="#">Datatable</a></li> -->
                    <c:if test="${sessionScope.permission == 'admin'}">
                        <li class="nav-item"><a href="MGMT.do" class="nav-link">Account</a></li>
                    </c:if>
                </ul>

                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link">User: ${sessionScope.userName}</a></li>
                    <li class="nav-item"><a class="nav-link" href="Logout.do">Logout</a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<!--Main Navigation-->
