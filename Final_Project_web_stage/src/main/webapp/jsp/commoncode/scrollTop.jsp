
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/scrollTop.css"/>
<button onclick="toTop()" class="go-to-top" id="to-top">Top</button>


<script>
    window.onscroll = function() {

        if (document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
            document.getElementById("to-top").style.display = "block";
        } else {
            document.getElementById("to-top").style.display = "none";
        }
    };

    function toTop(){
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

</script>