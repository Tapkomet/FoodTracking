<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Food List</title>
</head>
    <body>
        <h2>
            List Foods <br/>
        </h2>
        <table>
        <tr><th>id</th><th>Name</th><th>Calories</th><th>Protein</th><th>Fat</th><th>Carbs</th><th></th></tr>
        <c:forEach var="i" foods="${foods}">
            <tr><td><a href="food?food_id=<c:out value='${i.id}' />"> <c:out value="${i.id}"/></a>
            <td>${i.name}</td><td>${i.calories}</td>
            <td>${i.protein}</td><td>${i.fat}</td><td>${i.carbohydrates}</td>
            <td>
                <form action="${pageContext.request.contextPath}/api/client/deleteFood?food_id=${i.id}" method="post">
                <input type="submit" value="Delete"/>
                </form>
            </td>
        </c:forEach>
        </table>
        <br>
        <br>
        <%=request.getAttribute("foods")%>
        <br>
        <form action="${pageContext.request.contextPath}/api/client/addFood" method="post">
             FoodId <input type="number" name="food_id"/><br>
             Name <input type="text" name="name"/><br>
             Calories <input type="number" name="calories"/><br>
             Protein <input type="number" name="protein"/><br>
             Fat <input type="number" name="fat"/><br>
             Carbohydrates <input type="number" name="carbohydrates"/><br>
             <input type="submit"/>
        </form>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>
