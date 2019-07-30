<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Food List</title>
     <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
     <link rel="stylesheet" href="/css/style.css" type="text/css">
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
        <c:forEach var="i" items="${foods}">
            <tr><td><a href="getOne?food_id=<c:out value='${i.id}' />"> <c:out value="${i.id}"/></a>
            <td>${i.name}</td><td>${i.calories}</td>
            <td>${i.protein}</td><td>${i.fat}</td><td>${i.carbohydrates}</td>
            <td>
                <form action="${pageContext.request.contextPath}/api/client/food/delete?food_id=${i.id}" method="post">
                <input type="submit" value="Delete"/>
                </form>
            </td>
        </c:forEach>
        </table>

        <form action="${pageContext.request.contextPath}/api/client/food/getAll" method="post">
            <c:if test="${page > 1}">
                <button type="submit" class="btn btn-default" name="nextPage" value='previous'>
                    Previous
                </button>
            </c:if>
            <c:if test="${page < lastPage}">
                <button type="submit" class="btn btn-default" name="nextPage" value='next'>
                    Next
                </button>
            </c:if>
        <input type="hidden" name = "page" value="${page}">
        <input type="hidden" name = "tosort" value="${tosort}">
        </form>

        <br>
          <form action="${pageContext.request.contextPath}/api/client/food/getAll" method="get">
          Sort by: <br>
          <input type="radio" name="tosort" value="food_id"
          <c:if test="${tosort eq 'food_id'}">checked</c:if>>Id<br>
          <input type="radio" name="tosort" value="name"
          <c:if test="${tosort eq 'name'}">checked</c:if>>Name<br>
          <input type="radio" name="tosort" value="calories"
          <c:if test="${tosort eq 'calories'}">checked</c:if>>Calories<br>
          <input type="radio" name="tosort" value="protein"
          <c:if test="${tosort eq 'protein'}">checked</c:if>>Protein<br>
          <input type="radio" name="tosort" value="fat"
          <c:if test="${tosort eq 'fat'}">checked</c:if>>Fat<br>
          <input type="radio" name="tosort" value="carbohydrates"
          <c:if test="${tosort eq 'carbohydrates'}">checked</c:if>>Carbohydrates<br>
          <input type="submit" value="Sort"/>
          </form>
        <br>
        <form action="${pageContext.request.contextPath}/api/client/food/add" method="post">
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
