<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="followUp/user/edit.do" modelAttribute="followUp">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="article" />
	<acme:textbox code="followUp.title" path="title" /><br />
	<acme:textarea code="followUp.summary" path="summary" /><br />
	<acme:textarea code="followUp.body" path="body" /><br />
	<spring:message code="followUp.pictures.placeholder" var="pictureplaceholder"/>
	<acme:textbox code="followUp.pictures" path="pictureUrls" placeholder='${pictureplaceholder}' /><br />
	<acme:submit name="save" code="followUp.save"  />
	<acme:cancel code="followUp.cancel" url="article/user/list-published.do" /><br />	
</form:form>



