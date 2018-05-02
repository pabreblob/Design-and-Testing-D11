<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="message/actor/moveToFolder.do">
<input type="hidden" name="mess" value='<jstl:out value="${mess.id}"/>'/>
<select name="target">
<jstl:forEach items="${folders}" var="folder">
<option value='<jstl:out value="${folder.id}"/>'>
<jstl:out value="${folder.name}"/>
</option>
</jstl:forEach>
</select>
<acme:submit name="save" code="message.save"/>
<acme:cancel code="message.cancel" url="/message/actor/list.do?folderId=${origin.id}"/>
</form:form>
