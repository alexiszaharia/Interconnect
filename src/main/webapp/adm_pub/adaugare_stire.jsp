<%-- 
    Document   : adaugare_stire
    Created on : Jun 13, 2019, 8:38:52 PM
    Author     : Alexis
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:csrfMetaTags/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Adaugare stire</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/adm_pub/css/adm_pub.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
        <script src="<%=request.getContextPath()%>/adm_pub/js/adm_pub.js"></script>
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
                    <h1>Adaugare stire</h1>
                    <div id="alerta_adaugare_stire" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <form id="form_adaugare_stire" method="POST" 
                          enctype="multipart/form-data">
                        <div class="form-group" style="width: 90%;">
                            <label for="titlu_stire">Titlu stire</label>
                            <input type="text" class="form-control" id="titlu_stire" 
                                   name="titlu_stire"/>
                            <br/>
                            <label for="preview_stire">Preview stire:</label>
                            <textarea class="form-control" id="preview_stire" 
                                      name="preview_stire" rows="2"></textarea>
                            <br/>
                            <label for="tip_stire">Tip stire:</label>
                            <select class="form-control" id="tip_stire" name="tip_stire">
                                <option value="STIRI GENERALE">STIRI GENERALE</option>
                                <option value="ALERTA">ALERTA</option>
                                <option value="ADMINISTRATIE LOCALA">ADMINISTRATIE LOCALA</option>
                                <option value="ADMINISTRATIE NATIONALA">ADMINISTRATIE NATIONALA</option>
                            </select>
                            <br/>
                            <div class="checkbox" title="Alerta notificare pentru utilizatori">
                                <label>
                                    <input id="checkbox_anunt" name="checkbox_anunt" 
                                           type="checkbox" value="">Anunt
                                </label>
                            </div>
                            <br/>
                            <label for="files">Atasamente:</label>
                            <input type="file" id="files" name="files" multiple="multiple"/>
                            <br/>
                            <label for="continut_stire">Continut stire:</label>
                            <textarea class="form-control" id="continut_stire" name="continut_stire" 
                                      rows="10"></textarea>
                            <input type="hidden" name="nume_user" 
                                   value="${pageContext.request.userPrincipal.name}"/>
                        </div>
                        <input type="submit" id="btnSubmit" class="btn btn-primary" value="Trimitere"/>
                    </form>
                    <!--                    <button class="btn btn-primary" 
                                                onclick="adaugareStire('${pageContext.request.userPrincipal.name}')">
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
                    
                    var form = $('#form_adaugare_stire')[0];
                    var data = new FormData(form);
                    
                    adaugareStire(data);
                });
            });
        </script>
    </body>
</html>
