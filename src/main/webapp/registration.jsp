<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>




<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Doctor App</title>
</head>
    <body>
        <form method="POST" action="${pageContext.request.contextPath}/api/user-register">
            Surname <input type="text" name="surname"/><br>
            E-email <input type="text" name="email"/><br>
            Password <input type="password" name = "pass"/><br>
            <input type="submit"/>
        </form>
    </body>
</html>