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
    <title>Panel Użytkownika</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body style="padding-top: 100px" class="bg-dark">

<%@include file="navbar.jsp" %>

<div class="container text-white items-center">


    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger">
                    <strong>Błąd formularza! </strong>
                        ${errorMessage}
                </div>
            </c:if>

            <c:if test="${successMessage != null}">
                <div class="alert alert-success">
                    <strong>Sukces! </strong>
                        ${successMessage}
                </div>
            </c:if>
        </div>
        <div class="col-sm-3"></div>
    </div>

    <div class="row">

        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">

            <div class="card bg-dark text-center items-center" style="max-width: 50%; margin-left: auto; margin-right: auto">
                <img class="card-img-top"
                     src="${pageContext.request.contextPath}/resources/icons/userIcon.png"
                     alt="userIcon">
                <div class="card-body">
                    <h3 class="card-title">
                        ${username}
                    </h3>
                    <h5 class="card-text">Status konta: ${userRole}</h5>
                </div>
            </div>

            <br>

        </div>
    </div>

    <div class="row">

        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">

            <div class="text-center">
                <h4>Zmiana hasła:</h4>
            </div>
            <hr>

            <form:form action="/authentication/changePassword" method="post">
                <label for="newPasswordField">Nowe hasło:</label>
                <br>
                <input id="newPasswordField" type="password" class="form-control" name="newPassword"
                       min="2" max="30" required>
                <br>
                <label for="newPasswordConfirmField">Potwierdź hasło:</label>
                <br>
                <input id="newPasswordConfirmField" type="password" class="form-control"
                       name="newPasswordConfirm" min="2" max="30" required>
                <br>
                <input type="submit" class="btn btn-block btn-primary" value="Zmień hasło">
            </form:form>
        </div>
        <div class="col-sm-3">
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

            <div class="text-center">
                <h4>Usuń konto:</h4>
            </div>
            <hr>

            <form:form action="/authentication/removeUser">
                <input type="submit" class="btn btn-primary btn-block"
                       onclick="confirm('Na pewno chcesz usunąć konto?')" value="Usuń konto">
            </form:form>
        </div>
        <div class="col-sm-3"></div>
    </div>


    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="float-right">
                <br>
                <br>
                <a href="/" class="btn btn-info">
                    <span class="glyphicon glyphicon-arrow-left"></span> Powrót do strony głównej
                </a>
            </div>
        </div>
        <div class="col-sm-2">
        </div>

    </div>
    <br>
    <br>
    <br>
</div>
</body>

</html>
