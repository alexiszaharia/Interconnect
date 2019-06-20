<%-- 
    Document   : initiative
    Created on : Jun 12, 2019, 2:03:39 PM
    Author     : Alexis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Initiative</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/comun/css/comun.css"/>
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
                    <h1>Initiative ${tipInitiativa}</h1>
                    <c:forEach items="${listaInitiative}" var="item">
                        <a href="<%=request.getContextPath()%>/initiative/detalii_initiativa/${item.getId()}" title="Deschide initiativa">
                            <div class="panel panel-default elem_lista">
                                <div class="panel-body">
                                    <p class="titlu"><c:out value="${item.getTitluInitiativa()}"/></p>
                                    <p class="categorie">AUTOR: <c:out value="${item.getUserPublicare().getUserName()}"/></p>
                                    <p class="data"><c:out value="${item.getDataPublicareFormatata()}"/></p>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                    <ul class="pager">
                        <li <c:if test="${previousPage <= 0}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/${linkPagina}/${previousPage}">Previous</a>
                        </li>
                        <ul class="pagination">
                            <c:forEach var="item" begin="1" end="${totalPagini}">
                                <li <c:if test="${item == paginaCurenta}">class="active"</c:if>>
                                    <a href="<%=request.getContextPath()%>/${linkPagina}/${item}">${item}</a>
                                </li>
                            </c:forEach>
                        </ul>
                        <li <c:if test="${nextPage > totalPagini}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/${linkPagina}/${nextPage}">Next</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
