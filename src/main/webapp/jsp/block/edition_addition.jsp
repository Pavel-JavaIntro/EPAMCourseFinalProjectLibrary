<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8"/>
<fmt:setLocale value="${sessionScope.lan}"/>
<fmt:setBundle basename="messages"/>

<br/><fmt:message key="message.new"/>${sessionScope.code}
<form name="bookAddingForm" method="POST" action="library" onsubmit="return validateSurname();">
    <br/>
    <input type="hidden" name="command" value="add_edition"/>
    <fmt:message key="message.genre"/>:<br/>
    <input type="number" name="genre" id="genre" value="" required/>
    <br/><fmt:message key="message.authors"/>:<br/>
    <label for="surname0"><fmt:message key="message.usersurname"/></label><input type="text" name="surname0"
                                                                                 id="surname0" value=""/>
    <br/><label for="name0"><fmt:message key="message.username"/></label><input type="text" name="name0"
                                                                           id="name0" value=""/>
    <br/><label for="secondname0"><fmt:message key="message.secondname"/></label><input type="text" name="secondname0"
                                                                                   id="secondname0" value=""/>
    <br/><label for="surname1"><fmt:message key="message.usersurname"/></label><input type="text" name="surname1"
                                                                                 id="surname1" value=""/>
    <br/><label for="name1"><fmt:message key="message.username"/></label><input type="text" name="name1"
                                                                           id="name1" value=""/>
    <br/><label for="secondname1"><fmt:message key="message.secondname"/></label><input type="text" name="secondname1"
                                                                                   id="secondname1" value=""/>
    <br/><label for="surname2"><fmt:message key="message.usersurname"/></label><input type="text" name="surname2"
                                                                                 id="surname2" value=""/>
    <br/><label for="name2"><fmt:message key="message.username"/></label><input type="text" name="name2"
                                                                           id="name2" value=""/>
    <br/><label for="secondname2"><fmt:message key="message.secondname"/></label><input type="text" name="secondname2"
                                                                                   id="secondname2" value=""/>
    <br/><fmt:message key="message.booktitle"/><br/>
    <input type="text" name="booktitle" id="booktitle" value="" required/>
    <br/><fmt:message key="message.year"/>:<br/>
    <input type="number" name="bookyear" id="bookyear" value=""/>
    <br/><fmt:message key="message.booklocation"/>:<br/>
    <input type="number" name="booklocation" id="booklocation" value=""/>
    <br/><input type="submit" value="<fmt:message key="message.addbook"/>"/>
</form>
<script src="${pageContext.request.contextPath}/js/validator.js" type="text/javascript"></script>
