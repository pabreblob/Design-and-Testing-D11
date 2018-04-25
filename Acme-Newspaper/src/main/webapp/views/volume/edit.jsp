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

<form:form action="volume/user/edit.do" modelAttribute="volume">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="volume.title" path="title" /><br />
	<acme:textarea code="volume.description" path="description" /><br />
	<acme:textbox code="volume.year" path="year" /><br />
	<acme:textbox code="volume.price" path="price" /><br />
	<acme:submit name="save" code="volume.save"  />
	<acme:cancel code="volume.cancel" url="/volume/user/list-created.do" /><br />	
</form:form>



