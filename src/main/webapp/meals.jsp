<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<a href="meals?action=add"><img src="img/add.png" width="55" height="256"></a>
<h3>Дневник приёма пищи</h3>

<table border="1" cellpadding="8" cellspacing="0">

    <tr bgcolor="#87cefa">
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Изменить</th>
        <th>Удалить</th>
    </tr>

    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>

        <tr class=${mealTo.excess ? 'redfont' : 'greenfont'}>

            <td><%=TimeUtil.localTimeToString(mealTo.getDateTime())%>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?id=${mealTo.id}&action=edit"><img src="img/edit.jpg" width="30" height="30"></a></td>
            <td><a href="meals?id=${mealTo.id}&action=delete"><img src="img/delete.png" width="25" height="25"></a></td>
        </tr>
    </c:forEach>
</table>
<br/>
Если вы хотите добавить запись в дневник - нажмите на иконку
<a href="meals?action=add"><img src="img/add.png" width="25" height="26"></a>

</body>
</html>