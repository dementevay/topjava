<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit/Create meal</title>
</head>
<body>
<H1>Edit/Create meal</H1>

<form action="meal" method="post" name="edit_m">
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealWithExceed"/>
    <tr>
        <p><input name="isNew" value="${meal.id}" type="hidden"></p>
        <td>
            <p>Дата <input type="text" name="date" size="40" value="<%=TimeUtil.formatDateTime(meal.getDateTime())%>"></p>
        </td>
        <td>
            <p>Описание <input type="text" name="decript" size="40" value="${meal.description}"></p>
        </td>
        <td>
            <p>Калории <input type="text" name="calories" size="40" value="${meal.calories}"></p>
        </td>
    </tr>
<p><input type="submit"></p>
</form>

</body>
</html>
