<%@ page import="com.warchlak.config.security.AuthenticationTracker" %><%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-24
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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

        <script type="text/javascript" src="/resources/javascript/logoutHandler.js"></script>

</head>

<body class="bg-dark" style="padding-top: 70px;">

<c:set var="loggedIn" value="<%=AuthenticationTracker.isAuthenticated()%>"/>
<form:form id="logout_form" method="post" action="/logout"/>

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
            <c:choose>
                <c:when test="${loggedIn == true}">
                    <li><a href="#" id="logoutLink" onclick="logout()"><span class="glyphicon glyphicon-log-in"></span>Wyloguj</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/authentication/login"><span class="glyphicon glyphicon-log-in"></span>Zaloguj</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<div class="container-fluid bg-dark text-white">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

            <c:forEach var="major" items="${majors}">
                <a class="text-center text-white btn btn-primary btn-large btn-block"
                   data-toggle="collapse" href="#${major.id}">${major.name}</a>

                <div id="${major.id}" class="collapse">
                    <c:forEach var="course" items="${major.courses}">

                        <c:url var="addQuestionForm" value="/question/showAddForm">
                            <c:param name="courseId" value="${course.id}"/>
                        </c:url>
                        <c:url var="addQuestionFiles" value="/question/showAddFiles">
                            <c:param name="courseId" value="${course.id}"/>
                        </c:url>

                        <c:url var="showQuestions" value="/showQuestions">
                            <c:param name="courseId" value="${course.id}"/>
                        </c:url>

                        <div class="dropdown">
                            <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                               data-toggle="dropdown">${course.name}<span class="caret"></span></a>
                            <ul class="dropdown-menu">

                                <sec:authorize access="hasAnyRole('ADMIN','CONTRIBUTOR')">
                                <li>
                                    <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                       href="${addQuestionForm}">Dodaj pytanie (formularz)</a>
                                <li>
                                    <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                       href="${addQuestionFiles}">Dodaj pytanie (Z plików)</a>
                                    </sec:authorize>

                                <li>
                                    <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                       href="${showQuestions}">Wyświetl pytania</a>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="col-sm-3"></div>


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

    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
        </div>
        <div class="col-sm-2">
            <br><br>
            <a href="/" class="btn btn-info float-right">
                <span class="glyphicon glyphicon-arrow-left"></span> Strona główna
            </a>
        </div>
    </div>
</div>
</body>

</html>

