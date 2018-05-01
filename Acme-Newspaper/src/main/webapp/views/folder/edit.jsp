<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="folder/actor/saveEdit.do" modelAttribute="folder">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<acme:textbox code="folder.name" path="name"/>
	<acme:submit name="submit" code="folder.submit"/>
	<jstl:if test="${folder.children.size() == 0}">
		<acme:cancel url="folder/actor/delete.do?folderId=${folder.id}" code="folder.delete"/>
	</jstl:if>
	<jstl:if test="${folder.parent.id == 0}">
		<acme:cancel url="folder/actor/list.do" code="folder.cancel"/>
	</jstl:if>
	<jstl:if test="${folder.parent.id != 0}">
		<acme:cancel url="folder/actor/list.do?parentId=${folder.parent.id}" code="folder.cancel"/>
	</jstl:if>
</form:form>
<jstl:if test="${folder.children.size() != 0}">
	<spring:message code="folder.deleteNot"/>
</jstl:if>
