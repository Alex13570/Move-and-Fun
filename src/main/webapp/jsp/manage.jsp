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
    <title>Manage</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
    <%
        Event e = (Event) request.getAttribute("event");
    %>
    <%@include file="header.jsp"%>
    <br>
    <br>
    <br>
    <br>
    <br>
    <div class="container">
    <hr>

        <form action="/manage" method="post">
            <input type="hidden" name="id" value="<%=e.getId()%>"/>
            <input type="text" maxlength="31" name="title" value="<%=e.getTitle()%>" placeholder="Название мероприятия"/>
            <br/><textarea type="textarea" maxlength="255" style="width: 20%; height: 30%" name="description" value="<%=e.getDescription()%>" placeholder="Описание мероприятия"><%=e.getDescription()%></textarea>
            <br/><input type="date" name="date" value="<%=e.getDate()%>" placeholder="Дата проведения"/>
            <input type="hidden" name="admin_id" value="<%=e.getAdminId()%>"/>
            <br/><input type="submit" name="Сохранить">
        </form>
        <hr/>
        <a href="/delete?id=<%=e.getId()%>">Удалить мероприятие</a>
    </div>
</body>
</html>
