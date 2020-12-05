<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h2><fmt:message key="message.search"/></h2>
                        <br/>
                        <h3><fmt:message key="message.searcher"/><br/></h3>
                        <form name="searchForm" method="POST" action="library" onsubmit="return validateAuthor();">
                            <input type="hidden" name="command" value="find_edition"/>
                            <br/><fmt:message key="message.booktitle"/><br/>
                            <input type="text" name="title" id="title" value=""/>
                            <br/><fmt:message key="message.bookauthor"/><br/>
                            <input type="text" name="author" id="author" value=""/>
                            <br/><br/>
                            <input type="submit" value="<fmt:message key="message.search" />"/>
                        </form>
                    </td>
                    <td
                            rowspan="2"
                            style="width:80%">
                        <jsp:include page="${sessionScope.client.basket}"/>

                        <form name="selectForm" method="POST" action="library">
                            <fmt:message key="message.result"/><br/>

                            <select size="10" name="edition" required>
                                <c:forEach var="item" items="${sessionScope.editions}">
                                    <option value="${item.edition.id}">
<%--                                            ${item}--%>
                                        <plib:edition-info edition="${item}" availability="true"/>
                                    </option>
                                </c:forEach>
                            </select>

                            <br/>
                            <jsp:include page="${sessionScope.client.bookSelection}"/>
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
<script src="${pageContext.request.contextPath}/js/poster.js" type="text/javascript"></script>
</body>
</html>
