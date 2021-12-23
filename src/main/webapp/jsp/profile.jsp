<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.12.2021
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/jsp/style.css"/>
</head>
<body>
<%@include file="header.jsp"%>
<%
    User displayable = (User) request.getAttribute("displayedUser");
%>
<div class="container">
    <br>
    <br>
    <br>
    <hr>
<h1>
    <% Integer path = displayable.getAvatarId();
        System.out.println(path);
        if(path==null || path.equals(0) || path==0 || path==-1 || path.equals(-1)){
    %>
    <img class="avatar2" width="100" height="100" src="/no-avatar.png" />
    <% }else{ %>
    <img class="avatar2" width="100" height="100" src="/files/<%=path%>" />
    <%}%>
    <a class="btn" href="/file-upload">Обновить аватар</a>
</h1>
    <h3>имя:</h3> <%=displayable.getFirstName()%>
    <h3>фамилия:</h3> <%=displayable.getLastName()%>
    <h3>email:</h3> <%=displayable.getEmail()%>s
</div>
</body>
</html>
