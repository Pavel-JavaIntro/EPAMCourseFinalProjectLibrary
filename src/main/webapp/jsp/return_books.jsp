<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="plib" uri="http://library.pavka.by" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8"/>
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
                        <h2><fmt:message key="message.return" /></h2>
                        <p>
                        <form name="returnForm" method="POST" action="library">

                            <input type="hidden" name="command" value="return"/>
                            <fmt:message key="message.bookid"/>:<br/>
                            <input type="number" name="return" id="return" value="" required/>

                            <input type="submit" value="<fmt:message key="message.search"/>"/>
                        </form>
                        </p>
                        <p>
<%--                        ${sessionScope.returning}--%>
                        <plib:book-info book="${sessionScope.returning}" detailed="true"/>
                        <form name="fixReturnForm" method="POST" action="library">
                            <input type="hidden" name="command" value="fix_return"/>
                            <input type="submit" value="<fmt:message key="message.return"/>"/>
                        </form>
                        </p>
                        <jsp:include page="${sessionScope.result}"/>
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

