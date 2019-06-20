<%-- 
    Document   : referendum_detaliat
    Created on : Jun 13, 2019, 11:46:27 AM
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
        <title>Detalii referendum</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/comun/css/comun.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
        <script src="<%=request.getContextPath()%>/comun/js/comun.js"></script>
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
                    <div class="container" style="width: 80%;">
                        <h2 id="intrebare_referendum"></h2>
                        <p id="creator_referendum" class="categorie">AUTOR: </p>
                        <p id="data_referendum" class="data">DATA REFERENDUM: </p>
                        <br/>
                        <div id="statistici_referendum" style="margin-bottom: 50px;">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            var idReferendum = ${idReferendum};

            $(document).ready(function () {
                getDetaliiReferendum(idReferendum);
            });
        </script>
    </body>
</html>
