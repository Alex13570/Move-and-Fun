<%@ page import="ru.ivmiit.models.Event" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
    <br>
    <br>
    <br>
    <br>
    <hr>

    <form action="/create" method="post">
        <input type="text" maxlength="31" name="title" placeholder="Название мероприятия"/>
        <br/><textarea type="textarea" maxlength="255" style="width: 20%; height: 30%" name="description" placeholder="Описание мероприятия"></textarea>
        <br/><input type="date" name="date" placeholder="Дата проведения"/>
        <br/><input type="submit" name="Сохранить">
    </form>
</div>
</body>
</html>
