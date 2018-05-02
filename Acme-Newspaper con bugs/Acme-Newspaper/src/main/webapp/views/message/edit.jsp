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

<form:form action="message/actor/edit.do" modelAttribute="mess">
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:label path="recipients"><spring:message code="message.recipient"/></form:label>
<form:select path="recipients" multiple="true">
<form:options items="${actors}" itemValue="id" itemLabel="name" />
</form:select>
<form:errors cssClass="error" path="recipients"/><br/>
<acme:textbox code="message.subject" path="subject"/>
<form:label path="priority"><spring:message code="message.priority"/></form:label>
<form:select path="priority">
<form:option label="LOW" value="LOW"/>
<form:option label="NEUTRAL" value="NEUTRAL"/>
<form:option label="HIGH" value="HIGH"/>
</form:select>
<form:errors cssClass="error" path="priority"/><br/>
<acme:textarea code="message.body" path="body"/>
<acme:submit name="save" code="message.save"/>
<acme:cancel code="message.cancel" url="/folder/actor/list.do"/>
</form:form>
