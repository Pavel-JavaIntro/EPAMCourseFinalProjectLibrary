<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<table
        border="1"
        bgcolor="#7FFFD4"
        height="100"
        cellpadding="10"
        style="width:100%; border-radius:5px;">
    <tr>
        <th>
            <h3><fmt:message key="message.waddress"/></h3>
            <p><fmt:message key="message.libaddress"/></p>
            <h3><fmt:message key="message.wtime"/></h3>
            <p><fmt:message key="message.libtime"/></p>
            <h3><fmt:message key="message.developer"/></h3>
            <p> email: ${applicationScope.email}</p>
        </th>
    </tr>
</table>

