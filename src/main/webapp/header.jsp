<%-- 
    Document   : header
    Created on : Jun 10, 2019, 7:40:24 PM
    Author     : Alexis
--%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:csrfMetaTags/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font_awesome/css/all.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/general.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/jquery/jquery-3.4.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap/bootstrap.min.js"></script>
</head>

<nav class="navbar navbar-default" style="margin-bottom: 0px;">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand logo" href="<%=request.getContextPath()%>/home">INTERCONNECT</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <%if (request.getParameter("role").equals("[ROLE_CETATEAN]")) {%>
            <li>
                <a id="notificare_popover" href="#" data-toggle="popover" 
                   data-placement="bottom" data-trigger="focus" 
                   onclick="actualizareNotificariUser('${pageContext.request.userPrincipal.name}')">
                    <i class="fas fa-bell" style="font-size: 18px;"></i>
                    <span id="nr_notificari" class="badge" style="background-color: red; display: none;">0</span>
                </a>
            </li>
            <%}%>
            <li>
                <a href="<%=request.getContextPath()%>/modificare_parola" title="Schimbare parola">
                    <i class="fas fa-user"></i> &nbsp; ${pageContext.request.userPrincipal.name}
                </a>
            </li>
            <li><a href="<%=request.getContextPath()%>/logout" title="Delogare"><i class="fas fa-power-off"></i></a></li>
        </ul>
    </div>
</nav>

<script>
    var root = '<%=request.getContextPath()%>';
    var notificari;
    var user = '${pageContext.request.userPrincipal.name}';

    $(document).ready(function () {
        getNotificari(user);
        console.log(notificari.notificariNoi);
        
        if (notificari.notificariNoi == 0) {
            $('#nr_notificari').text('0');
            $('#nr_notificari').css('display', 'none');
        } else {
            $('#nr_notificari').text(notificari.notificariNoi);
            $('#nr_notificari').css('display', '');
        }

        var htmlPopover = '';
        $.each(notificari.listaNotificari, function (index, elem) {
            htmlPopover += '<a href="<%=request.getContextPath()%>/news/detalii_news/' + elem.id + '" title="Vizualizare anunt">';
            htmlPopover += '<div>' + elem.titluStire + '</div>';
            htmlPopover += '</a>'
        });

        $('#notificare_popover').popover({content: htmlPopover, html: true,
            placement: "bottom", trigger: "focus"});
    });

    function getNotificari(user) {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        notificari = new Object();

        var datePost = '&numeUtilizator=' + user;

        $.ajax({
            type: 'POST',
            url: root + "/obtinere_notificari",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            dataType: 'json',
            data: datePost,
            success: function (data, textStatus, jqXHR) {
                if (data.codRetur == 0) {
                    notificari = Object.assign(notificari, data.objectResponse);
                } else {
                    console.log("Eroare la obtinerea notificarilor " + data.mesajConsola);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Eroare la obtinerea notificarilor");
            }
        });
    }

    function actualizareNotificariUser(user) {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        var datePost = '&numeUtilizator=' + user;

        $.ajax({
            type: 'POST',
            url: root + "/actualizare_notificari",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            dataType: 'json',
            data: datePost,
            success: function (data, textStatus, jqXHR) {
                if (data.codRetur == 0) {
                    $('#nr_notificari').text('0');
                    $('#nr_notificari').css('display', 'none');
                } else {
                    console.log("Eroare la actualizarea notificarilor " + data.mesajConsola);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Eroare la actualizarea notificarilor");
            }
        });
    }
</script>