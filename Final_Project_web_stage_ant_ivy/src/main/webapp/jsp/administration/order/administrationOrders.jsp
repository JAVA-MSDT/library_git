<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>

<head>
    <title><fmt:message key="label.title.epam"/></title>
    <meta charset="utf-8">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tableStyle.css">
    <script rel="script" src="${pageContext.request.contextPath}/js/fieldExchanger.js"></script>

</head>

<body>
<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">
        <h1><fmt:message key="label.order.list"/></h1>
        <hr>

        <%-- Adding - sorting - seraching bar --%>

        <div class="main-row">
            <div class="add-column">
                <form name="administration-edit-order" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-edit-order">
                    <input class="add-button" type="submit" name="edit" value="<fmt:message key="label.add.order"/>"/>
                </form>
            </div>
            <div class="search-column">
                <form name="administration-search-order" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-search-order">
                    <input class="search-field" id="search-field" type="text" name="query" required
                           placeholder="<fmt:message key="button.search"/> "/>

                    <select id="reading-place" style="display: none" class="search-field" name="query">
                        <option value="">
                            <fmt:message key="label.order.reading.place"/>
                        </option>
                        <option value="HOME">
                            <fmt:message key="label.order.home"/>
                        </option>
                        <option value="HALL">
                            <fmt:message key="label.order.hall"/>
                        </option>
                    </select>

                    <select class="select-option" id="select-option" name="type" onchange="changeSearchFieldType()">
                        <option value="bookName"><fmt:message key="label.book.title"/></option>
                        <option value="userName"><fmt:message key="label.user.name"/></option>
                        <option value="email"><fmt:message key="label.email"/></option>
                        <option value="orderDate"><fmt:message key="label.order.date"/></option>
                        <option value="returningDate"><fmt:message key="label.order.return.date"/></option>
                        <option value="readingPlace"><fmt:message key="label.order.reading.place"/></option>
                    </select>
                    <input class="submit-button" type="submit" value="<fmt:message key="button.search"/>"/>
                </form>
            </div>

            <div class="sort-column">
                <form name="administration-sort-order" action="controller" method="post">
                    <input type="hidden" id="command" name="command" value="administration-sort-order">
                    <div class="row-option">
                        <div class="option-col">
                            <select id="sort-option" class="select-option" name="type" onchange="this.form.submit()">
                                <option value=""><fmt:message key="label.sort.by"/></option>
                                <option value="bookName"><fmt:message key="label.book.title"/></option>
                                <option value="userName"><fmt:message key="label.user.name"/></option>
                                <option value="email"><fmt:message key="label.email"/></option>
                                <option value="orderDate"><fmt:message key="label.order.date"/></option>
                                <option value="returningDate"><fmt:message key="label.order.return.date"/></option>
                                <option value="readingPlace"><fmt:message key="label.order.reading.place"/></option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <%-- in case of search for an order and it is not exist the below message will be displayed
        instead of order list --%>
        <c:choose>
            <c:when test="${not empty requestScope.orderNotExist}">
                <h2 class="permission" style="color: brown"><fmt:message key="message.order.not.exist"/></h2> <br>
            </c:when>
            <c:otherwise>
                <%-- Order table --%>
        <div class="container">
            <div class="tableContainer">
                <table class="tableList">
                    <tr>
                        <th>
                            <h3><fmt:message key="label.id"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.name"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.email"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.book.title"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.order.date"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.order.return.date"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.order.reading.place"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="label.order.returned"/></h3>
                        </th>
                        <th>
                            <h3><fmt:message key="button.edit"/></h3>
                        </th>
                    </tr>
                    <c:forEach varStatus="loop" var="row" items="${requestScope.orderList}">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${row.userName}</td>
                            <td>${row.userEmail}</td>
                            <td>${row.bookName}</td>
                            <td>
                                <fmt:formatDate value="${row.orderDate}" type="date"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${row.returningDate}" type="date"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${row.readingPlace eq 'HOME'}">
                                        <fmt:message key="label.order.home"/>
                                    </c:when>
                                    <c:when test="${row.readingPlace eq 'HALL'}">
                                        <fmt:message key="label.order.hall"/>
                                    </c:when>
                                </c:choose>
                            <td>
                                <c:choose>
                                    <c:when test="${row.bookReturned eq false}">
                                        <fmt:message key="label.false"/>
                                    </c:when>
                                    <c:when test="${row.bookReturned eq true}">
                                        <fmt:message key="label.true"/>
                                    </c:when>
                                </c:choose>
                            <td>
                                <form name="administration-edit-order" action="controller" method="post">
                                    <input type="hidden" name="command" value="administration-edit-order">
                                    <input class="edit" type="submit" name="edit"
                                           value="<fmt:message key="button.edit"/>"/>
                                    <input type="hidden" name="order_id" value="<c:out value="${row.id}"/>"/>
                                    <input type="hidden" name="bookName" value="<c:out value="${row.bookName}"/>"/>
                                    <input type="hidden" name="userName" value="<c:out value="${row.userName}"/>"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
            </c:otherwise>
        </c:choose>

    </div>

</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp" />
</body>

</html>
