<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-07-07
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zarejestruj się</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body style="padding-top: 100px" class="bg-dark">

<%@include file="navbar.jsp"%>

<div class="text-white">

    <form:form action="/authentication/registerUser" method="post" modelAttribute="userDTO">

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <c:if test="${error != null}">
                <div class="alert alert-danger">
                    <strong>Błąd formularza! </strong>
                        ${error}
                </div>
            </c:if>

            <c:if test="${success != null}">
                <div class="alert alert-success">
                    <strong>Sukces! </strong>
                        ${success}
                </div>
            </c:if>
        </div>
        <div class="col-sm-4"></div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <label for="usernameField">Nazwa użytkownika:</label>
            <form:input id="usernameField" type="text"
                        class="form-control" path="username"/>

            <spring:bind path="username">
                <c:if test="${status.error}">
                    <div class="alert alert-danger">
                        <form:errors path="username"/>
                    </div>
                </c:if>
            </spring:bind>
            <br>

        </div>
        <div class="col-sm-4"></div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <label for="passwordField">Hasło:</label>
            <form:password id="passwordField"
                           class="form-control" path="password"/>
            <spring:bind path="password">
                <c:if test="${status.error}">
                    <div class="alert alert-danger">
                        <form:errors path="password"/>
                    </div>
                </c:if>
            </spring:bind>
            <br>

        </div>
        <div class="col-sm-4"></div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <label for="confirmPasswordField">Potwierdź hasło:</label>
            <form:password id="confirmPasswordField"
                           class="form-control" path="confirmedPassword"/>
            <spring:bind path="confirmedPassword">
                <c:if test="${status.error}">
                    <div class="alert alert-danger">
                        <form:errors path="confirmedPassword"/>
                    </div>
                </c:if>
            </spring:bind>
            <br>

        </div>

        <div class="col-sm-4"></div>
    </div>
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">

            <label for="emailField">Email studencki:</label>
            <form:input id="emailField" type="email"
                           class="form-control" path="email"/>
            <spring:bind path="email">
                <c:if test="${status.error}">
                    <div class="alert alert-danger">
                        <form:errors path="email"/>
                    </div>
                </c:if>
            </spring:bind>
            <br>

        </div>
        <div class="col-sm-4"></div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <br>
            <input type="submit" class="btn btn-primary btn-block" value="Zarejestruj">
        </div>

    </div>
    <div class="col-sm-4"></div>
</div>
</form:form>



<div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6">
        <div class="float-right">
            <br>
            <br>
            <a href="/" class="btn btn-info">
                <span class="glyphicon glyphicon-arrow-left"></span> Powrót do strony głównej
            </a>
        </div>
    </div>
</div>
<div class="col-sm-3"></div>

</body>

</html>
