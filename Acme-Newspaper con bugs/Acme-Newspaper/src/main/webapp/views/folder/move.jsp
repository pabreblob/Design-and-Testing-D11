<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h1><spring:message code="folder.moving"/> <jstl:out value="${toMove.name}"/></h1>

<spring:message code="folder.selectNone"/>
<form:form action="folder/actor/moveSave.do" modelAttribute="moveForm">
	<form:hidden path="folderToMove"/>
	<acme:select items="${show}" itemLabel="name" code="folder.newFolder" path="idNewParent"/>
	<acme:submit name="submit" code="folder.submit"/>
	<jstl:if test="${toMove.parent.id == 0}">
		<acme:cancel url="folder/actor/list.do" code="folder.cancel"/>
	</jstl:if>
	<jstl:if test="${toMove.parent.id != 0}">
		<acme:cancel url="folder/actor/list.do?parentId=${toMove.parent.id}" code="folder.cancel"/>
	</jstl:if>
</form:form>
