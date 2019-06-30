<%-- 
    Document   : modificare_user
    Created on : Jun 14, 2019, 2:18:32 AM
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
        <title>Modificare user</title>
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
                    <h1>Modificare utilizator</h1>
                    <div id="alerta_modificare_utilizator" class="alert" 
                         style="display: none; width: 90%;">
                    </div>
                    <div class="form-group" style="width: 90%;">
                        <label for="nume_user">Nume user:</label>
                        <input type="text" class="form-control" id="nume_user" 
                               value="${user.getUserName()}"/>
                        <br/>
                        <label for="parola_user">Parola user:</label>
                        <input type="password" class="form-control" id="parola_user" value=""/>
                        <br/>
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
                            onclick="modificareUtilizator(${user.getId()})">
                        Trimitere
                    </button>
                </div>
            </div>
        </div>

        <script>
            var root = '<%=request.getContextPath()%>';
            var userInitial;
            var parolaInitiala;
            var rolInitial;
            var numePersoanaInitiala;
            var prenumePersoanaInitiala;
            var varstaInitiala;
            var judetInitial;
            var localitateInitiala;
            var adresaInitiala;
            var sexInitial;

            $(document).ready(function () {
                var rol = '${user.getRole().getRol()}';
                $('#tip_utilizator option[value=' + rol +']').attr("selected", "selected");
                
                userInitial = '${user.getUserName()}';
                parolaInitiala = $('#parola_user').val();
                rolInitial = rol;
                
                numePersoanaInitiala = '${user.getNumePersoana()}';
                $('#nume_persoana').val(numePersoanaInitiala);
                prenumePersoanaInitiala = '${user.getPrenumePersoana()}';
                $('#prenume_persoana').val(prenumePersoanaInitiala);
                varstaInitiala = ${user.getVarsta()};
                $('#varsta_persoana').val(varstaInitiala);
                judetInitial = '${user.getJudet()}';
                $('#judet').val(judetInitial);
                localitateInitiala = '${user.getLocalitate()}';
                $('#localitate').val(localitateInitiala);
                adresaInitiala = '${user.getAdresa()}';
                $('#adresa').val(adresaInitiala);
                sexInitial = '${user.getSex()}';
                $('#sex option[value=' + sexInitial + ']').attr("selected", "selected");
            });
        </script>
    </body>
</html>
