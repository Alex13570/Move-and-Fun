<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.11.2021
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Присоединение</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<div class="header">
    <div class="container">
<%
    String s = (String) request.getAttribute("msg");
%>

<%=s%>

<hr/>

<a href="/list">Вернуться к списку мероприятий</a>
</div>
</div>
</body>
</html>
