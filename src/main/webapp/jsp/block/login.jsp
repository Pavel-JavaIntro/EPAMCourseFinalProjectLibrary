<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8"/>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<td bgcolor="#e6e6fa">
    <p>
    <form name="loginForm" method="POST" action="library" onsubmit="return validateLogin();">
        <h3><fmt:message key="message.login"/><br/></h3>
        <br/>
        <input type="hidden" name="command" value="login"/>
        <fmt:message key="message.usersurname"/>:<br/>
        <input type="text" name="surname" id="surname" value=""/>
        <br/><fmt:message key="message.username"/>:<br/>
        <input type="text" name="name" id="name" value=""/>
        <br/><fmt:message key="message.userpassword"/>:<br/>
        <input type="password" name="password" id="password" value=""/>
        <br/> <font color="red">${errorLoginPassMessage} </font> <br/>
        ${wrongAction} <br/>
        <input type="submit" value="<fmt:message key="message.entrance"/>"/>
    </form>
    <p> <fmt:message key="message.unregistered"/> <br/>
    <form name="searchForm" method="POST" action="library">
        <input type="hidden" name="command" value="search"/>
        <img src="./images/book.png" width="30" height="30" alt="">
        <input type="submit" value="<fmt:message key="message.search"/>">
    </form>
</td>
<script src="${pageContext.request.contextPath}/js/validator.js" type="text/javascript"></script>

