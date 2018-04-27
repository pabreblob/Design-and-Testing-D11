<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${folder.parent != null}">
	<a href="folder/actor/list.do?parentId=${back}"><spring:message code="folder.goBack"/></a><br>
</jstl:if>
<jstl:if test="${folder.parent == null}">
	<a href="folder/actor/list.do"><spring:message code="folder.goBack"/></a><br>
</jstl:if>

<form:form action="folder/actor/saveEdit.do" modelAttribute="folder">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="parent.id"/>
	<acme:textbox code="folder.name" path="name"/>
	<acme:submit name="submit" code="folder.submit"/>
	<jstl:if test="${folder.children.size() == 0}">
		<acme:cancel url="folder/actor/delete.do?folderId=${folder.id}" code="folder.delete"/>
	</jstl:if>
	<jstl:if test="${folder.parent.id == 0}">
		<acme:cancel url="folder/actor/list.do" code="folder.cancel"/>
	</jstl:if>
	<jstl:if test="${folder.parent.id != 0}">
		<acme:cancel url="folder/actor/list.do?folderId=${folder.parent.id}" code="folder.cancel"/>
	</jstl:if>
</form:form>