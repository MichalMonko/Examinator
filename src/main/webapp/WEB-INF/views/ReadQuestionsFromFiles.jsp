<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Hello, world!</title>

    <script type="text/javascript" src="/resources/javascript/logoutHandler.js"></script>
</head>

<body class="bg-dark" style="padding-top: 70px;">

<security:authorize access="isAuthenticated()" var="loggedIn"/>
<form:form id="logout_form" method="post" action="/logout"/>

<%@include file="navbar.jsp" %>

<div class="container-fluid bg-dark text-white">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
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
        <div class="col-sm-4"></div>
    </div>

    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <h2>Kurs: ${course.name}</h2>
            <br>

            <label class="btn btn-default btn-block btn-lg text-center " for="file_input"><br><br><h4>Wybierz pliki</h4>
                <br>
                <input type="file" class="custom-file-input" id="file_input" multiple onchange="readFiles()">
            </label>
            <br>
            <div class="col-2"></div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-lg-8">
            <form:form modelAttribute="questionsAsJson" action="/question/addFromFiles" id="jsonAddForm"
                       acceptCharset="UTF-8">
                <input type="hidden" id="jsonData" name="questionsAsJson" value="">
                <input type="hidden" id="courseId" name="courseId" value="${courseId}">
            </form:form>
            <div class="btn btn-primary btn-lg" id="addFilesButton">Dodaj pliki</div>
            <div class="float-right">
                <a href="/showCoursesList" class="btn btn-info">
                    <span class="glyphicon glyphicon-arrow-left"></span> Powrót do listy
                </a>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-lg-8">
            <div id="file_status"></div>
        </div>
    </div>

</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="/resources/javascript/ParseQuestionFromFilesToJson.js"></script>
</body>

</html>

