<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>

<head>
    <title><fmt:message key="label.title.epam"/></title>
    <meta charset="utf-8">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <script rel="script" src="${pageContext.request.contextPath}/js/orderValidator.js"></script>
</head>

<body>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">

        <h1><fmt:message key="label.add.edit.order"/></h1>
        <hr>

        <%-- in case of updating an existing book or inserting a new book one of these messages will be displaye--%>
        <c:choose>
            <%-- Update Info --%>
            <c:when test="${ param.operationStatus eq 'updated'}">
                <h2 class="permission" style="color: green; margin: 20px auto"><fmt:message
                        key="message.update.done"/></h2> <br>
            </c:when>
            <c:when test="${ param.operationStatus eq 'updateFail'}">
                <h2 class="permission" style="color: green; margin: 20px auto"><fmt:message
                        key="message.update.not.done"/></h2> <br>
            </c:when>

            <%-- Insert Info --%>
            <c:when test="${param.operationStatus eq 'inserted'}">
                <h2 class="permission" style="color: green; margin: 20px auto"><fmt:message
                        key="message.insert.done"/></h2> <br>
            </c:when>
            <c:when test="${param.operationStatus eq 'insertFail'}">
                <h2 class="permission" style="color: green; margin: 20px auto"><fmt:message
                        key="message.insert.not.done"/></h2> <br>
            </c:when>
            <%-- Removing info--%>
            <c:when test="${param.operationStatus eq 'removed'}">
                <h2 class="permission" style="color: green; margin: 10px auto"><fmt:message
                        key="message.remove.done"/></h2> <br>
            </c:when>
            <c:when test="${param.operationStatus eq 'removeFail'}">
                <h2 class="permission" style="color: brown; margin: 10px auto"><fmt:message
                        key="message.remove.fail"/></h2> <br>
            </c:when>
        </c:choose>

        <%-- Server form validation--%>
        <c:if test="${not empty requestScope.validationList}">
            <br>
            <hr>
            <br>
            <c:forEach items="${requestScope.validationList}" var="validation">
                <c:choose>
                    <c:when test="${validation eq 'returningDateOlder'}">
                        <h3 style="color: firebrick"><fmt:message key="validation.order.returning.date"/></h3>
                    </c:when>
                </c:choose>
            </c:forEach>
            <br>
            <hr>
        </c:if>

        <%-- Order Form for adding or editting --%>

        <div class="container">
            <div class="editContainerForm">
                <form id="librarianOrderForm" name="administration-update-order" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-update-order">
                    <input type="hidden" name="insert" value="${sessionScope.inserted}">
                    <input type="hidden" name="order_id" value="${requestScope.editOrder.orderId}">
                    <input type="hidden" id="returned-message" value="<fmt:message key="message.remove.order"/>">

                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.book.title"/></h3>
                        </div>
                        <div class="inputCol">
                            <c:choose>
                                <c:when test="${empty requestScope.editOrder.book}">
                                    <select name="book_id">
                                        <c:forEach var="book" items="${requestScope.bookList}">
                                            <option value="${book.id}"> ${book.name} </option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select name="book_id">
                                        <c:forEach var="book" items="${requestScope.bookList}">
                                            <option value="${book.id}"
                                            <c:if test="${book.name eq requestScope.bookName}"> selected = selected </c:if>>
                                                <%--    <c:if test="${requestScope.editOrder.book.id eq book.id}"> selected = selected </c:if>> --%>
                                                    ${book.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.email"/></h3>
                        </div>
                        <div class="inputCol">

                            <c:choose>
                                <c:when test="${empty requestScope.editOrder.user}">
                                    <select name="user_id">
                                        <c:forEach var="user" items="${requestScope.userList}">
                                            <option value="${user.id}"> ${user.email} </option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select name="user_id">
                                        <c:forEach var="user" items="${requestScope.userList}">
                                            <option value="${user.id}"
                                                    <c:if test="${requestScope.editOrder.user.id eq user.id}"> selected = selected </c:if> >
                                                    ${user.email}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.date"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="date" name="order_date" id="order-date"
                                   value="${not empty requestScope.editOrder.orderDate ? requestScope.editOrder.orderDate : ''}"
                                   placeholder="<fmt:message key="label.order.date"/>" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.return.date"/></h3>
                            <p id="returning-date-message" style="display: none"><fmt:message
                                    key="validation.order.returning.date"/></p>
                        </div>
                        <div class="inputCol">
                            <input type="date" name="returning_date" id="returning-date"
                                   value="${not empty requestScope.editOrder.returningDate ? requestScope.editOrder.returningDate : ''}"
                                   placeholder="<fmt:message key="label.order.return.date"/>" required>

                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.reading.place"/></h3>
                        </div>
                        <div class="inputCol">
                            <select id="librarianReadingPlace" name="reading_place">
                                <option value="HOME"
                                        <c:if test="${requestScope.editOrder.readingPlace eq  'HOME' }"> selected = "selected" </c:if>>
                                    <fmt:message key="label.order.home"/>
                                </option>
                                <option value="HALL"
                                        <c:if test="${requestScope.editOrder.readingPlace eq  'HALL' }"> selected = "selected" </c:if>>
                                    <fmt:message key="label.order.hall"/>
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.returned"/></h3>
                        </div>
                        <div class="inputCol">
                            <select id="book-returned" name="book_returned">
                                <option value=" ${requestScope.editOrder.bookReturned eq  'false' ? 'false' : 'false'}"
                                        <c:if test="${requestScope.editOrder.bookReturned eq  'false' }"> selected = "selected" </c:if>>
                                    <fmt:message key="label.false"/>
                                </option>
                                <option value="${requestScope.editOrder.bookReturned eq 'true' ? 'true' : 'true'}"
                                        <c:if test="${requestScope.editOrder.bookReturned eq  'true' }"> selected = "selected" </c:if>>
                                    <fmt:message key="label.true"/>
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <input type="submit" onclick=" return orderFormValidation()"
                               value="<fmt:message key="label.update"/> ">
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp"/>
</body>

</html>
