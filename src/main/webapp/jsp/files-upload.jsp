<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.12.2021
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Загрузка аватара</title>
</head>

<%@include file="header.jsp"%>

<body>
<div class="container">
    <br>
    <br>
    <br>
Разрешены только JPG / PNG!
<form action="/file-upload" method="post" enctype="multipart/form-data">
    <input class="button" type="file" name="file" accept="image/jpeg,image/png"/>
    <input class="button" type="submit" value="Send!"/>
</form>
</div>
</body>
</html>