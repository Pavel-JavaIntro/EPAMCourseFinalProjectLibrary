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
                        <h2><fmt:message key="message.welcome" /></h2>
                        <p style="text-indent:20px">
                            <fmt:message key="message.maintext1" /></p>

                        <p style="text-indent:20px">
                            <fmt:message key="message.maintext2" /> ${applicationScope.books}
                            <fmt:message key="message.maintext3" /> ${applicationScope.users}
                            <fmt:message key="message.maintext4" /></p>

                        <p style="text-indent:20px"><fmt:message key="message.maintext5" />
                        <form action="library" method="post">
                            <a href="#" onclick="postTo('search');"><fmt:message key="message.search" /></a>
                        </form>
                        <p><fmt:message key="message.maintext6" /></p>
                        <p style="text-indent:20px">
                            <fmt:message key="message.maintext7" /></p>
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
