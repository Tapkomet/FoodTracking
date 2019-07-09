<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Car List</title>
</head>
    <body>
        <h2>
            List Items <br/>
        </h2>
        <table>
        <tr><th>Name</th><th>Group</th></tr>
        <c:forEach var="i" items="${items}">
            <tr><td>${i.itemId}<c:out value="${i.name}"/></td><td>${i.price}</td>
        </c:forEach>
        </table>
        <br>
        <br>
        <%=request.getAttribute("items")%>
        <br>
        <form action="${pageContext.request.contextPath}/api/admin/addItem'/>" method="post">
             ItemId <input type="number" name="item_id"/><br>
             Name <input type="text" name="name"/><br>
             Available <input type="checkbox" name="available"/><br>
             Price <input type="number" name="price"/><br>
             <input type="submit"/>
        </form>

        <br/>
        <a href="${pageContext.request.contextPath}/index.jsp">index</a>
    </body>
</html>
