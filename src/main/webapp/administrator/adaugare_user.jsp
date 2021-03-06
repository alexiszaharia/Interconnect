<%-- 
    Document   : adaugare_user
    Created on : Jun 13, 2019, 9:24:57 PM
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
        <title>Adaugare user</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/administrator/css/administrator.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
        <script src="<%=request.getContextPath()%>/administrator/js/administrator.js"></script>
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
                    <h1>Adaugare utilizator</h1>
                    <div id="alerta_adaugare_utilizator" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <form id="form_import_csv" method="POST" 
                          enctype="multipart/form-data">
                        <div class="form-group" style="width: 90%;">
                            <label for="files_csv">Atasamente:</label>
                            <input type="file" id="files_csv" name="files_csv" multiple="multiple"/>
                        </div>
                        <button id="btnImport" class="btn btn-success">
                            <i class="fas fa-upload"></i>
                            Import csv utilizatori
                        </button>
                    </form>
                    <hr/>
                    <div class="form-group" style="width: 90%;">
                        <label for="nume_user">Nume user:</label>
                        <input type="text" class="form-control" id="nume_user"/>
                        <br/>
                        <label for="parola_user">Parola user:</label>
                        <input type="password" class="form-control" id="parola_user" value=""/>
                        <br/>
                        <label for="enabled">Enabled:</label>
                        <select class="form-control" id="enabled">
                            <option value="0">DISABLED</option>
                            <option value="1">ENABLED</option>
                        </select>
                        <label for="tip_utilizator">Tip utilizator:</label>
                        <select class="form-control" id="tip_utilizator">
                            <option value="ROLE_CETATEAN">CETATEAN</option>
                            <option value="ROLE_ADMINISTRATIE_PUBLICA">ADMINISTRATIE PUBLICA</option>
                            <option value="ROLE_ADMINISTRATOR">ADMINISTRATOR</option>
                        </select>
                        <br/>
                        <label for="nume_persoana">Nume persoana:</label>
                        <input type="text" class="form-control" id="nume_persoana"/>
                        <br/>
                        <label for="prenume_persoana">Prenume persoana:</label>
                        <input type="text" class="form-control" id="prenume_persoana"/>
                        <br/>
                        <label for="varsta_persoana">Varsta persoana:</label>
                        <input type="number" class="form-control" id="varsta_persoana"
                               min="18" max="100"/>
                        <br/>
                        <label for="judet">Judet:</label>
                        <input type="text" class="form-control" id="judet"/>
                        <br/>
                        <label for="localitate">Localitate:</label>
                        <input type="text" class="form-control" id="localitate"/>
                        <br/>
                        <label for="adresa">Adresa:</label>
                        <input type="text" class="form-control" id="adresa"/>
                        <br/>
                        <label for="sex">Sex:</label>
                        <select class="form-control" id="sex">
                            <option value="M">Masculin</option>
                            <option value="F">Feminin</option>
                        </select>
                    </div>
                    <button class="btn btn-primary" 
                            onclick="adaugareUtilizator()">
                        Trimitere
                    </button>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            
            $(document).ready(function () {
                $('#btnImport').click(function (event) {    
                    event.preventDefault();
                    
                    var form = $('#form_import_csv')[0];
                    var data = new FormData(form);
                    
                    importUtilizatori(data);
                });
            });
        </script>
    </body>
</html>
