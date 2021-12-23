<%@ page import="ru.ivmiit.models.Event" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
    <br>
    <br>
    <br>
    <hr>

<%
    Optional<Event> optionalEvent = (Optional<Event>) request.getAttribute("currentEvent");
    if(optionalEvent.isPresent()) {
        Event event = optionalEvent.get();
        %>
<h1>
<%=event.getTitle()%>
</h1>
<h2>
    <%=event.getDate()%>
</h2>
<p>
    <%=event.getDescription()%>
</p>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    List<Event> activeEvents = (List<Event>) request.getAttribute("activeEvents");
    List<Event> adminEvents = (List<Event>) request.getAttribute("adminEvents");
    if(adminEvents.contains(event)){
        %>
            Вы являетесь администратором данного события.
            <a href="/manage?id=<%=event.getId()%>">Редактировать событие</a>
        <%
    } else {
        if(activeEvents.contains(event)){
            %>
                Вы уже участвуете.
                <a href="/quit?id=<%=event.getId()%>">Покинуть мероприятие</a>
            <%
        }else{
            %>
                Вы ещё не участвуете.
                <a href="/join?id=<%=event.getId()%>">Присоединиться к мероприятию</a>
            <%
        }
    }
%>

<%
    }else{
        %>
        <h1>Мероприятие не найдено:(</h1>
        <%
    }
%>

</div>

</body>
</html>
