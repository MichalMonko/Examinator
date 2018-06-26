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

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <title>Hello, world!</title>

    <script type="text/javascript" src="/resources/javascript/ParseQuestionFromFilesToJson.js"></script>

</head>

<body class="bg-dark">


<div class="container-fluid bg-dark text-white">

    <input type="hidden" id="courseId" value="${courseId}">

    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <h2>Kurs: ${course.name}</h2>
            <br>

            <div id="successIndicator"></div>

            <div class="custom-file">
                <input type="file" class="custom-file-input" id="file_input" multiple onchange="readFiles()">
                <label class="custom-file-label" for="file_input">Wybierz pliki</label>
                <br>
            </div>
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

