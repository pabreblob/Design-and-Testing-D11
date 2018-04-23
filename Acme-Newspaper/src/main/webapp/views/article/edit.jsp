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

<form:form action="article/user/edit.do" modelAttribute="article">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:select items="${newspapers}" itemLabel="title" code="article.newspaper" path="newspaper"/>
	<acme:textbox code="article.title" path="title" /><br />
	<acme:textarea code="article.summary" path="summary" /><br />
	<acme:textarea code="article.body" path="body" /><br />
	<spring:message code="article.pictures.placeholder" var="pictureplaceholder"/>
	<acme:textbox code="article.pictures" path="pictureUrls" placeholder='${pictureplaceholder}'/><br />
	<acme:checkbox code="article.final" path="finalMode"/><br />
	<acme:submit name="save" code="article.save"  />
	<acme:cancel code="article.cancel" url="/welcome/index.do" /><br />	
</form:form>



