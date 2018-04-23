<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="tabooword/admin/create.do" modelAttribute="word">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="tabooword.word" path="word" /><br />
	<acme:submit name="submit" code="tabooword.submit"  />
	<acme:cancel code="tabooword.cancel" url="/tabooword/admin/list.do" /><br />	
</form:form>