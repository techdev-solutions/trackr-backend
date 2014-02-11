<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>trackr admin login</title>
</head>
<body>
<form class="form" action="/j_spring_security_check" method="post">
    <p>
        <label for="username">Username</label>
        <input type="text" id="username" name="j_username"/>
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="j_password"/>
    </p>
    <button type="submit" class="btn">Log in</button>
</form>
</body>
</html>
