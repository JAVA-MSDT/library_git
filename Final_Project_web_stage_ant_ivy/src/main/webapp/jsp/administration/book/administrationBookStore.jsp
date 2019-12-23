<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <script rel="script" src="${pageContext.request.contextPath}/js/removeItem.js"></script>
</head>

<body>
<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">
        <h1><fmt:message key="label.book.store"/></h1>
        <hr>

        <%-- Adding - sorting - searching bar --%>
        <div class="main-row">
            <div class="add-column">
                <form name="administration-edit-book" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-edit-book">
                    <input class="add-button" type="submit" name="edit" value="<fmt:message key="label.add.book"/>"/>
                </form>
            </div>
            <div class="search-column">
                <form name="search-book" action="controller" method="post">
                    <input type="hidden" name="command" value="search-book">
                    <input class="search-field" type="text" name="query" required
                           placeholder="<fmt:message key="button.search"/> "/>
                    <select class="select-option" name="type">
                        <option value="book_title"><fmt:message key="label.book.title"/></option>
                    </select>
                    <input class="submit-button" type="submit" value="<fmt:message key="button.search"/>"/>
                </form>
            </div>

            <div class="sort-column">
                <form name="sort-book" action="controller" method="post">
                    <input type="hidden" name="command" value="sort-book">
                    <div class="row-option">
                        <div class="option-col">
                            <select id="sort-option" class="select-option" name="type" onchange="this.form.submit()">
                                <option value=""><fmt:message key="label.sort.by"/> </option>
                                <option value="book_title"><fmt:message key="label.book.title"/></option>
                                <option value="quantity"><fmt:message key="label.quantity"/></option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%-- in case of removing a book one of these messages will be displayed--%>
        <c:choose>
            <c:when test="${param.operationStatus eq 'removed'}">
                <h2 class="permission" style="color: green; margin: 10px auto"><fmt:message
                        key="message.remove.done"/></h2> <br>
            </c:when>
            <c:when test="${param.operationStatus eq 'removeFail'}">
                <h2 class="permission" style="color: brown; margin: 10px auto"><fmt:message
                        key="message.remove.fail"/></h2> <br>
            </c:when>

        </c:choose>


        <%-- in case of search for a book and it is not exist the below message will be displayed
        instead of book list --%>
        <c:choose>
            <c:when test="${not empty requestScope.bookNotExist}">
                <h2 class="permission" style="color: brown"><fmt:message key="message.book.not.exist"/></h2> <br>
            </c:when>
            <c:otherwise>

                <%-- Book Table --%>
                <div class="container">
                    <div class="tableContainer">
                        <table class="tableList">
                            <tr>
                                <th>
                                    <h3><fmt:message key="label.id"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.book.title"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.quantity"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="button.edit"/></h3>
                                </th>

                                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                    <th>
                                        <h3><fmt:message key="button.remove"/></h3>
                                    </th>
                                </c:if>

                            </tr>
                            <c:forEach varStatus="loop" var="bookList" items="${requestScope.bookList}">
                            <tr>
                                <td>
                                    <h4> ${loop.count} </h4>
                                </td>
                                <td>
                                    <h4> ${bookList.name}</h4>
                                </td>
                                <td><h4> ${bookList.quantity} </h4></td>
                                <td>
                                    <form name="administration-edit-book" action="controller" method="post">
                                        <input type="hidden" name="command" value="administration-edit-book">
                                        <input class="edit" type="submit" name="edit"
                                               value="<fmt:message key="button.edit"/> "/>
                                        <input type="hidden" name="book_id" value="<c:out value="${bookList.id}"/>"/>
                                    </form>
                                </td>

                                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                <td>
                                    <form name="admin-remove-book" action="controller" method="post">
                                        <input type="hidden" name="command" value="admin-remove-book">
                                        <input type="hidden" id="remove-message" value="<fmt:message key="message.remove.item"/>">
                                        <input class="edit" onclick="return remove()" type="submit" name="edit"
                                               value="<fmt:message key="button.remove"/> "/>
                                        <input type="hidden" name="book_id" value="<c:out value="${bookList.id}"/>"/>
                                    </form>
                                </td>
                                </c:if>

                                </c:forEach>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</div>
<jsp:include page="/jsp/commoncode/footer.jsp"/>
</body>

</html>