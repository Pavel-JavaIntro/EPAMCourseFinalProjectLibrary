<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8"/>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>

<br/><fmt:message key="message.user"/>${sessionScope.surname} ${sessionScope.name}
<form name="userAddingForm" method="POST" action="library">
    <br/>
    <input type="hidden" name="command" value="add_user"/>
    <fmt:message key="message.waddress"/><br/>
    <input type="text" name="address" id="address" value="" required/>
    <br/>
    <fmt:message key="message.phone"/><br/>
    <input type="text" name="phone" id="phone" value=""/>
    <br/>Email: <br/>
    <input type="text" name="email" id="email" value=""/>
    <jsp:include page="${sessionScope.client.assignment}"/>
    <br/><input type="submit" value="<fmt:message key="message.adduser"/>"/>
</form>

