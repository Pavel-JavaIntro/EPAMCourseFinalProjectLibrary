<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<h3><fmt:message key="message.selected"/><a href="#" onclick="postTo('basket');">${sessionScope.client.basketSize}</a><fmt:message
        key="message.books"/></h3>
<script src="${pageContext.request.contextPath}/js/poster.js" type="text/javascript"></script>
