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

<display:table class="displaytag" name="marked" id="row" requestURI="advertisement/admin/list-marked.do" pagesize="5">
	
	<spring:message code="advertisement.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="advertisement.bannerUrl" var="bannerHeader" />
	<display:column title="${bannerHeader}" ><a href="${row.bannerUrl}"><spring:message code="advertisement.bannerUrl.link"/></a></display:column>
	
	<spring:message code="advertisement.pageUrl" var="pageHeader" />
	<display:column title="${pageHeader}" ><a href="${row.pageUrl}"><spring:message code="advertisement.pageUrl.link"/></a></display:column>
	
	<spring:message code="advertisement.owner" var="ownerHeader" />
	<display:column title="${ownerHeader}">
		<a href="user/display.do?userId=${row.owner.id}"><jstl:out value="${row.owner.userAccount.username}" /></a>
	</display:column>
	
	<display:column><a href="advertisement/admin/delete-marked.do?advertisementId=${row.id}"><spring:message code="advertisement.delete"/></a></display:column>
	
</display:table>