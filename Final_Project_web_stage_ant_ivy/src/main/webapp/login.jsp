<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title> <fmt:message key="label.title.epam"/> </title>
    <meta charset="utf-8">
    <meta name="description" content="Epam Library">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>

<div class="loginContainer">
    <div class="form">

        <img src="${pageContext.request.contextPath}/img/epam.jpg" width="100" height="100">

        <h2><fmt:message key="message.welcome.back"/></h2>

        <div class="error-message">
            <c:if test="${not empty requestScope.invalidLogin}">
                <br>
                <h2 style="color: brown"><fmt:message key="message.loginError"/></h2>
            </c:if>
            <c:if test="${not empty requestScope.blocked}">
                <h2 style="color: brown"><fmt:message key="message.blockedUser"/></h2>
            </c:if>
        </div>

        <form name="LoginForm" action="controller" method="POST">
            <input type="hidden" name="command" value="login"/>
            <input type="text" placeholder=
            <fmt:message key="label.login"/> name="user_login" pattern="[a-zA-z0-9]+"/>
            <input type="password" placeholder=
            <fmt:message key="label.password"/> name="user_password" pattern="^([a-zA-Z0-9@*#]{4,10})$"/>
            <input type="submit" value=
            <fmt:message key="label.login"/> id="btnLog">
        </form>
        <div class="register">
						<span class="txt1">
							<fmt:message key="message.no.account"/>
						</span>

            <a class="regbtn" href="${pageContext.request.contextPath}/registration.jsp">
                <fmt:message key="label.register"/>
            </a>
        </div>

    </div>
</div>
<jsp:include page="jsp/commoncode/footer.jsp"/>
<%--
<jsp:include page="${pageContext.request.contextPath}jsp/commoncode/footer.jsp" />
--%>
</body>
</html>