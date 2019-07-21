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
        <c:if test="${not empty sql_error_message}">
            <p class="error">${sql_error_message}</p>
        </c:if>
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
          <form action="${pageContext.request.contextPath}/api/client/foods" method="get">
          Sort by: <br>
          <input type="radio" name="toSort" value="food_id" checked>Code<br>
          <input type="radio" name="toSort" value="name">Name<br>
          <input type="radio" name="toSort" value="calories">calories<br>
          <input type="radio" name="toSort" value="protein">protein<br>
          <input type="radio" name="toSort" value="fat">fat<br>
          <input type="radio" name="toSort" value="carbohydrates">carbohydrates<br>
          <input type="submit" value="Sort"/>
          </form>
        <br>
        <%=request.getAttribute("foods")%>
        <br>
        <form action="${pageContext.request.contextPath}/api/client/addFood" method="post">
             FoodId <input type="number" name="food_id"/><br>
             <c:if test="${not empty id_error_message}">
                <p class="error">${id_error_message}</p>
             </c:if>
             Name <input type="text" name="name"/><br>
             <c:if test="${not empty name_error_message}">
                <p class="error">${name_error_message}</p>
             </c:if>
             Calories <input type="number" name="calories"/><br>
             <c:if test="${not empty calories_error_message}">
                <p class="error">${calories_error_message}</p>
             </c:if>
             Protein <input type="number" name="protein"/><br>
             <c:if test="${not empty protein_error_message}">
                <p class="error">${protein_error_message}</p>
             </c:if>
             Fat <input type="number" name="fat"/><br>
             <c:if test="${not empty fat__error_message}">
                <p class="error">${fat__error_message}</p>
             </c:if>
             Carbohydrates <input type="number" name="carbohydrates"/><br>
             <c:if test="${not empty carbohydrates_error_message}">
                <p class="error">${carbohydrates_error_message}</p>
             </c:if>
             <input type="submit"/>
        </form>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>
