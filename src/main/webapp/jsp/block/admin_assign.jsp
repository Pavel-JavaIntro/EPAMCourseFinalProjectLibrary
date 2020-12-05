<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8"/>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>

<br/><input type="radio" name="role" value="reader" checked><fmt:message key="message.reader"/>
<br/><input type="radio" name="role" value="subscriber"><fmt:message key="message.subsc"/>
<br/><input type="radio" name="role" value="librarian"><fmt:message key="message.librarian"/>
