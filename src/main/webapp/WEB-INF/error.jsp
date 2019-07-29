<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
    <body>
        <h2>
           Error Page<br/>
        </h2>

        <c:if test="${not empty code}">
            <p class="error">Error code: ${code}</p>
        </c:if>

        <c:if test="${not empty message}">
            <p class="message">Error: ${message}</p>
        </c:if>


       <br>

    </body>
</html>
