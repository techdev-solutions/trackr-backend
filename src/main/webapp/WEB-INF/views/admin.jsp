<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>trackr admin login</title>
    <link rel="stylesheet" href="src/vendor/bootstrap/dist/css/bootstrap.min.css"/>
</head>
<body>

<br/><br/><br/><br/><br/><br/><br/><br/>

<div class="container">

    <div class="row">
        <div class="col-lg-offset-4 col-lg-4">

            <!-- the form takes one third in the middle of the display !-->
            <div class="row">
                <div class="col-sm-12">

                    <form class="form-horizontal" action="login/admin" method="post">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Username</label>
                            <div class="col-sm-8">
                                <input type="text" id="username" name="username" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Password</label>
                            <div class="col-sm-8">
                                <input type="password" id="password" name="password" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button type="submit" class="btn btn-primary">Log in</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>

        </div>
    </div>

</div>
</body>
</html>
