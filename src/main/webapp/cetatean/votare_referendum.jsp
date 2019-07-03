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
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
        <script src="<%=request.getContextPath()%>/cetatean/js/cetatean.js"></script>
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
                                    <div id="div_prezentare_referendum">
                                        <p style="font-size: 16px; font-weight: bold;">
                                            Prezentare referendum
                                        </p>
                                        <c:if test="${referendum.getListaAtasamente().size() > 0}">
                                            <p style="font-weight: bold">Lista de atasamente:</p>
                                            <c:forEach items="${referendum.getListaAtasamente()}" var="item">
                                                <a href="<%=request.getContextPath()%>${item.getCale()}" target="_blank">${item.getDenumire()}</a>
                                                <br/>
                                            </c:forEach>
                                            <br/>
                                        </c:if>
                                        <p>
                                            ${referendum.getPrezentare()}
                                        </p>
                                        <br/>
                                        <button id="btnInchiderePrezentare" class="btn btn-primary">
                                            Afisare intrebari
                                        </button>
                                    </div>
                                    <div id="div_intrebari_referendum" class="form-group" style="width: 90%; display: none;">
                                        <c:forEach items="${referendum.getListaIntrebari()}" var="intrebareReferendum">
                                            <div class="class_intrebare_referendum">
                                                <p style="font-size: 14px;">${intrebareReferendum.getTextIntrebare()}</p>
                                                <input type="hidden" name="id_intrebare" 
                                                       value="${intrebareReferendum.getIdIntrebare()}"/>
                                                <form>
                                                    <c:forEach items="${intrebareReferendum.getListaOptiuniReferendum()}" var="item">
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="optiune_referendum" value="${item.getIdOptiune()}"/>
                                                                ${item.getTextOptiune()}
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </form>
                                            </div>
                                            <br/>
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

            $(document).ready(function () {
                $('#btnInchiderePrezentare').click(function () {
                    $('#div_prezentare_referendum').css('display', 'none');
                    $('#div_intrebari_referendum').css('display', '');
                });
            });
        </script>
    </body>
</html>
