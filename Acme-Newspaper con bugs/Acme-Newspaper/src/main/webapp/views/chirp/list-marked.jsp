<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table class="displaytag" name="marked" id="row" requestURI="chirp/admin/list-marked.do" pagesize="20" defaultsort="4" defaultorder="descending">
	
	<spring:message code="chirp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="chirp.description" var="descHeader" />
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
	
	<display:column><a href="chirp/admin/delete-marked.do?chirpId=${row.id}"><spring:message code="chirp.delete"/></a></display:column>
	
</display:table>