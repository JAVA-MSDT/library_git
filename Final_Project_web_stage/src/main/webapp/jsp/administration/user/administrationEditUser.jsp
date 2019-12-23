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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountBodyStyle.css">
    <script rel="script" src="${pageContext.request.contextPath}/js/userValidator.js"></script>
</head>

<body>

<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/navigation.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/scrollTop.jsp"/>

<div class="profileContainer">
    <div class="basicInfo">

        <h1><fmt:message key="label.add.edit.user"/></h1>

        <%-- in case of updating an existing User or inserting a new User one of these messages will be displaye--%>

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
                        key="message.insert.user.not.done"/></h2> <br>
            </c:when>
        </c:choose>

        <%-- Server form validation--%>
        <c:if test="${not empty requestScope.validationList}">
            <br>
            <hr>
            <br>
            <c:forEach items="${requestScope.validationList}" var="validation">
                <c:choose>
                    <c:when test="${validation eq 'nameError'}">
                    <h3 style="color: firebrick"> <fmt:message key="validation.user.name"/> </h3>
                </c:when>
                    <c:when test="${validation eq 'lastNameError'}">
                        <h3 style="color: firebrick"> <fmt:message key="validation.user.name"/> </h3>
                    </c:when>
                    <c:when test="${validation eq 'emailError'}">
                        <h3 style="color: firebrick"> <fmt:message key="validation.user.email"/> </h3>
                    </c:when>
                    <c:when test="${validation eq 'loginError'}">
                        <h3 style="color: firebrick"> <fmt:message key="validation.user.login"/> </h3>
                    </c:when>
                    <c:when test="${validation eq 'passwordError'}">
                        <h3 style="color: firebrick"> <fmt:message key="validation.user.password"/> </h3>
                    </c:when>
                </c:choose>
            </c:forEach>
            <br>
            <hr>
        </c:if>

        <%-- User Form for adding or editting --%>

        <div class="container">
            <div class="editContainerForm">
                <form id="librarianReaderForm" name="administration-update-user" action="controller" method="post">
                    <input type="hidden" name="command" value="administration-update-user">
                    <input type="hidden" name="user_id" value="${requestScope.editUser.id}">
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.name"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="text" name="first_name" id="user-name"
                                   value="${not empty requestScope.editUser.name ? requestScope.editUser.name : ''}"
                                   pattern="([\w\s]{0,30})$" placeholder="<fmt:message key="validation.user.name"/>" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.reader.last.name"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="text" name="last_name" id="user-last-name"
                                   value="${not empty requestScope.editUser.lastName ? requestScope.editUser.lastName : ''}"
                                   pattern="([\w\s]{0,30})$" placeholder="<fmt:message key="validation.user.name"/>" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.email"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="email" name="user_email" id="email"
                                   value="${not empty requestScope.editUser.email ? requestScope.editUser.email : ''}"
                                   pattern="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
                                   placeholder="<fmt:message key="validation.user.email"/>" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.login"/></h3>
                        </div>
                        <div class="inputCol">
                            <input type="text" name="user_login" id="user-login"
                                   value="${not empty requestScope.editUser.login ? requestScope.editUser.login : ''}"
                                   pattern="^([a-zA-Z0-9]{2,10})$" placeholder="<fmt:message key="validation.user.login"/>" required>
                        </div>
                    </div>

                    <c:if test="${empty requestScope.editUser}">
                        <div class="row">
                            <div class="labelCol">
                                <h3 class="label"><fmt:message key="label.password"/></h3>
                            </div>
                            <div class="inputCol">
                                <input type="text" name="user_password" id="user-password"
                                       value="${not empty requestScope.editUser.password ? requestScope.editUser.password : ''}"
                                       pattern="^([a-zA-Z0-9@*#]{4,10})$"
                                       placeholder="<fmt:message key="validation.user.password"/>" required>
                            </div>
                        </div>
                    </c:if>


                    <c:choose>
                        <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                            <div class="row">
                                <div class="labelCol">
                                    <h3 class="label"><fmt:message key="label.reader.role"/></h3>
                                </div>
                                <div class="inputCol">
                                    <select name="user_role">
                                        <option value="ADMIN"
                                                <c:if test="${requestScope.editUser.role eq 'ADMIN'}"> selected = selected </c:if>>
                                            <fmt:message key="label.role.admin"/></option>
                                        <option value="LIBRARIAN"
                                                <c:if test="${requestScope.editUser.role eq 'LIBRARIAN'}"> selected = selected </c:if>>
                                            <fmt:message key="label.role.librarian"/></option>
                                        <option value="READER"
                                                <c:if test="${requestScope.editUser.role eq 'READER'}"> selected = selected </c:if>>
                                            <fmt:message key="label.role.reader"/></option>
                                    </select>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${sessionScope.user.role eq 'LIBRARIAN'}">
                            <div class="row">
                                <div class="inputCol">
                                    <input type="hidden" name="user_role"
                                           value="${not empty requestScope.editUser.role ? requestScope.editUser.role : ''}">
                                </div>
                            </div>
                        </c:when>
                    </c:choose>

                    <div class="row">
                        <div class="labelCol">
                            <h3 class="label"><fmt:message key="label.reader.blocked"/></h3>
                        </div>
                        <div class="inputCol">
                            <select name="user_blocked">
                                <option value="false"
                                        <c:if test="${requestScope.editUser.blocked eq 'false'}"> selected = selected </c:if>>
                                    <fmt:message key="label.false"/></option>
                                <option value="true"
                                        <c:if test="${requestScope.editUser.blocked eq 'true'}"> selected = selected </c:if>>
                                    <fmt:message key="label.true"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <input type="submit" onclick="return userFormValidation()" value="<fmt:message key="label.update"/>">
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<jsp:include page="${pageContext.request.contextPath}/jsp/commoncode/footer.jsp"/>
</body>

</html>
