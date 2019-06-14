<%-- 
    Document   : lista_useri
    Created on : Jun 14, 2019, 1:01:42 AM
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
        <title>Lista utilizatori</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/administrator/css/administrator.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/administrator/js/administrator.js"></script>
    </head>
    <body>
        <jsp:include page="/header"/>
        <div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
            <div class="row">
                <div class="col-sm-2">
                    <jsp:include page="/meniu">
                        <jsp:param name="role" value="${pageContext.request.userPrincipal.authorities}"/>
                    </jsp:include>
                </div>
                <div class="col-sm-10">
                    <h1>Lista utilizatori</h1>
                    <div class="table-responsive" style="width: 90%;">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>
                                        Username
                                    </th>
                                    <th>
                                        Rol
                                    </th>
                                    <th>
                                        Activ
                                    </th>
                                    <th>
                                        Modificare
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listaUseri}" var="item">
                                    <tr specific="${item.getRole().getRol()}">
                                        <td>${item.getUserName()}</td>
                                        <td>${item.getRole().getRol()}</td>
                                        <td specific="${item.getId()}" functie="${(item.isEnabled()) ? 1 : 0}"></td>
                                        <td>
                                            <a href="<%=request.getContextPath()%>/modificare_user/${item.getId()}">
                                                <i class="fas fa-edit" style="font-size: 18px;"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <ul class="pager">
                        <li <c:if test="${previousPage <= 0}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/pagina_listare_useri/${previousPage}">Previous</a>
                        </li>
                        <ul class="pagination">
                            <c:forEach var="item" begin="1" end="${totalPagini}">
                                <li <c:if test="${item == paginaCurenta}">class="active"</c:if>>
                                    <a href="<%=request.getContextPath()%>/pagina_listare_useri/${item}">${item}</a>
                                </li>
                            </c:forEach>
                        </ul>
                        <li <c:if test="${nextPage > totalPagini}">class="disabled"</c:if>>
                            <a href="<%=request.getContextPath()%>/pagina_listare_useri/${nextPage}">Next</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <script>
            var root = '<%=request.getContextPath()%>';
            
            $(document).ready(function () {
                $('tr').each(function () {
                    var specific = $(this).attr('specific');
                    if (specific == 'ROLE_CETATEAN') {
                        $(this).addClass('success');
                    } else if (specific == 'ROLE_ADMINISTRATIE_PUBLICA') {
                        $(this).addClass('warning');
                    }
                });

                $('td[specific]').each(function () {
                    var specific = $(this).attr('specific');
                    var functie = $(this).attr('functie');
                    
                    if (functie == 1) {
                        $(this).html('<i class="fas fa-check-square" ' 
                                + 'style="color: green; font-size: 18px; cursor: pointer;" ' 
                                + 'onclick="activeazaDezactiveazaUser(0, ' + specific + ')" ' 
                                + 'title="Activat"></i>');
                    } else if (functie == 0) {
                        $(this).html('<i class="fas fa-window-close" ' 
                                + 'style="color: red; font-size: 18px; cursor: pointer;" ' 
                                + 'onclick="activeazaDezactiveazaUser(1, ' + specific + ')" ' 
                                + 'title="Dezactivat"></i>');
                    }
                });
            });
        </script>
    </body>
</html>
