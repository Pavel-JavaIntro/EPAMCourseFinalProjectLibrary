<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8"/>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>

<br/><fmt:message key="message.old"/>${sessionScope.code}
<form name="bookAddingForm" method="POST" action="library">
    <br/>
    <input type="hidden" name="command" value="add_book"/>
    <br/><fmt:message key="message.booklocation"/>:<br/>
    <input type="number" name="booklocation" id="booklocation" value=""/>
    <br/><input type="submit" value="<fmt:message key="message.addbook"/>"/>
</form>
