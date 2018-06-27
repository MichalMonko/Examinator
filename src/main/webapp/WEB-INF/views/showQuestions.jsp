<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Hello, world!</title>
</head>

<body class="bg-dark">

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
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>

</html>
