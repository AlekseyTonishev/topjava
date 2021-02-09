<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/xml" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>


    <title>Редактирование</title>
</head>

<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">

        <h3>Дата и время приема пищи:</h3>
        <dl>
            <input type="datetime-local" required name="dateTime" size=50 value="${meal.dateTime}"
                   placeholder="Обязательное поле">
        </dl>
        <h3>Описание:</h3>
        <dl>
            <input type="text" name="description" required size=50 value="${meal.description}"
                   placeholder="Завтрак, Полдник и т.п.">
        </dl>
        <h3>Количество колорий:</h3>
        <dl>
            <input type="number" required name="calories" min="0" size=50 value="${meal.calories}"
                   placeholder="Целое число">
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
        <button><a href="meals">Отменить</a></button>
    </form>
</section>
</body>
</html>