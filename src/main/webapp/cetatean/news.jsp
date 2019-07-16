<%-- 
    Document   : news
    Created on : Jun 11, 2019, 10:50:04 AM
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
                    <h1>Stiri</h1>
                    <div id="filtre" class="form-inline">
                        <div class="form-group">
                            <label for="continut">Continut:</label>
                            <input type="text" class="form-control" id="continut" 
                                   value="${continut}">
                        </div>
                        <div class="form-group">
                            <label for="autor">Autor:</label>
                            <input type="text" class="form-control" id="autor" 
                                   value="${autor}">
                        </div>
                        <button id="btnCautare" class="btn btn-primary">Cautare</button>
                    </div>
                    <br/>
                    <c:forEach items="${listaStiri}" var="item">
                        <a href="<%=request.getContextPath()%>/news/detalii_news/${item.getId()}" title="Deschide stirea">
                            <div class="panel panel-default elem_lista">
                                <div class="panel-heading">
                                    <p class="titlu"><c:out value="${item.getTitluStire()}"/></p>
                                    <p class="categorie">CATEGORIE: <c:out value="${item.getTipStire()}"/></p>
                                    <p class="data"><c:out value="${item.getDataPublicareFormatata()}"/></p>
                                </div>
                                <div class="panel-body">
                                    <c:out value="${item.getPreviewStire()}"/> ...
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                    <ul class="pager">
                        <li <c:if test="${previousPage <= 0}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/news/${previousPage}<c:if test="${!continut.isEmpty() || !autor.isEmpty()}">/${continut}&${autor}</c:if>">Previous</a>
                            </li>
                            <ul class="pagination">
                            <c:forEach var="item" begin="1" end="${totalPagini}">
                                <li <c:if test="${item == paginaCurenta}">class="active"</c:if>>
                                    <a href="<%=request.getContextPath()%>/news/${item}<c:if test="${!continut.isEmpty() || !autor.isEmpty()}">/${continut}&${autor}</c:if>">${item}</a>
                                    </li>
                            </c:forEach>
                        </ul>
                        <li <c:if test="${nextPage > totalPagini}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/news/${nextPage}<c:if test="${!continut.isEmpty() || !autor.isEmpty()}">/${continut}&${autor}</c:if>">Next</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <script>
                var root = '<%=request.getContextPath()%>';

                $('#btnCautare').click(function () {
                    var continut = $('#continut').val();
                    var autor = $('#autor').val();
                    
                    if (continut != '' || autor != '') {
                        window.location = root + '/news/1/' + continut + '&' + autor;
                    } else {
                        window.location = root + '/news/1';
                    }
                });
        </script>
    </body>
</html>
