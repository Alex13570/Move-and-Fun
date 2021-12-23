<%@ page import="ru.ivmiit.models.User" %>
<%@ page import="ru.ivmiit.models.Event" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная</title>
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
    Integer typeSort = (Integer) request.getAttribute("typeSort");
    String sortParam = (String) request.getAttribute("sortParam");
    List<Event> events = (List<Event>) request.getAttribute("events");
    List<Event> activeEvents = (List<Event>) request.getAttribute("activeEvents");
    List<Event> adminEvents = (List<Event>) request.getAttribute("adminEvents");

    for(Event e : events){
        if( (typeSort==1) || (typeSort==2 && activeEvents.contains(e)) || (typeSort==3 && adminEvents.contains(e))) {
%>
    <div style="background-color: gray">
        <h3>
            <%=e.getTitle()%>
        </h3>
        <h2>
            <%=e.getDate()%>
        </h2>
        <%=e.getDescription()%>
        <br/>
        <%
            if(adminEvents.contains(e)){
                %>
                    <a href="manage?id=<%=e.getId()%>">
                        Управлять
                    </a>
                <%
            } else {
                if(activeEvents.contains(e)) {

                %>
                    Вы участвуете.
                <%
            } else {
                %>
                    Вы пока не участвуете.
                <%
            }
                %>
        <a href="see?id=<%=e.getId()%>">
            Просмотр
        </a>
                <%
            }
        %>
    </div>
<%
        }
    }
%>
</div>
</body>
</html>
