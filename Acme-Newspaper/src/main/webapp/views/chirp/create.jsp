<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="chirp/user/create.do" modelAttribute="chirp">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="chirp.title" path="title" /><br />
	<acme:textarea code="chirp.description" path="description" /><br />
	<acme:submit name="submit" code="chirp.submit"  />
	<acme:cancel code="chirp.cancel" url="/welcome/index.do" /><br />	
</form:form>