<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<body>
<nav class="navbar navbar-inverse navbar-expand-lg navbar-fixed-top">

    <div class="container-fluid" style="display: inline-block">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Testowniki</a>
        </div>

        <ul class="nav navbar-nav">
            <li><a href="/">Strona główna</a></li>
            <li><a href="/showCoursesList">Przeglądaj kursy</a></li>
        </ul>

        <div class="nav navbar-nav">
            <form:form action="/showCoursesList" class="form-inline" method="post" style="padding-top: 8px">
                <div class="input-group">
                    <input name="searchName" type="text" class="form-control input-group">
                    <div class="input-group-append">
                        <input type="submit" class="btn btn-default">Szukaj
                    </div>
                </div>
            </form:form>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span>Załóż konto</a></li>
            <c:choose>
                <c:when test="${loggedIn == true}">
                    <li><a id="logoutLink" href="#" onclick="logout()"><span class="glyphicon glyphicon-log-in"></span>Wyloguj</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="/authentication/login"><span class="glyphicon glyphicon-log-in"></span>Zaloguj</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<form:form action="/authentication/registerUser" method="post" modelAttribute="userDTO">
    <label>Nazwa użytkownika:</label><form:input path="username"/>
    <br>
    <label>Email studencki:</label><form:input path="email"/>
    <br>
    <label>Hasło:</label><form:input path="password"/>
    <br>
    <label>Potwierdź hasło:</label><form:input path="confirmedPassword"/>
    <br>
    <input type="submit" value="Zarejestruj">
</form:form>

</body>

</html>
