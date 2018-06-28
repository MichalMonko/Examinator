<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Hello, world!</title>
</head>

<body style="padding-top: 70px;">

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
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>Zaloguj</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="jumbotron bg-dark text-white">
        <h1 class="text-center">Testowniki</h1>
    </div>
    <div class="row">
        <div class="col d-flex">
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/resources/icons/write_question.png"
                     alt="write question icon">
                <div class="card-body">
                    <h4 class="card-title">Dodaj nowe pytanie</h4>
                    <p class="card-text">
                        Dodaj pytanie do bazy
                    </p>
                    <a href="#" class="btn btn-primary">Dodaj pytanie</a>
                </div>
            </div>
        </div>
        <div class="col d-flex">
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/resources/icons/lookup_base_icon.png"
                     alt="lookup database icon">
                <div class="card-body">
                    <h4 class="card-title">Przeglądaj bazę</h4>
                    <p class="card-text">
                        Znajdź kurs i pytania
                    </p>
                    <a href="showCoursesList" class="btn btn-primary">Przeglądaj</a>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col">

        </div>
        <div class="col">

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
</body>

</html>