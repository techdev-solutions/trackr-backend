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
    <link rel="stylesheet" href="/app/bower_components/bootstrap/dist/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"><h3 class="panel-title">Login</h3></div>
                <div class="panel-body">
                    <c:if test="${param.error == 'true'}">
                        <div class="alert alert-warning">Login fehlgeschlagen. Bitte nur techdev Accounts nutzen!</div>
                    </c:if>
                    <c:if test="${param.logout == 'true'}">
                        <div class="alert alert-success">Erfolgreich ausgeloggt!</div>
                    </c:if>
                    <p>Um sich bei Trackr mit Google einzuloggen bitte auf den Knopf drücken.</p>
                    <form class="form" action="/j_spring_openid_security_check" method="post">
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