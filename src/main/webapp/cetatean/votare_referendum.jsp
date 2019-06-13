<%-- 
    Document   : votare_referendum
    Created on : Jun 13, 2019, 6:20:52 PM
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
        <title>Votare referendum</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/cetatean/css/cetatean.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/cetatean/js/cetatean.js"></script>
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
                        <h1>Votare referendum</h1>
                        <c:if test="${referendumActiv == false}">
                            <p style="font-size: 16px;">
                                Nu exista un referendum activ
                            </p>
                        </c:if>
                        <c:if test="${referendumActiv == true}">
                            <div id="div_referendum">
                                <c:if test="${oreActive == true && votatDeja == false}">
                                    <div id="alerta_votare_referendum" class="alert alert-danger" 
                                         style="display: none; width: 90%;">
                                    </div>
                                    <div class="form-group" style="width: 90%;">
                                        <p style="font-size: 14px;">${referendum.getIntrebare()}</p>
                                        <c:forEach items="${referendum.getListaOptiuni()}" var="item">
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="optiune_referendum" value="${item.getIdOptiune()}"/>
                                                    ${item.getTextOptiune()}
                                                </label>
                                            </div>
                                        </c:forEach>
                                        <button class="btn btn-primary" onclick="votareReferendum(${referendum.getIdReferendum()},
                                                        '${pageContext.request.userPrincipal.name}')">
                                            Trimite
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${oreActive == false || votatDeja == true}">
                                    <a href="<%=request.getContextPath()%>/istoric_referendumuri/detalii_referendum/${referendum.getIdReferendum()}">
                                        Vizualizare detalii referendum
                                    </a>
                                </c:if>
                            </div>
                        </c:if>
                        <div style="margin-bottom: 50px;">

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var root = '<%=request.getContextPath()%>';
        </script>
    </body>
</html>
