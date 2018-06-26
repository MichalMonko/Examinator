<%--
  Created by IntelliJ IDEA.
  User: fff
  Date: 2018-06-24
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Hello, world!</title>

    <script type="text/JavaScript">

    </script>

</head>

<body class="bg-dark">
<div class="container-fluid bg-dark text-white">
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="panel-group">

                <c:forEach var="major" items="${majors}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="text-center text-white btn btn-primary btn-large btn-block"
                               data-toggle="collapse" href="#${major.id}">${major.name}</a>
                        </h4>
                    </div>

                    <div id="${major.id}" class="panel-collapse collapse">
                        <c:forEach var="course" items="${major.courses}">

                            <c:url var="addQuestionForm" value="/showAddQuestionForm">
                                <c:param name="courseId" value="${course.id}"/>
                            </c:url>
                            <c:url var="addQuestionFiles" value="/showAddQuestionFiles">
                                <c:param name="courseId" value="${course.id}"/>
                            </c:url>
                            <c:url var="showQuestions" value="/showAddQuestionForm">
                                <c:param name="courseId" value="${course.id}"/>
                            </c:url>

                            <div class="panel-body">
                                <div class="dropdown">
                                    <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                       data-toggle="dropdown">${course.name}<span class="caret"></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                               href="${addQuestionForm}">Dodaj pytanie (formularz)</a>
                                        <li>
                                            <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                               href="${addQuestionFiles}">Dodaj pytanie (Z plików)</a>
                                        <li>
                                            <a class="text-center text-white btn btn-default btn-large btn-block bg-light text-dark "
                                               href="${showQuestions}">Wyświetl pytania</a>
                                    </ul>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    </c:forEach>
                </div>
                <div class="col-sm-4"></div>
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

