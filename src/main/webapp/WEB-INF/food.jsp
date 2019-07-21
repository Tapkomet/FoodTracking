<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Food</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
    <body>
        <h2>
            The food <br/>
        </h2>
        <c:if test="${not empty sql_error_message}">
            <p class="error">${sql_error_message}</p>
        </c:if>
        <br>
        <form action="${pageContext.request.contextPath}/api/client/editFood" method="post">
             Id <input type="number" name="food_id" value="${food.id}" readonly/><br>
             <c:if test="${not empty id_error_message}">
                <p class="error">${id_error_message}</p>
             </c:if>
             Name <input type="text" name="name" value="${food.name}" /><br>
             <c:if test="${not empty name_error_message}">
                <p class="error">${name_error_message}</p>
             </c:if>
             Total calories <input type="number" name="calories" value="${food.calories}"/><br>
             <c:if test="${not empty calories_error_message}">
                <p class="error">${calories_error_message}</p>
             </c:if>
             Protein <input type="number" name="protein" value="${food.protein}"/><br>
             <c:if test="${not empty protein_error_message}">
                <p class="error">${protein_error_message}</p>
             </c:if>
             Fat <input type="number" name="fat" value="${food.fat}"/><br>
             <c:if test="${not empty fat__error_message}">
                <p class="error">${fat__error_message}</p>
             </c:if>
             Carbs <input type="number" name="carbohydrates" value="${food.carbohydrates}"/><br>
             <c:if test="${not empty carbohydrates_error_message}">
                <p class="error">${carbohydrates_error_message}</p>
             </c:if>
             <input type="submit"/>
        </form>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>