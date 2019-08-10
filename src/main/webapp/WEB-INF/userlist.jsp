<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User List</title>
</head>
    <body>
        <h2>
            List Users <br/>
        </h2>
        <c:if test="${not empty sql_error_message}">
            <p class="error">${sql_error_message}</p>
        </c:if>
        <table>
        <tr><th>Id</th><th>Surname</th><th>Email</th><th>role</th></tr>
        <c:forEach var="i" items="${users}">
            <tr><td>${i.id}</td>
            <td>${i.surname}</td><td>${i.email}</td>
            <td>${i.role}</td>
            <form action="${pageContext.request.contextPath}/api/admin/users/edit?id=${i.id}" method="post">
            <td>
            <select name="role">
                <option value="ADMIN">ADMIN</option>
                <option value="CLIENT" selected>CLIENT</option>
            </select>
            <td>
            <input type="submit" value="Change"/>
            </td>
            </form>
        </c:forEach>
        </table>
        <br>
        <br>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>