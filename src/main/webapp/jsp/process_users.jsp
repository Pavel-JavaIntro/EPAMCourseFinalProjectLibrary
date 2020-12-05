<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="plib" uri="http://library.pavka.by" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="message.title" /></title>
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
                        <table>
                            <h3><fmt:message key="message.administer_user"/></h3>
                            <tr>
                                <form name="adminUserForm" method="POST" action="library">
                                    <input type="hidden" name="command" value="find_user"/>
                                    <fmt:message key="message.usersurname"/>:<br/>
                                    <input type="text" name="surname" id="surname" value="" required/>
                                    <br/><fmt:message key="message.username"/>:<br/>
                                    <input type="text" name="name" id="name" value="" required/>
                                    <br/><input type="submit" value="<fmt:message key="message.select" />"/>
                                </form>
                            </tr>
                            <tr>
                                <jsp:include page="${sessionScope.user_addition}"/>
                                <br/><jsp:include page="${sessionScope.result}"/>
                            </tr>
                        </table>
                    </td>
                    <td
                            rowspan="2"
                            style="width:80%">

                        <form name="changeStatusForm" method="POST" action="library">
                            <fmt:message key="message.status"/><br/>
                            <select size="4" name="user" required>
                                <c:forEach var="item" items="${sessionScope.users}">
                                    <option value="${item.id}">
<%--                                            ${item}--%>
                                        <plib:user-info user="${item}"/>
                                    </option>
                                </c:forEach>
                            </select>

                            <br/>
                            <input type="radio" name="status" value="subscriber"><fmt:message key="message.subsc"/> <br/>
                            <input type="radio" name="status" value="reader"><fmt:message key="message.reader"/><br/>
                            <input type="radio" name="status" value="guest"><fmt:message key="message.excluded"/><br/>
                            <input type="submit" value="<fmt:message key="message.status" />"/>
                            <input type="hidden" name="command" value="change_status"/>
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
<script src="${pageContext.request.contextPath}/js/poster.js" type="text/javascript"></script>
</body>
</html>
