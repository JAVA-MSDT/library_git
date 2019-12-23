<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>

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

<%-- Just to display to the user his orders --%>
<div class="profileContainer">
    <div class="basicInfo">
        <h1>My Order:</h1>
        <hr>
        <div class="container">
            <div class="tableContainer">
                <table class="tableList">
                    <tr>
                        <th>
                            <h2><fmt:message key="label.id"/></h2>
                        </th>
                        <th>
                            <h2><fmt:message key="label.book.title"/></h2>
                        </th>
                        <th>
                            <h2><fmt:message key="label.order.date"/></h2>
                        </th>
                        <th>
                            <h2><fmt:message key="label.order.return.date"/></h2>
                        </th>
                        <th>
                            <h2><fmt:message key="label.order.reading.place"/></h2>
                        </th>
                    </tr>
                    <c:forEach varStatus="loop" var="orderList" items="${requestScope.orderList}">
                        <tr>
                            <td>
                                <h2> ${loop.count}</h2>
                            </td>
                            <td>
                             <form name="" action="controller" method="post">
                                  <input type="hidden" name="command" value="">
                                    <div class="row-option">
                                         <div class="">
                                            <select id="display-option" class="select-option" name="type">
                                                <c:forEach var="bookNameList" items="${orderList.bookName}">
                                                <h2> <option value=${bookNameList}> ${bookNameList} </option> </h2>
                                                </c:forEach>
                                            </select>
                                         </div>
                                   </div>
                               </form>
                            </td>
                            <td>
                                <h2>
                                    <fmt:formatDate value="${orderList.orderDate}" type="date"/>
                                </h2>
                            </td>
                            <td>
                                <h2>
                                    <fmt:formatDate value="${orderList.returningDate}" type="date"/>
                                </h2>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${orderList.readingPlace eq 'HOME'}">
                                        <fmt:message key="label.order.home"/>
                                    </c:when>
                                    <c:when test="${orderList.readingPlace eq 'HALL'}">
                                        <fmt:message key="label.order.hall"/>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

    </div>

</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp"/>
</body>

</html>
