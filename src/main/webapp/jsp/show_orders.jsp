<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="plib" uri="http://library.pavka.by" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8"/>
    <title><fmt:message key="message.title"/></title>
</head>
<body>
<table
        border="1"
        align="center"
        rules="rows"
        style="width:70%;">
    <tr>
        <td>
            <jsp:include page="block/header.jsp"/>
            <table
                    border="1"
                    bgcolor="#e6e6fa"
                    cellpadding="10"
                    style="width:100%; border-radius:5px;">
                <tr>
                    <td
                            rowspan="2"
                            style="width:80%">
                        <h2><fmt:message key="message.users_orders"/></h2>
                        <form name="orderForm" id="orderForm" method="POST" action="library">
                            <select size="10" name="book" required>
                                <c:forEach var="item" items="${sessionScope.orders}">
                                    <c:forEach var="edition" items="${item.editionInfoSet}">
                                        <option value="${edition.book.id}">
                                            <plib:order-info order="${item}" edition="${edition}" standard="true"/>
                                        </option>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                            <br/>
                            <input type="hidden" name="command" value="prepare_book"/>
                            <button name="preparation" value="deny"><fmt:message
                                    key="message.deny"/></button>
                            <br/>
                            <button name="preparation" value="prepare"><fmt:message key="message.prepare"/></button>
                        </form>
                    </td>
                    <jsp:include page="${sessionScope.client.entrance}"/>
                </tr>
                <tr>
                    <jsp:include page="${sessionScope.client.adminSection}"/>
                </tr>
            </table>
            <jsp:include page="block/footer.jsp"/>
        </td>
    </tr>
</table>
<script src="${pageContext.request.contextPath}/js/validator.js" type="text/javascript"></script>
</body>
</html>


