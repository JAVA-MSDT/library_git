<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>

<head>
    <title>Epam Library</title>
    <meta charset="utf-8">
    <meta name="author" content="Ahmed Samy">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tableStyle.css">
    <script rel="script" src="${pageContext.request.contextPath}/js/removeItem.js"></script>
</head>

<body>

<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">
        <h1><fmt:message key="label.reader.list"/></h1>
        <hr>

        <%-- Adding - sorting - seraching bar --%>

        <div class="main-row">
            <div class="add-column">
                <form name="administration-add-user" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-edit-user">
                    <input class="add-button" type="submit" name="edit" value="<fmt:message key="label.add.reader"/>"/>
                </form>
            </div>
            <div class="search-column">
                <form name="administration-search-user" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-search-user">
                    <input class="search-field" type="text" name="query" required
                           placeholder="<fmt:message key="button.search"/> "/>
                    <select class="select-option" name="type">
                        <option value="user_login"><fmt:message key="label.login"/></option>
                        <option value="user_email"><fmt:message key="label.email"/></option>
                    </select>
                    <input class="submit-button" type="submit" value="<fmt:message key="button.search"/>"/>
                </form>
            </div>
            <div class="sort-column">
                <form name="administration-sort-user" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-sort-user">
                    <div class="row-option">
                        <div class="option-col">
                            <select id="sort-option" class="select-option" name="type" onchange="this.form.submit()">
                                <option value=""><fmt:message key="label.sort.by"/></option>
                                <option value="first_name"><fmt:message key="label.name"/></option>
                                <option value="user_email"><fmt:message key="label.email"/></option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%-- in case of removing a user one of these messages will be displayed--%>

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


        <%-- in case of search for a user and it is not exist the below message will be displayed
        instead of user list --%>
        <c:choose>
            <c:when test="${not empty requestScope.userNotExist}">
                <h2 class="permission" style="color: brown; margin: 20px auto"><fmt:message
                        key="message.user.not.exist"/></h2> <br>
            </c:when>
            <c:otherwise>
                <%-- User table --%>
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
                                    <h3><fmt:message key="label.reader.last.name"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.email"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.login"/></h3>
                                </th>
                                <th>
                                    <h3><fmt:message key="label.reader.blocked"/></h3>
                                </th>

                                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                    <th>
                                        <h3><fmt:message key="label.reader.role"/></h3>
                                    </th>
                                </c:if>

                                <th>
                                    <h3><fmt:message key="button.edit"/></h3>
                                </th>

                                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                    <th>
                                        <h3><fmt:message key="button.remove"/></h3>
                                    </th>
                                </c:if>

                            </tr>

                            <c:forEach varStatus="loop" var="userList" items="${requestScope.userList}">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${userList.name}</td>
                                    <td>${userList.lastName}</td>
                                    <td>${userList.email}</td>
                                    <td>${userList.login}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${userList.blocked eq false}">
                                                <fmt:message key="label.false"/>
                                            </c:when>
                                            <c:when test="${userList.blocked eq true}">
                                                <fmt:message key="label.true"/>
                                            </c:when>
                                        </c:choose>

                                    </td>
                                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                        <td>
                                        <c:choose>
                                            <c:when test="${userList.role eq 'ADMIN'}">
                                                <fmt:message key="label.role.admin"/>
                                            </c:when>
                                            <c:when test="${userList.role eq 'LIBRARIAN'}">
                                                <fmt:message key="label.role.librarian"/>
                                            </c:when>
                                            <c:when test="${userList.role eq 'READER'}">
                                                <fmt:message key="label.role.reader"/>
                                            </c:when>
                                        </c:choose>
                                        </td>
                                    </c:if>
                                    <td>
                                        <form name="administration-edit-user" action="controller" method="post">
                                            <input type="hidden" name="command" value="administration-edit-user">
                                            <input class="edit" type="submit" name="edit"
                                                   value="<fmt:message key="button.edit"/>"/>
                                            <input type="hidden" name="user_id" value="<c:out value="${userList.id}"/>"/>
                                        </form>
                                    </td>

                                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                        <td>
                                            <form name="admin-remove-user" action="controller" method="post">
                                                <input type="hidden" name="command" value="admin-remove-user">
                                                <input type="hidden" id="remove-message" value="<fmt:message key="message.remove.item"/>">
                                                <input class="edit" onclick="return remove()" type="submit" name="edit"
                                                       value="<fmt:message key="button.remove"/> "/>
                                                <input type="hidden" name="user_id" value="<c:out value="${userList.id}"/>"/>
                                            </form>
                                        </td>
                                    </c:if>

                                </tr>
                            </c:forEach>

                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp"/>
</body>

</html>
