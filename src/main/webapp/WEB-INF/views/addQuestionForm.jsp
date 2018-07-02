<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-24
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
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

    <script type="text/javascript" src="/resources/javascript/addQuestion.js"></script>

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


<div class="container-fluid bg-dark text-white">
    <div class="row">
        <div class="col-lg-3"></div>
        <div class="col-lg-6">
            <h2>Kurs: ${course.name}</h2>

            <c:choose>
                <c:when test="${empty success}">
                </c:when>
                <c:when test="${success}">
                    <div class="alert alert-success" role="alert">
                        <strong>Sukces! </strong>Pytanie zostało z powodzeniem dodane do bazy.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" role="alert">
                        <strong>Niepowodzenie! </strong>Dodawanie pytania do bazy nie powiodło się!
                    </div>
                </c:otherwise>
            </c:choose>

            <form:form id="question_add_form" action="question/add" method="post" modelAttribute="question">

                <input type="hidden" name="courseId" value="${courseId}">


                <div id="correctValues">
                    <input type="hidden" name="correct" value="0">
                    <input type="hidden" name="correct" value="0">
                    <input type="hidden" name="correct" value="0">
                    <input type="hidden" name="correct" value="0">
                </div>


                <div class="form-group">
                    <label class="control-label">Pytanie:</label>
                    <textarea name="content" rows="5" class="form-control" required></textarea>
                </div>

                <div class="form-group">
                    <label class="control-label">Odpowiedź A</label>
                    <br>
                    <div class="input-group">
                        <textarea name="answer" class="form-control" form="question_add_form" rows="5"></textarea>
                        <span class="input-group-addon btn btn-defalut"
                              style="background-color: lightcoral;" id="0button" onclick="switchValues(this)"></span>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label">Odpowiedź B</label>
                    <br>
                    <div class="input-group">
                        <textarea name="answer" class="form-control" form="question_add_form" rows="5"></textarea>
                        <span class="input-group-addon btn btn-defalut"
                              style="background-color: lightcoral;" id="1button" onclick="switchValues(this)"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label">Odpowiedź C</label>
                    <br>
                    <div class="input-group">
                        <textarea name="answer" class="form-control" form="question_add_form" rows="5"></textarea>
                        <span class="input-group-addon btn btn-defalut"
                              style="background-color: lightcoral;" id="2button" onclick="switchValues(this)"></span>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label">Odpowiedź D</label>
                    <br>
                    <div class="input-group">
                        <textarea name="answer" class="form-control" form="question_add_form" rows="5"></textarea>
                        <span class="input-group-addon btn btn-defalut"
                              style="background-color: lightcoral;" id="3button" onclick="switchValues(this)"></span>
                    </div>
                </div>


                <div class="form-group" id="submit_section">
                    <button id="add_question_button" type="button" class="btn btn-primary"
                            onclick="appendAnotherAnswerField()">Dodaj odpowiedz
                    </button>
                    <input type="submit" value="Wyslij pytanie" class="btn btn-default float-right">
                </div>
            </form:form>
        </div>
<div class="col-3"></div>
    </div>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <br><br>
            <a href="/showCoursesList" class="btn btn-info float-right">
                <span class="glyphicon glyphicon-arrow-left"></span> Powrót do listy
            </a>
        </div>
        <div class="col-2"></div>
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

