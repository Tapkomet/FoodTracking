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
            The food <br/>
        </h2>
        <br>
        <form action="${pageContext.request.contextPath}/api/client/editFood" method="post">
             Id <input type="number" name="food_id" value="${food.id}" readonly/><br>
             Name <input type="text" name="name" value="${food.name}" /><br>
             Total calories <input type="number" name="calories" value="${food.calories}"/><br>
             Protein <input type="number" name="protein" value="${food.protein}"/><br>
             Fat <input type="number" name="fat" value="${food.fat}"/><br>
             Carbs <input type="number" name="carbohydrates" value="${food.carbohydrates}"/><br>
             <input type="submit"/>
        </form>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>