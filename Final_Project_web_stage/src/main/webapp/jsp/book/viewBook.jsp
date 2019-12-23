<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="now" value="new java.util.Date();"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>
        ${requestScope.book.name}
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookView.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formStyle.css"/>
    <script rel="script" src="${pageContext.request.contextPath}/js/orderValidator.js"></script>
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="main-container">
    <div class="tiny-header">
        <p> ${requestScope.book.name} </p>
    </div>

    <c:choose>
        <c:when test="${not empty requestScope.invalidLogin}">
            <h2 class="permission"><fmt:message key="message.login.register"/></h2> <br>
        </c:when>
        <c:when test="${not empty requestScope.operationStatus}">
            <h2 class="permission" style="color: darkred"><fmt:message key="message.book.order.not.done"/></h2> <br>
        </c:when>
    </c:choose>

    <%-- Book view --%>
    <div class="book-container">
        <div class="book-img">
            <img alt="Effective Java" src="../../img/effective.jpg"/>

        </div>
        <div class="book-description">
            <div class="book-title">
                <h2> ${requestScope.book.name} </h2>
            </div>
            <div class="book-author">
                <h2>By: <span>Joshua Bloch</span></h2>
            </div>
            <div class="about-book"><h3> About the Book </h3></div>
            <div class="book-descr">
                <p>&nbsp;&nbsp;&nbsp;&nbsp;It is a long established fact that a reader will be distracted by the
                    readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has
                    a more-or-less normal distribution of letters, as opposed to using 'Content here, content here',
                    making it look like readable English. Many desktop publishing packages and web page editors now use
                    Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                    still in their infancy. Various versions have evolved over the years, sometimes by accident,
                    sometimes on purpose (injected humour and the like). </p>
            </div>
            <div class="book-info">
                <table class="book-table-info">
                    <tr>
                        <th colspan="2"><h3>Author Info</h3></th>
                    </tr>
                    <tr>
                        <th> Nationality</th>
                        <td> earth</td>
                    </tr>
                    <tr>
                        <th>Birth date</th>
                        <td>0000</td>
                    </tr>
                    <tr>
                        <th>Works</th>
                        <td> Link</td>
                    </tr>
                    <tr>
                        <th colspan="2"><h3>Edition Notes:</h3></th>
                    </tr>
                    <tr>
                        <th> Edition Info:</th>
                        <td> First Edition</td>
                    </tr>
                    <tr>
                        <th>Genre:</th>
                        <td> Programming</td>
                    </tr>
                    <tr>
                        <th>Copyright Date:</th>
                        <td> 2000</td>
                    </tr>
                </table>
            </div>
            <div class="order-button-container" style="margin-top: 25px">
                <form name="order-book" action="controller" method="post">
                    <input type="hidden" name="command" value="order-book">
                    <input style="width: 200px" class="edit" type="submit" name="order"
                           value="<fmt:message key="button.order"/> "/>
                    <input type="hidden" name="book_id" value="<c:out value="${requestScope.book.id}"/>"/>
                </form>
            </div>
        </div>
    </div>

    <!-- if the user is logged in and he wants to order a book, this part wil be display -->

    <c:if test="${not empty requestScope.display}">
        <div class="container">

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

            <div class="editContainerForm">
                <form id="librarianOrderForm" name="confirm-order" action="controller" method="post">
                    <input type="hidden" name="command" value="confirm-order">
                    <input type="hidden" name="book_id" value="<c:out value="${requestScope.book.id}"/>"/>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.date"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="date" name="order_date" id="order-date" required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.return.date"/></h3>
                            <p id="returning-date-message" style="display: none"><fmt:message
                                    key="validation.order.returning.date"/></p>
                        </div>
                        <div class="inputCol">
                            <input type="date" name="returning_date" id="returning-date" required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.order.reading.place"/></h3>
                        </div>
                        <div class="inputCol">
                            <select id="readingPlace" name="reading_place">
                                <option value="HOME"><fmt:message key="label.order.home"/></option>
                                <option value="HALL"><fmt:message key="label.order.hall"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <input style="width: 200px" class="edit" onclick="return orderFormValidation()" type="submit" name="order"
                               value="<fmt:message key="button.confirm"/> "/>
                    </div>
                </form>
            </div>
        </div>
    </c:if>

    <hr>

    <%-- Optional for the users to leave a comment on the specified book--%>
    <div class="container" style="margin-bottom: 20px">
        <div class="add-comment">
            <h2> Add a Comment </h2>
        </div>
        <div class="editContainerForm">
            <form id="comment" name="comment" action="controller" method="post">
                <input type="hidden" name="command" value="confirm-comment">
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.name"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="text" name="commenter-name" value="">
                    </div>
                </div>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.email"/></h3>
                    </div>
                    <div class="inputCol">
                        <input type="text" name="commenter-email" value="">
                    </div>
                </div>
                <div class="row">
                    <div class="labelCol">
                        <h3 class="label"><fmt:message key="label.comment"/></h3>
                    </div>
                    <div class="inputCol">
                        <textarea id="subject" name="subject" placeholder=" <fmt:message key="label.write"/> "
                                  style="height:200px"></textarea>
                    </div>
                </div>
                <div class="row">
                    <input type="submit" value="<fmt:message key="button.submit"/> ">
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp" />
</body>
</html>
