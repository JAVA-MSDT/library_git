<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <title>
        <c:if test="${ not empty sessionScope.user}">
            ${sessionScope.user.name}
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <fmt:message key="label.title.epam"/>
        </c:if>
    </title>
    <meta charset="utf-8">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tableStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookCard.css">
    <script rel="script" src="${pageContext.request.contextPath}/js/displayBookOption.js"></script>
        <style type="text/css">

            body{
                background: black;
            }
            .forward-backward{
                width: 10%;
                height: 40px;
                padding: 5px;
            }
            .buttons{
                width: 100%;
                height: 100%;

            }
            .backward, .forward{
                width: 45%;
                height: 100%;
                float: left;
                margin-left: 4px;
            }

            .forward-btn{
                height: 100%;
                width: 100%;
                border-radius: 5px;
                background: black;
                color: white;

            }
        </style>
</head>
<body>
<%-- Special page for a non registered users or for regesterd users--%>


<jsp:include page="/jsp/commoncode/navigation.jsp"/>
<jsp:include page="/jsp/commoncode/scrollTop.jsp"/>



<div class="profileContainer" style="width: 90%">
    <div class="basicInfo">
        <h1><fmt:message key="label.book.store"/></h1>
        <hr>
        <%-- Adding - sorting - seraching bar --%>

        <div class="main-row">
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
                                <option value=""><fmt:message key="label.sort.by"/></option>
                                <option value="book_title"><fmt:message key="label.name"/></option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>

            <div class="add-column">
                <form name="sort-book" action="controller" method="post">
                    <input type="hidden" name="command" value="sort-book">
                    <div class="row-option">
                        <div class="option-col">
                            <select id="display-option" class="select-option" name="type" onchange="displayBook()">
                                <option value="displayCard"><fmt:message key="label.display.card"/></option>
                                <option value="displayTable"><fmt:message key="label.display.table"/></option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <c:choose>
            <c:when test="${param.operationStatus eq 'confirmed'}">
                <h2 class="permission" style="color: green"><fmt:message key="message.book.order.done"/></h2> <br>
            </c:when>
        </c:choose>
<div class="forward-backward">
                    <div class="buttons">

                        <div class="backward">
                            <form name="display-book" action="controller" method="post">
                                <input type="hidden" name="command" value="display-book">
                                <input type="hidden" name="backward" value="backward">
                                <input class="forward-btn" type="submit" value="Backward"/>
                            </form>
                        </div>

                        <div class="forward">
                                <form name="display-book" action="controller" method="post">
                                    <input type="hidden" name="command" value="display-book">
                                    <input type="hidden" name="forward" value="forward">
                                    <input class="forward-btn" type="submit" value="Forward"/>
                                </form>

                        </div>
                    </div>
                </div>
        <%-- in case of search for a book and it is not exist the below message will be displayed
    instead of book list ,,,, Display the book List as Cards --%>
        <div id="display-book-card" style="display: block">
        <c:choose>
            <c:when test="${not empty requestScope.bookNotExist}">
                <h2 class="permission" style="color: brown"><fmt:message key="message.book.not.exist"/></h2> <br>
            </c:when>
            <c:otherwise>
                <%--Book Cards --%>
                <div class="container">
                    <c:forEach var="bookList" items="${requestScope.bookList}" varStatus="loop">
                        <div class="book-card">
                            <div class="card-container">
                                <img src="${pageContext.request.contextPath}/img/effective.jpg" alt="Avatar" class="book-image">
                                <div class="name-overlay">
                                    <div class="book-info">
                                        <div class="book-name">
                                            <h3>${bookList.name}</h3>
                                        </div>
                                        <hr>
                                        <div class="book-row">
                                            <div class="book-available">
                                                <c:if test="${bookList.quantity > 0}">
                                                    <p><fmt:message key="label.available"/></p>
                                                </c:if>
                                                <c:if test="${bookList.quantity == 0}">
                                                    <p><fmt:message key="label.not.available"/></p>
                                                </c:if>
                                            </div>
                                            <c:if test="${sessionScope.user.role eq 'LIBRARIAN' or sessionScope.user.role eq 'ADMIN'}">
                                                <div class="book-quantity">
                                                    <p> ${bookList.quantity}</p>
                                                </div>
                                            </c:if>

                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="book-row">
                                <div class="view-book">
                                    <form name="view-book" action="controller" method="post">
                                        <input type="hidden" name="command" value="view-book">
                                        <input class="book-btn" type="submit" name="view"
                                               value="<fmt:message key="button.view"/> "/>
                                        <input type="hidden" name="book_id" value="<c:out value="${bookList.id}"/>"/>
                                    </form>
                                </div>
                                <c:if test="${sessionScope.user.role eq 'LIBRARIAN' or sessionScope.user.role eq 'ADMIN'}">

                                    <div class="edit-book">
                                        <form name="administration-edit-book" action="controller" method="post">
                                            <input type="hidden" name="command" value="administration-edit-book">
                                            <input class="book-btn" type="submit" name="view"
                                                   value="<fmt:message key="button.edit"/> "/>
                                            <input type="hidden" name="book_id" value="<c:out value="${bookList.id}"/>"/>
                                        </form>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        </div>

        <%-- in case of search for a book and it is not exist the below message will be displayed
    instead of book list ,,,, Display the book List as Table --%>
        <div id="display-book-table" style="display: none">
        <%-- Display List --%>
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
                                    <h3><fmt:message key="label.name"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.available"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="button.view"/></h3>
                                </th>

                            </tr>
                            <c:forEach varStatus="loop" var="bookList" items="${requestScope.bookList}">
                            <tr>
                                <td>
                                    <h4> ${loop.count} </h4>
                                </td>
                                <td>
                                    <h4> ${bookList.name}</h4>
                                </td>
                                <td>
                                    <h4>
                                    <div>
                                    <c:if test="${bookList.quantity > 0}">
                                        <fmt:message key="label.available"/>
                                    </c:if>
                                    <c:if test="${bookList.quantity == 0}">
                                        <fmt:message key="label.not.available"/>
                                    </c:if>
                                </div>
                                </h4>
                                </td>
                                <td>
                                    <div class="view-book">
                                        <form name="view-book" action="controller" method="post">
                                            <input type="hidden" name="command" value="view-book">
                                            <input class="book-btn" type="submit" name="view"
                                                   value="<fmt:message key="button.view"/> "/>
                                            <input type="hidden" name="book_id" value="<c:out value="${bookList.id}"/>"/>
                                        </form>
                                    </div>
                                </td>
                                </c:forEach>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        </div>
    </div>

</div>
<jsp:include page="/jsp/commoncode/footer.jsp"/>
</body>

</html>
