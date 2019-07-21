<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>




<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
    <body>
       <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="centre">
        <h2>
             Registration page! <br/>

        </h2>

        <c:if test="${not empty sql_error_message}">
            <p class="error">${sql_error_message}</p>
        </c:if>
        <br/>

        <br>
        <form method="POST" action="${pageContext.request.contextPath}/api/user-register">

            Surname <input type="text" name="surname"/><br>
            <c:if test="${not empty surname_error_message}">
            	<p class="error">${surname_error_message}</p>
            </c:if>
            E-email <input type="text" name="email"/><br>
            <c:if test="${not empty email_error_message}">
            	<p class="error">${email_error_message}</p>
            </c:if>
            Password <input type="password" name = "pass"/><br>
            <c:if test="${not empty password_error_message}">
                <p class="error">${password_error_message}</p>
            </c:if>
            <input type="submit" class="btn_style" style="margin-bottom: 30px" value="Register"/>
        </form>

        <p class="centre" style="margin-top: 30px">Already have an account? <a href="/login.jsp ">Sign In</a>.</p>
            </div>
         </div>

        </div>
       </div>
    </body>
</html>