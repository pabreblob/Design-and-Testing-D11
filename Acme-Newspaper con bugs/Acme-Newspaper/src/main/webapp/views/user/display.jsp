<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="user.username" />
	:
	<jstl:out value="${user.userAccount.username}" />
</p>
<p>
	<spring:message code="user.name" />
	:
	<jstl:out value="${user.name}" />
</p>
<p>
	<spring:message code="user.surname" />
	:
	<jstl:out value="${user.surname}" />
</p>
<jstl:if test='${not empty user.address}'>
<p>
	<spring:message code="user.address" />
	:
	<jstl:out value="${user.address}" />
</p>
</jstl:if>
<jstl:if test='${not empty user.phone}'>
<p>
	<spring:message code="user.phone" />
	:
	<jstl:out value="${user.phone}" />
</p>
</jstl:if>
<p>
	<spring:message code="user.email" />
	:
	<jstl:out value="${user.email}" />
</p>
<security:authorize access="hasRole('USER')">
	<jstl:if test="${following != null}">
	<jstl:if test="${following}">
		<a href="user/user/unfollow.do?userId=${user.id}"><spring:message code="user.unfollow"/></a>
	</jstl:if>
	<jstl:if test="${not following}">
		<a href="user/user/follow.do?userId=${user.id}"><spring:message code="user.follow"/></a>
	</jstl:if>
	</jstl:if>
</security:authorize>
<br>
<h1><spring:message code="user.articles"/></h1>

<display:table class="displaytag" name="articles" requestURI="user/display.do" pagesize="5" uid="ar" defaultsort="2" defaultorder="descending">
	
	<spring:message code="user.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="welcome.chirp.format" var="dateFormat" />
	<spring:message code="user.moment" var="momentHeader"/>
	<display:column title="${momentHeader}">
		<fmt:formatDate value="${ar.moment}" pattern="${dateFormat}"/>
	</display:column>
	
	<spring:message code="user.newspaper" var="newspaperHeader" />
	<display:column property="newspaper.title" title="${newspaperHeader}" />
	<display:column>
		<a href="newspaper/display.do?newspaperId=${ar.newspaper.id}"><spring:message code="user.newspaperURL"/></a>
	</display:column>
	
</display:table>

<h1>Chirps</h1>

<display:table class="displaytag" name="chirps" requestURI="user/display.do" pagesize="20" defaultsort="3" uid="ch" defaultorder="descending">
	
	<spring:message code="welcome.chirp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="welcome.chirp.description" var="descHeader" />
	<display:column property="description" title="${descHeader}" />
	
	<spring:message code="welcome.chirp.moment" var="momentHeader" />
	<spring:message code="welcome.chirp.format" var="dateFormat" />
	<display:column title="${momentHeader}">
		<fmt:formatDate value="${ch.moment}" pattern="${dateFormat}"/>
	</display:column>

</display:table>
