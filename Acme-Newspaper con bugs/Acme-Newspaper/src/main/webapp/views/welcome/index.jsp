<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('USER')">

	<h1><spring:message code="welcome.chirps" /></h1>
	
	<display:table class="displaytag" name="chirps" id="row" requestURI="welcome/index.do" pagesize="20" defaultsort="4" defaultorder="descending">
	
		<spring:message code="welcome.chirp.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" />
	
		<spring:message code="welcome.chirp.description" var="descHeader" />
		<display:column property="description" title="${descHeader}" />
		
		<spring:message code="welcome.chirp.creator" var="creatorHeader" />
		<display:column title="${creatorHeader}">
			<a href="user/display.do?userId=${row.creator.id}"><jstl:out value="${row.creator.userAccount.username}" /></a>
		</display:column>
		
		<spring:message code="welcome.chirp.moment" var="momentHeader" />
			<spring:message code="welcome.chirp.format" var="dateFormat" />
			<display:column title="${momentHeader}">
				<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
			</display:column>
	
	</display:table>
	
	<a href="chirp/user/create.do"><spring:message code="welcome.chirp.create"/></a>

</security:authorize>
