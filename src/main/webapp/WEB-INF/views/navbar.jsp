<%@ page import="com.warchlak.config.security.AuthenticationTracker" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="loggedUser" value="<%=AuthenticationTracker.getLoggedUsername()%>"/>
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
                        <input type="submit" class="btn btn-default" value="Szukaj">
                    </div>
                </div>
            </form:form>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${loggedIn == true}">
                    <li><a href="/user/accountPage"><span class="glyphicon glyphicon-user"></span>${loggedUser}</a></li>
                    <li><a id="logoutLink" href="#" onclick="logout()"><span
                            class="glyphicon glyphicon-log-in"></span>Wyloguj</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="/authentication/signUp"><span class="glyphicon glyphicon-user"></span>Załóż konto</a></li>
                    <li><a href="/authentication/login"><span class="glyphicon glyphicon-log-in"></span>Zaloguj</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
