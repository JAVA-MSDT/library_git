<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">


<c:choose>
    <%-- if the user is not in the system the footer will be as below--%>
    <c:when test="${sessionScope.user == null}">
        <footer class="footer">
            <div class="footLinks">
                <div class="footMenu">
                    <ul>
                        <li><a href="../../index.jsp"><fmt:message key="nav.home"/> </a></li>
                        <li><a href="../../login.jsp"><fmt:message key="label.login"/> </a></li>
                        <li><a href="#"><fmt:message key="nav.news"/> </a></li>
                        <li><a href="#"><fmt:message key="nav.contact"/> </a></li>
                        <li><a href="#">About</a></li>
                    </ul>
                </div>
                <div class="socialMedia">
                    <ul>
                        <li><a href="https://www.facebook.com/EPAM.Belarus/" target="_blank">Facebook</a></li>
                        <li><a href="https://twitter.com/epam_minsk" target="_blank">Twitter</a></li>
                        <li><a href="https://www.linkedin.com/company/epam-systems/" target="_blank">Linkedin</a></li>
                    </ul>

                </div>
            </div>
            <div class="copyright">
                <p>&#64; Copyright: Epam System Belarus 2019</p>
                <p> <fmt:message key="message.dev.des.by"/> <a href="https://github.com/JAVA-MSDT">Ahmed Samy</a></p>
                <br/>
                <c:if test="${sessionScope.version != null}">
                    <p>"${sessionScope.version}" </p>
                </c:if>
                <c:if test="${sessionScope.timeStamp != null}">
                    <p>"${sessionScope.timeStamp}" </p>
                </c:if>
            </div>
        </footer>
    </c:when>
    <%-- otherwise if the user in the system the footer will be as his own page footer--%>

    <c:otherwise>
        <footer class="profileFooter">
            <p><fmt:message key="message.for.help"/> <a href="#"><fmt:message key="label.contact.us"/> </a></p>
            <p>
            <p>&#64; Copyright: Epam System Belarus 2019</p>
            <p> <fmt:message key="message.dev.des.by"/> <a href="https://github.com/JAVA-MSDT">Ahmed Samy</a></p>
            <br/>
            <c:if test="${sessionScope.version != null}">
                <p> Building Version: ${sessionScope.version} </p>
            </c:if>
            <c:if test="${sessionScope.timeStamp != null}">
                <p> Building Time: ${sessionScope.timeStamp} </p>
            </c:if>
        </footer>
    </c:otherwise>
</c:choose>


