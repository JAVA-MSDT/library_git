<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
</head>
<body>
<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer" style="height: 200px">

    <h1><fmt:message key="message.nullPage"/></h1>
</div>
<jsp:include page="/jsp/commoncode/footer.jsp"/>
</body>
</html>