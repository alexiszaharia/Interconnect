<%-- 
    Document   : modificare_parola
    Created on : Jun 17, 2019, 1:49:25 PM
    Author     : Alexis
--%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:csrfMetaTags/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificare parola</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/general.js"></script>
    </head>
    <body>
        <jsp:include page="/header">
            <jsp:param name="role" value="${pageContext.request.userPrincipal.authorities}"/>
        </jsp:include>
        <div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
            <div class="row">
                <div class="col-sm-2">
                    <jsp:include page="/meniu">
                        <jsp:param name="role" value="${pageContext.request.userPrincipal.authorities}"/>
                    </jsp:include>
                </div>
                <div class="col-sm-10">
                    <h1>Modificare parola</h1>
                    <div id="alerta_modificare_parola" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <div class="form-group" style="width: 90%;">
                        <label for="parola_user_initiala">Parola user initiala:</label>
                        <input type="password" class="form-control" id="parola_user_initiala" value=""/>
                        <br/>
                        <label for="parola_user_noua">Parola noua user:</label>
                        <input type="password" class="form-control" id="parola_user_noua" value=""/>
                        <br/>
                        <label for="parola_user_confirmata">Parola noua confirmata user:</label>
                        <input type="password" class="form-control" id="parola_user_confirmata" value=""/>
                        <br/>
                    </div>
                    <button class="btn btn-primary" 
                            onclick="modificareParolaUtilizator('${pageContext.request.userPrincipal.name}')">
                        Trimitere
                    </button>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
        </script>
    </body>
</html>
