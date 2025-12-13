<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<head>
    <title>${meal.id == null ? 'Добавление' : 'Редактирование'} еды</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<h3>${meal.id == null ? 'Добавление' : 'Редактирование'} еды</h3>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">

    <p>
        <label for="dateTime">DateTime:</label>
        <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}" required>
    </p>

    <p>
        <label for="description">Description:</label>
        <input type="text" id="description" name="description" value="${meal.description}" required>
    </p>

    <p>
        <label for="calories">Calories:</label>
        <input type="number" id="calories" name="calories" value="${meal.calories}" min="0" required>
    </p>

    <p>
        <button type="submit">Save</button>
        <button type="button" onclick="history.back()">Cancel</button>
    </p>
</form>

</body>
</html>
