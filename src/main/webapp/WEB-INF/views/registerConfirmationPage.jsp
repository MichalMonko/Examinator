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
    <title>Potwierdzenie rejestracji</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body style="padding-top: 100px" class="bg-dark">

<%@include file="navbar.jsp" %>

<div class="text-white container items-center text-center">

    <c:choose>
        <c:when test="${errorMessage != null}">
            <h3><strong>Błąd: </strong> ${errorMessage}</h3>
        </c:when>

        <c:otherwise>
            <h3>Użytkownik pomyślnie aktywowany<h3>
        </c:otherwise>
    </c:choose>

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

