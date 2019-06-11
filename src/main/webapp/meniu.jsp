<%-- 
    Document   : meniu
    Created on : Jun 10, 2019, 9:50:12 PM
    Author     : Alexis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="resources/css/font_awesome/css/all.css"/>
    <link rel="stylesheet" href="resources/css/general.css"/>
    <script src="resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="resources/js/jquery/jquery-3.4.1.min.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <button type="button" class="navbar-toggle" data-toggle="collapse" 
                data-target="#meniu_principal">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="meniu_principal">
            <ul class="nav navbar-nav">
                <li><a href="home"><i class="fas fa-home"></i> &nbsp; Acasa</a></li>
                <%if(request.getParameter("role").equals("[ROLE_CETATEAN]")) {%>
                    <li><a href="home"><i class="fas fa-newspaper"></i> &nbsp; Stiri</a></li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>