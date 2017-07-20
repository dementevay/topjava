<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meals</title>
    <style>
        .exceeded {
            color: red;
        }
        .nonexceeded {
            color: green;
        }
    </style>
</head>
<body>
<a href="users" >Users</a>
<table border="1" width="60%" cellpadding="8">
    <caption><h2>Список еды:</h2></caption>
    <thead>
    <tr>
        <th>id</th>
        <th width="30%">Дата</th>
        <th width="40%">Описание</th>
        <th>Калории</th>
        <th width="15%">Превышен лимит</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>

    <c:forEach items="${meals_list}" var="me" varStatus="status">
        <jsp:useBean id="me" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${me.exceed ? 'exceeded':'nonexceeded'}">
            <td>${me.id}</td>
            <td><%=TimeUtil.formatDateTime(me.getDateTime())%></td>
            <td>${me.description}</td>
            <td>${me.calories}</td>
            <td>${me.exceed}</td>
            <td>
                <form method="post">
                    <button name="edit_btn" value="${me.id}">Edit</button>
                </form>
            </td>
            <td>
                <form method="post">
                    <button name="delete_btn" value="${me.id}">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>


</table>
</body>
</html>
