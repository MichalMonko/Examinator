<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-27
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class="container-fluid text-white text-center">

    <div class="row">
        <div class="col-sm-3"></div>

        <div class="col-sm-6">
            <h1>Pytania do kursu: ${course.name}</h1>
            <br>
            <c:forEach var="question" items="${course.questions}">

                <button class="btn btn-block btn-primary" data-toggle="collapse" data-target="#answers_${question.id}">
                        ${question.content}
                </button>

                <div id="answers_${question.id}" class="collapse">

                    <div class="panel-group">
                        <c:forEach var="answer" items="${question.answers}">

                            <c:choose>

                                <c:when test="${answer.correct}">
                                    <div class="panel panel-success">
                                        <div class="panel-heading"></div>
                                        <div class="panel-body text-dark">${answer.content}</div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div class="panel panel-danger">
                                        <div class="panel-heading"></div>
                                        <div class="panel-body text-dark">${answer.content}</div>
                                    </div>
                                </c:otherwise>

                            </c:choose>
                        </c:forEach>
                    </div>

                </div>

            </c:forEach>
        </div>
        <div class="col-sm-3">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <br><br>
            <a href="showCoursesList" class="btn btn-info float-right">
                <span class="glyphicon glyphicon-arrow-left"></span> Powrót do listy
            </a>
        </div>
        <div class="col-sm-2"></div>
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
