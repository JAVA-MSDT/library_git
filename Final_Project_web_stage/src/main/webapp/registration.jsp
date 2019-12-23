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

<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="loginContainer">
    <div class="form" style="width: 50%">

        <img src="${pageContext.request.contextPath}img/epam.jpg" width="300" height="100">

        <h2><fmt:message key="message.registration"/></h2>
        <br>
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
            <input type="hidden" name="command" value="registration"/>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.name"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="text" name="name" id="user-name"
                               pattern="([\w\s]{0,30})$" placeholder="<fmt:message key="validation.user.name"/>" required>
                    </div>
                </div>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.reader.last.name"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="text" name="last_name" id="user-last-name"
                               pattern="([\w\s]{0,30})$" placeholder="<fmt:message key="validation.user.name"/>" required>
                    </div>
                </div>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.email"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="email" name="email" id="email"
                               pattern="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
                               placeholder="<fmt:message key="validation.user.email"/>" required>
                    </div>
                </div>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.login"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="text" name="login" id="user-login"
                               pattern="^([a-zA-Z]{2,10})$" placeholder="<fmt:message key="validation.user.login"/>" required>
                    </div>
                </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.password"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="text" name="password" id="user-password"
                                   pattern="^([a-zA-Z0-9@*#]{4,10})$"
                                   placeholder="<fmt:message key="validation.user.password"/>" required>
                        </div>
                    </div>
            <br>
                <div class="row">
                    <input type="submit" onclick="return userFormValidation()" value="<fmt:message key="button.submit"/>">
                </div>

        </form>
    </div>
</div>
<jsp:include page="${pageContext.request.contextPath}jsp/commoncode/footer.jsp" />
</body>
</html>