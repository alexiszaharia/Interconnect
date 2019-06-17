<%-- 
    Document   : header
    Created on : Jun 10, 2019, 7:40:24 PM
    Author     : Alexis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default" style="margin-bottom: 0px;">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand logo" href="<%=request.getContextPath()%>/home">INTERCONNECT</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="<%=request.getContextPath()%>/modificare_parola" title="Schimbare parola">
                    <i class="fas fa-user"></i> &nbsp; ${pageContext.request.userPrincipal.name}
                </a>
            </li>
            <li><a href="<%=request.getContextPath()%>/logout" title="Delogare"><i class="fas fa-power-off"></i></a></li>
        </ul>
    </div>
</nav>