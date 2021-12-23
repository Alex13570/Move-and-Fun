<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логин</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<div class="header">
<div class="container">
<%
    Boolean errors = (Boolean) request.getAttribute("errors");
    if(errors!=null && errors==true){

%>
Неверная пара логин-пароль!
<%
    }
%>

<form action="sign-in" method="post">
    <input class="btn" type="text" name="email" placeholder="email..."/>
    <input class="btn" type="password" name="password" placeholder="Пароль..."/>
    <input class="btn" type="submit" value="Войти"/>
</form>
</div>
</div>
</body>
</html>
