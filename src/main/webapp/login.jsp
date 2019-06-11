<%-- 
    Document   : login
    Created on : Jun 8, 2019, 6:14:22 PM
    Author     : Alexis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Pagina Logare</title>
        <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css"/>
        <link rel="stylesheet" href="resources/css/general.css"/>
    </head>
    <body>
        <div class="container">
            <div class="panel panel-default center-block" style="width: 70%; margin-top: 10%;">       
                <div class="panel-heading logo">
                    INTERCONNECT
                </div>
                <div class="panel-body">
                    <form action="<%=request.getContextPath()%>/login" method="POST">
                        <div class="form-group">
                            <label for="user_name">User Name:</label>
                            <input id="user_name" type="text" name="username" 
                                   class="form-control" placeholder="Introduceti numele de user"/>
                        </div>
                        <div class="form-group">
                            <label for="parola">Password:</label>
                            <input id="parola" type="password" name="password" 
                                   class="form-control" placeholder="Introduceti parola"/>
                        </div>
                        <input type="submit" value="Sign In" class="btn btn-default center-block"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <br/>
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            Userul si parola sunt invalide!
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-info">
                            Ati fost delogat!
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
