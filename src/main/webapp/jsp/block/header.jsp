<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>
<table
        border="1"
        background="./images/book.png"
        bgcolor="#7FFFD4"
        cellpadding="10"
        style="width:100%; border-radius:5px;">
    <tr>
        <th>
            <h1><fmt:message key="message.libheader"/></h1>
            <h3><fmt:message key="message.libad"/></h3>
        </th>
        <th>
            <form name="selectLang" method="POST" action="library">
                <input type="hidden" name="command" value="set_lang"/>
                <img src="./images/rus.png" width="30" height="30" alt="">
                <br/>
                <button name="lan" value="ru">RU</button>
            </form>
        </th>
        <th>
            <form name="selectLang" method="POST" action="library">
                <input type="hidden" name="command" value="set_lang"/>
                <img src="./images/eng.png" width="30" height="30" alt="">
                <br/>
                <button name="lan" value="en">EN</button>
            </form>
        </th>
    </tr>
</table>