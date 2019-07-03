<%-- 
    Document   : adaugare_referendum
    Created on : Jun 13, 2019, 1:54:27 PM
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
        <title>Adaugare referendum</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/adm_pub/css/adm_pub.css"/>
        <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>        
        <script src="<%=request.getContextPath()%>/adm_pub/js/adm_pub.js"></script>
    </head>
    <body>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String dataMin = sdf.format(calendar.getTime());
        %>
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
                    <h1>Adaugare referendum</h1>
                    <div id="alerta_adaugare_referendum" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <form id="form_adaugare_referendum" method="POST" 
                          enctype="multipart/form-data">
                        <div class="form-group" style="width: 90%;">
                            <label for="files">Atasamente:</label>
                            <input type="file" id="files" name="files" multiple="multiple"/>
                            <br/>
                            <label for="prezentare_referendum">Prezentare referendum:</label>
                            <textarea class="form-control" id="prezentare_referendum" 
                                      name="prezentare_referendum" rows="10"></textarea>
                            <br/>
                            <label for="data_referendum">Data referendum:</label>
                            <input type="date" class="form-control" id="data_referendum" 
                                   name="data_referendum" min="<%=dataMin%>"/>
                            <br/>                      
                        </div>
                    </form>
                    <hr/>
                    <div class="form-group" style="width: 90%;">
                        <p>Intrebari salvate: <span id="nr_intrebari_salvate">0</span></p>
                        <div id="div_intrebare_referendum">
                            <label for="intrebare_referendum">Intrebare referendum:</label>
                            <input type="text" class="form-control" id="intrebare_referendum"/>
                            <br/>
                            <label>Optiuni:</label>
                            <div id="div_optiuni">
                                <input type="text" class="form-control" name="optiune"/>
                                <br/>
                                <input type="text" class="form-control" name="optiune"/>
                                <br/>
                            </div>
                            <i class="fas fa-plus-circle" style="font-size: 20px; cursor: pointer;" 
                               title="Adauga optiune" id="btn_adauga_optiune"></i>
                        </div>
                        <br/>
                        <button id="btn_salvare_intrebare" class="btn btn-success">
                            Salvare intrebare
                        </button>
                    </div>
                    <br/>
                    <button class="btn btn-primary" 
                            onclick="adaugareReferendum('${pageContext.request.userPrincipal.name}')">
                        Trimitere
                    </button>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            var nrIntrebariSalvate = 0;
            var intrebariSalvate = '';

            $('#btn_adauga_optiune').click(function () {
                $('#div_optiuni').append('<input type="text" class="form-control" name="optiune"/><br/>');
            });

            $('#btn_salvare_intrebare').click(function () {
                $('#alerta_adaugare_referendum').removeClass('alert-success');
                $('#alerta_adaugare_referendum').removeClass('alert-danger');
                $('#alerta_adaugare_referendum').empty();

                var intrebare = $('#intrebare_referendum').val();
                var optiuni = getOptiuni();

                if (intrebare == '') {
                    $('#alerta_adaugare_referendum').css('display', '');
                    $('#alerta_adaugare_referendum').addClass('alert-danger');
                    $('#alerta_adaugare_referendum').html('Intrebarea nu poate fi goala!');
                    return;
                }
                if (optiuni == false) {
                    $('#alerta_adaugare_referendum').css('display', '');
                    $('#alerta_adaugare_referendum').addClass('alert-danger');
                    $('#alerta_adaugare_referendum').html('Trebuie completate cel putin doua optiuni de raspuns!');
                    return;
                }

                nrIntrebariSalvate++;
                if (intrebariSalvate == '') {
                    intrebariSalvate += intrebare + ';' + optiuni;
                } else {
                    intrebariSalvate += '<>' + intrebare + ';' + optiuni;
                }
                $('#nr_intrebari_salvate').html(nrIntrebariSalvate);

                $('#intrebare_referendum').val('');
                $('input[name=optiune]').each(function (i, elem) {
                    $(this).val('');
                });

                console.log('nr salvate = ' + nrIntrebariSalvate + ' intrebari = ' + intrebariSalvate);
            });
        </script>
    </body>
</html>
