<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
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

    <script type="text/javascript" src="/resources/javascript/ParseQuestionFromFilesToJson.js"></script>

</head>

<body class="bg-dark" style="padding-top: 70px;">


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
            <form action="/search" class="form-inline" method="post" style="padding-top: 8px">
                <div class="input-group">
                    <input type="text" class="form-control input-group" name="searchValue">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-default">Szukaj</button>
                    </div>
                </div>
            </form>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span>Załóż konto</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>Zaloguj</a></li>
        </ul>
    </div>
</nav>

<div class="container-fluid bg-dark text-white">

    <input type="hidden" id="courseId" value="${courseId}">

    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <h2>Kurs: ${course.name}</h2>
            <br>

            <div id="successIndicator"></div>

            <label class="btn btn-default btn-block btn-lg text-center " for="file_input"><br><br><h4>Wybierz pliki</h4><br>
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
            <div class="btn btn-primary btn-lg" onclick="sendFiles();">Dodaj pliki</div>
            <div class="float-right">
                <a href="showCoursesList" class="btn btn-info">
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
</body>

</html>

