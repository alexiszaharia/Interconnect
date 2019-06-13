<%-- 
    Document   : meniu
    Created on : Jun 10, 2019, 9:50:12 PM
    Author     : Alexis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <button type="button" class="navbar-toggle" data-toggle="collapse" 
                data-target="#meniu_principal">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="meniu_principal">
            <ul class="nav navbar-nav">
                <li><a href="<%=request.getContextPath()%>/home"><i class="fas fa-home"></i> &nbsp; Acasa</a></li>
                    <%if (request.getParameter("role").equals("[ROLE_CETATEAN]")) {%>
                <li>
                    <a href="<%=request.getContextPath()%>/news">
                        <i class="fas fa-newspaper"></i> &nbsp; Stiri
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/istoric_referendumuri">
                        <i class="fas fa-poll"></i> &nbsp; Istoric referendumuri
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/initiative_administratie">
                        <i class="fas fa-tasks"></i> &nbsp; Initiative administratie
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/initiative_cetateni">
                        <i class="fas fa-users"></i> &nbsp; Initiative cetateni
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/creare_initiativa">
                        <i class="fas fa-paste"></i> &nbsp; Creare initiativa
                    </a>
                </li>
                <%} else if (request.getParameter("role").equals("[ROLE_ADMINISTRATIE_PUBLICA]")) {%>
                <li>
                    <a href="<%=request.getContextPath()%>/page_adaugare_referendum">
                        <i class="far fa-calendar-plus"></i> &nbsp; Adaugare referendum
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/istoric_referendumuri">
                        <i class="fas fa-poll"></i> &nbsp; Istoric referendumuri
                    </a>
                </li>    
                <li>
                    <a href="<%=request.getContextPath()%>/initiative_administratie">
                        <i class="fas fa-tasks"></i> &nbsp; Initiative administratie
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/initiative_cetateni">
                        <i class="fas fa-users"></i> &nbsp; Initiative cetateni
                    </a>
                </li>
                <li>
                    <a href="<%=request.getContextPath()%>/creare_initiativa">
                        <i class="fas fa-paste"></i> &nbsp; Creare initiativa
                    </a>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>