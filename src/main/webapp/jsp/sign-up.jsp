<%@ page import="ru.ivmiit.models.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<div class="header">
<div class="container">
<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if(errors!=null && errors.size()>0){
        for(String s : errors){
            %>
            <%=s%><br/>
            <%
        }
    }
%>

<form action="sign-up" method="post">
    <input class="btn" type="text" name="email" placeholder="email..."/>
    <input class="btn" type="password" name="password" placeholder="Пароль..."/>
    <input class="btn" type="text" name="first_name" placeholder="Имя..."/>
    <input class="btn" type="text" name="last_name" placeholder="Фамилия..."/>
    <input class="btn" type="submit" value="Register"/>
</form>
</div>
</div>
</body>
</html>
