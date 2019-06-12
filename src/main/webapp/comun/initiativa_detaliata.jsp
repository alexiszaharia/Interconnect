<%-- 
    Document   : initiativa_detaliata
    Created on : Jun 12, 2019, 3:55:18 PM
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
        <title>Detalii initiativa</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/comun/css/comun.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/comun/js/comun.js"></script>
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
                    <div class="container" style="width: 80%;">
                        <h2>${initiativa.getTitluInitiativa()}</h2>
                        <p class="categorie">AUTOR: ${initiativa.getUserPublicare().getUserName()}</p>
                        <p class="data">${initiativa.getDataPublicareFormatata()}</p>
                        <br/>
                        <div style="margin-bottom: 50px;">
                            ${initiativa.getTextInitiativa()}
                        </div>
                        <div style="margin-bottom: 20px;">
                            <span id="like" class="like_dislike" title="Like" 
                                  onclick="selectareLikeDislike('${pageContext.request.userPrincipal.name}', 
                                    ${initiativa.getId()}, 1)">
                                <i class="fas fa-thumbs-up"></i> <span id="nr_like">${like}</span>
                            </span> &nbsp; &nbsp; 
                            <span id="dislike" class="like_dislike" title="Dislike" 
                                  onclick="selectareLikeDislike('${pageContext.request.userPrincipal.name}', 
                                    ${initiativa.getId()}, 0)">
                                <i class="fas fa-thumbs-down"></i> <span id="nr_dislike">${dislike}</span>
                            </span>
                        </div>
                        <div style="margin-bottom: 20px;">
                            <div class="form-group">
                                <label for="comment_opinie">Comentariu:</label>
                                <textarea class="form-control" rows="3" id="comment_opinie"></textarea>
                            </div>
                            <button class="btn btn-primary" title="Trimitere comentariu"
                                    onclick="adaugareComentariuInitiativa('${pageContext.request.userPrincipal.name}', 
                                    ${initiativa.getId()})">
                                Trimitere
                            </button>
                        </div>
                        <div id="div_lista_opinii">
                            <c:forEach items="${initiativa.getListaOpinii()}" var="item">
                                <div class="opinie">
                                    <p class="autor_opinie"><c:out value="${item.getUserOpinie().getUserName()}"/></p>
                                    <p class="data"><c:out value="${item.getDataFormatata()}"/></p>
                                    <p><c:out value="${item.getComentariu()}"/></p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            var likeUserCurent = ${tipVotUserCurent};
            
            $(document).ready(function () {
                if (likeUserCurent == 0) {
                    $('#dislike').addClass('button_like_active');
                } else if (likeUserCurent == 1) {
                    $('#like').addClass('button_like_active');
                }
            });
        </script>
    </body>
</html>
