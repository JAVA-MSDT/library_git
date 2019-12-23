<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navStyle.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>

<div class="top-nav" id="top-nav-id">

    <a href="../../index.jsp" class="active"> <fmt:message key="nav.home"/> </a>
    <c:if test="${sessionScope.user == null}">
        <a href="controller?command=display-book"> <fmt:message key="label.book.store"/> </a>
    </c:if>

    <c:choose>
        <%-- Menu bar for reader only--%>
        <c:when test="${sessionScope.user.role eq 'READER'}">
            <a href="controller?command=profile" class="active"><fmt:message key="nav.profile"/></a>
            <a href="controller?command=display-book"><fmt:message key="label.book.store"/></a>
            <a href="controller?command=user-order"><fmt:message key="label.order.list"/></a>

        </c:when>
        <%-- Menu bar for Admin or Librarian only--%>
        <c:when test="${sessionScope.user.role eq 'LIBRARIAN' or sessionScope.user.role eq 'ADMIN'}">
            <a href="controller?command=profile" class="active"> ${sessionScope.user.role} <fmt:message
                    key="nav.profile"/></a>
            <a href="controller?command=administration-book-store"><fmt:message key="label.book.store"/></a>
            <a href="controller?command=administration-order-list"><fmt:message key="label.order.list"/></a>
            <a href="controller?command=administration-display-user"><fmt:message key="label.reader.list"/></a>
        </c:when>
    </c:choose>

    <a href="#"><fmt:message key="nav.news"/></a>
    <a href="#"><fmt:message key="nav.contact"/></a>
    <a href="#"><fmt:message key="nav.about"/></a>

    <div class="log-btn">
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <a href="../../login.jsp"> <fmt:message key="label.login"/> </a>
            </c:when>
            <c:otherwise>
                <a href="controller?command=logout"><fmt:message key="nav.logout"/></a>

            </c:otherwise>
        </c:choose>
    </div>
    <div style="float: right" class="dropdown">
        <a class="drop-btn"> <fmt:message key="nav.language"/> </a>
        <div class="dropdown-content">
            <form method="POST" action="controller">
                <input type="hidden" name="command" value="change-language"/>
                <button type="submit" name="language"
                        value="en"><fmt:message key="nav.language.en"/>
                </button>
                <button type="submit" name="language"
                        value="ar"><fmt:message key="nav.language.ar"/>
                </button>
                <button type="submit" name="language"
                        value="ru"><fmt:message key="nav.language.ru"/>
                </button>
            </form>
        </div>
    </div>
    <button class="icon" onclick="myFunction()">&#9776;</button>


</div>

<c:choose>
    <%-- if the user is not in the system the main page header will be as below--%>
    <c:when test="${sessionScope.user == null}">
        <header>
            <img src="${pageContext.request.contextPath}/img/libraryHall.jpg" class="headerImage" alt="header image">
        </header>
    </c:when>
    <%-- otherwise if the user  in the system the main page header will be as his own page header--%>
    <c:otherwise>
        <header>
            <img src="${pageContext.request.contextPath}/img/libraryHall.jpg" class="headerImage" alt="header image">
            <div class="avContainer">
                <div class="profile-img">
                <img class="avatar" src="${pageContext.request.contextPath}/img/profImage.jpg">
                <div class="overlay">
                    <a href="#">
                        <fmt:message key="button.edit"/>
                    </a>
                </div>
            </div>
                <div class="user-name">
                    <h2 class="name">${sessionScope.user.name}</h2>
                    <h2 class="name"> ${sessionScope.user.lastName}</h2>
                </div>
            </div>


        </header>
    </c:otherwise>
</c:choose>

<script>
    function myFunction() {
        var x = document.getElementById("top-nav-id");
        if (x.className === "top-nav") {
            x.style.display = "block";
            x.className += " responsive";
        } else {
            x.className = "top-nav";
        }
    }
</script>