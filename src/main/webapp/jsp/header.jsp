<%@ page import="ru.ivmiit.models.User" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.12.2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css">
</head>
<body>

<%
    User userFromHeader = (User) session.getAttribute("user");
%>


<h1>Всё ок, ты <%=userFromHeader.getFirstName()%> <%=userFromHeader.getLastName()%></h1>
<div class="header">
    <div class="container">
        <div class="header_logo">
            Move and Fun
        </div>

        <div class="menu">
            <a href="/sign-out">Выйти</a>
            <a href="/create">Создать</a>
            <a href="/list">Список мероприятий</a>
            <a href="/list?sort=2">Мои мероприятия</a>
            <a href="/list?sort=3">Курируемое</a>
            <a href="/profile">Профиль</a>
            <form action="/list" method="get" style="text-decoration: none; display: inline-block;padding-left: 1%;">
                <input type="hidden" name="sort" value="4"/>
                <input type="text" maxlength="31" name="sortParameter" placeholder="Название мероприятия"/>
                <input type="submit" value="Найти!">
            </form>
        </div>
    </div>
</div>

</body>
</html>
