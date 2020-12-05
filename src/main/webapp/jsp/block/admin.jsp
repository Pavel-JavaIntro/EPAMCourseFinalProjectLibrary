<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<td
        bgcolor="#e6e6fa"
        align="center">
    <h3><fmt:message key="message.admin"/></h3>
    <form action="library" method="post">
        <a href="#" onclick="postTo('welcome');"><fmt:message key="message.main" /></a><br/>
        <a href="#" onclick="postTo('show_orders');"><fmt:message key="message.users_orders" /></a><br/>
        <a href="#" onclick="postTo('show_books');"><fmt:message key="message.dispatch" /></a><br/>
        <a href="#" onclick="postTo('return_books');"><fmt:message key="message.return" /></a><br/>
        <a href="#" onclick="postTo('overdue_books');"><fmt:message key="message.overdue" /></a><br/>
        <a href="#" onclick="postTo('book_options');"><fmt:message key="message.book_manage" /></a><br/>
        <a href="#" onclick="postTo('user_options');"><fmt:message key="message.user_manage" /></a>
    </form>

</td>
<script src="${pageContext.request.contextPath}/js/poster.js" type="text/javascript"></script>