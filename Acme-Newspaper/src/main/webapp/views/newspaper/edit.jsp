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

<form:form action="newspaper/user/edit.do" modelAttribute="newspaper">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="creator" />
	<form:hidden path="publicationDate" />
	<form:hidden path="marked" />
	

	<acme:textbox code="newspaper.title" path="title" /><br />
	<acme:textarea code="newspaper.description" path="description" /><br />
	<acme:textbox code="newspaper.picture" path="pictureUrl" placeholder ='http://www.image.com/imageId=25'/><br />
	<acme:textbox code="newspaper.price" path="price" /><br />
	
	<acme:submit name="save" code="newspaper.save"  />
	<acme:cancel code="newspaper.cancel" url="newspaper/user/list.do" /><br />	
</form:form>



