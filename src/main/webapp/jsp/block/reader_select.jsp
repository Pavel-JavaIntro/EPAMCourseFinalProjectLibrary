<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<input type="submit" value="<fmt:message key="message.select" />"/>
<input type="hidden" name="command" value="select_book"/>
