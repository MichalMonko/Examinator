<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-29
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logowanie</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 100px" class="bg-dark">


<%@include file="navbar.jsp" %>

<c:url value="/authentication/login" var="loginUrl"/>

<div class="text-white">
    <form:form action="${loginUrl}" method="post">

        <c:if test="${param.error != null}">
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <div class="alert alert-danger">
                        <strong>Błąd! </strong>Nieprawidłowa nazwa użytkownika lub hasło.
                    </div>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </c:if>

        <c:if test="${errorMessage != null}">
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <div class="alert alert-danger">
                        <strong>Błąd formularza! </strong>
                            ${errorMessage}
                    </div>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </c:if>


        <c:if test="${successMessage != null}">
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <div class="alert alert-success">
                        <strong>Sukces! </strong>
                            ${successMessage}
                    </div>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <label for="username">Nazwa użytkownika:</label>
                <input type="text" class="form-control" id="username" name="username"/>
                <br>
            </div>
            <div class="col-sm-4"></div>
        </div>

        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <label for="password">Hasło:</label>
                <input type="password" class="form-control" id="password" name="password"/>
                <br>
            </div>
            <div class="col-sm-4"></div>
        </div>


        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <br>
                <input type="submit" class="btn btn-primary btn-block" value="Zaloguj">
                <br>
            </div>
            <div class="col-sm-4"></div>
        </div>


    </form:form>
</div>


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
