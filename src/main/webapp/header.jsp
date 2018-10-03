<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>

<!-- Navigation -->
<nav class="navbar navbar-fixed-top">
    <!-- <div class = "header-top"></div> -->
    <div class="container">
        <div class="navbar-header">
            <a href = "http://www.lib.ntu.edu.tw/" >
                        <img src = "${initParam['LogoURL']}" alt = "National Taiwan University Library" class = "logo">
            </a>
        </div>

        <ul class="nav navbar-nav">
            <li><a href="Index.do" class="">Import</a></li>
            <li><a href="History.do" class="">History</a></li>
            <c:if test="${sessionScope.permission == 'admin'}">
                <li><a href="MGMT.do" class="">Account Management</a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a style="color: #69B0AC">使用者: ${sessionScope.userName}</a></li>
            <li><a href="Logout.do">Logout</a></li>
        </ul>
    </div>
</nav>