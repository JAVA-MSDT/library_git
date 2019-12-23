<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>Server Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
</head>
<body>
<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer" style="height:340px">
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <h1> <fmt:message key="message.server.not.in"/> </h1>
        </c:when>
        <c:when test="${empty requestScope.emptyList eq 'emptyList'}">
                    <h1> Book List Empty </h1>
                </c:when>
        <c:otherwise>
            <h1> <fmt:message key="message.server.error"/> </h1>
        </c:otherwise>
    </c:choose>

</div>
<jsp:include page="/jsp/commoncode/footer.jsp"/>
</body>
</html>