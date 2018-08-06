<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-27
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/quiz_style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/javascript/logoutHandler.js"></script>

    <title>Hello, world!</title>

</head>

<body class="bg-dark" style="padding-top: 70px;">

<c:set var="loggedIn" value="<%=AuthenticationTracker.isAuthenticated()%>"/>
<form:form id="logout_form" method="post" action="/logout"/>

<%@include file="navbar.jsp" %>
<input type="hidden" id="jsonData" name="jsonData" value='${jsonString}'>

<div class="container-fluid text-white text-center">


    <div class="row">
        <div class="col-sm-2"></div>

        <div class="col-sm-8">

            <div class="quizComponent border border-light text-center" id="quizBoard">
                <div class="quizComponent bg-white text-dark" id="quizQuestion">
                    Here comes question
                </div>

                <div class="quizComponent text-center" id="quizAnswers">

                    <%--<div class="answer">--%>
                    <%--<p> Answer 1--%>
                    <%--</p>--%>
                    <%--</div>--%>

                    <%--<div class="answer">--%>
                    <%--<p> Answer 2</p>--%>
                    <%--</div>--%>
                    <%--<div class="answer">--%>
                    <%--<p> Answer 3</p>--%>
                    <%--</div>--%>
                    <%--<div class="answer">--%>
                    <%--<p> Answer 4</p>--%>
                    <%--</div>--%>

                </div>

                <div class="quizComponent border border-light" id="quizPanel">

                    <div class="panel_component" id="counter">
                        Ilość pozostałych wystąpień: 5
                    </div>

                    <div class="panel_component" id="timer">
                        Czas nauki: 25:34
                    </div>

                    <div class="questionNextButton btn btn-default w-25" id="nextQuestionButton">
                        Sprawdź
                    </div>
                </div>

                <div class="quizComponent" id="quizProgress">
                    Progres:
                    <div class="progress">
                        <div id="progressBar" class="progress-bar bg-success text-center" style="color: black;" role="progressbar"
                             aria-valuenow="25"
                             aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                </div>

            </div>


        </div>
    </div>

    <div class="col-sm-2"></div>

    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <br>
            <br>
            <a href="/showCoursesList" class="btn btn-info float-right">
                <span class="glyphicon glyphicon-arrow-left"></span> Powrót do listy
            </a>
        </div>
        <div class="col-sm-2"></div>
    </div>

    <br>
    <br>

</div>

<script type="text/javascript" src="/resources/javascript/parseQuestionData.js"></script>

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

