<%-- 
    Document   : detailed_news
    Created on : Jun 11, 2019, 6:58:12 PM
    Author     : Alexis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Stiri</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/cetatean/css/cetatean.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
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
                        <h2>${stire.getTitluStire()}</h2>
                        <p class="categorie">CATEGORIE: ${stire.getTipStire()}</p>
                        <p class="data">${stire.getDataPublicareFormatata()}</p>
                        <br/>
                        <c:if test="${stire.getListaAtasamente().size() > 0}">
                            <p style="font-weight: bold">Lista de atasamente:</p>
                            <c:forEach items="${stire.getListaAtasamente()}" var="item">
                                <a href="<%=request.getContextPath()%>${item.getCale()}" target="_blank">${item.getDenumire()}</a>
                                <br/>
                            </c:forEach>
                            <br/><br/>
                        </c:if>
                        <div style="margin-bottom: 50px;">
                            ${stire.getContinutStire()}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
