<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Список еды</h3>
<jsp:useBean id="mealList" scope="request" type="java.util.List"/>
<a href="meals?action=create">Добавить еду</a>
<br><br>
<table>
    <thead>
    <tr>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealList}" var="item">
        <tr class="${item.excess ? 'excess' : 'normal'}">
            <td>${fn:formatDateTime(item.dateTime)}</td>
            <td>${item.description}</td>
            <td>${item.calories}</td>
            <td><a href="meals?action=update&id=${item.id}">Обновить</a></td>
            <td><a href="meals?action=delete&id=${item.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
