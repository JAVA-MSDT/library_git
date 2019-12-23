<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>

<head>
    <title>${sessionScope.user.name}</title>
    <meta charset="utf-8">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tableStyle.css">

</head>

<body>

<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">
        <h1><fmt:message key="label.profile.page"/></h1>
        <hr>
        <table class="readerInfo">
            <tr>
                <td>
                    <h2><fmt:message key="label.name"/></h2>
                </td>
                <td>
                    <h3> ${sessionScope.user.name}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <h2><fmt:message key="label.email"/></h2>
                </td>
                <td>
                    <h3> ${sessionScope.user.email}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <h2><fmt:message key="label.login"/></h2>
                </td>
                <td>
                    <h3> ${sessionScope.user.login}</h3>
                </td>
            </tr>

        </table>
    </div>

</div>

<jsp:include page="${pageContext.request.contextPath}jsp/commoncode/footer.jsp" />
</body>

</html>
