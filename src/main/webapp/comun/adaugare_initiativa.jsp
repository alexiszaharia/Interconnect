<%-- 
    Document   : adaugare_initiativa
    Created on : Jun 12, 2019, 8:04:22 PM
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
        <title>Adaugare initiativa</title>
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
                    <h1>Adaugare initiativa</h1>
                    <div id="alerta_adaugare_initiativa" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <form id="form_adaugare_initiativa" method="POST" 
                          enctype="multipart/form-data">
                        <div class="form-group" style="width: 90%;">
                            <label for="titlu_initiativa">Titlu initiativa:</label>
                            <input type="text" class="form-control" id="titlu_initiativa" 
                                   name="titlu_initiativa"/>
                            <label for="files">Atasamente:</label>
                            <input type="file" id="files" name="files" multiple="multiple"/>
                            <br/>
                            <label for="continut_initiativa">Text initiativa:</label>
                            <textarea class="form-control" rows="10" id="continut_initiativa"
                                      name="continut_initiativa"></textarea>
                            <input type="hidden" name="nume_user" 
                                   value="${pageContext.request.userPrincipal.name}"/>
                        </div>
                        <input type="submit" id="btnSubmit" class="btn btn-primary" value="Trimitere"/>
                    </form>
<!--                    <button class="btn btn-primary" 
                            onclick="adaugareInitiativa('${pageContext.request.userPrincipal.name}')">
                        Trimitere
                    </button>-->
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            
            $(document).ready(function () {
                $('#btnSubmit').click(function (event) {
                    event.preventDefault();
                    
                    var form = $('#form_adaugare_initiativa')[0];
                    var data = new FormData(form);
                    
                    adaugareInitiativa(data);
                });
            });
        </script>
    </body>
</html>
