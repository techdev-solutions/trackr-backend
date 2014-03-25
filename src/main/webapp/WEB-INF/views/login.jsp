<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>trackr login</title>
    <link rel="stylesheet" href="src/vendor/bootstrap/dist/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"><h3 class="panel-title">Login</h3></div>
                <div class="panel-body">
                    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                        <div class="alert alert-warning">
                            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                        </div>
                    </c:if>
                    <c:if test="${param.logout == 'true'}">
                        <div class="alert alert-success">Erfolgreich ausgeloggt!</div>
                    </c:if>
                    <p>Um sich bei Trackr mit Google einzuloggen bitte auf den Knopf drücken.</p>
                    <form class="form" action="login/openid" method="post">
                        <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
                        <button type="submit" class="btn btn-primary">Login mit Google</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>