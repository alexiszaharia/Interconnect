<%-- 
    Document   : home
    Created on : Jun 8, 2019, 12:04:26 PM
    Author     : Alexis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Acasa</title>
        <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="resources/css/general.css"/>
        <script src="resources/js/bootstrap/bootstrap.min.js"></script>
        <script src="resources/js/jquery/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
            <div class="row">
                <div class="col-sm-2">
                    <jsp:include page="meniu.jsp">
                        <jsp:param name="role" value="${pageContext.request.userPrincipal.authorities}"/>
                    </jsp:include>
                </div>
                <div class="col-sm-10">
                    <h1>Acasa</h1>
                </div>
            </div>
        </div>
    </body>
</html>
