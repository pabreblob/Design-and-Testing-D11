<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="agent/save.do" modelAttribute="userForm">
	
	<form:hidden path="userAccount.authorities"/>
	<form:hidden path="userAccount.id"/>
	<form:hidden path="userAccount.version"/>
	
	<acme:textbox code="user.username" path="userAccount.username" />
	<acme:password code="user.password" path="userAccount.password" />
	<acme:password code="user.confirmPassword" path="confirmPass" />
	<acme:textbox code="user.name" path="name" />
	<acme:textbox code="user.surname" path="surname" />
	<spring:message code="user.placeholderEmail" var="emailplaceholder"/>
	<acme:textbox code="user.email" path="email" placeholder='${emailplaceholder}' />
	<acme:textbox code="user.phone" path="phone" />
	<acme:textbox code="user.address" path="address" />
	<a href="misc/terms.do" target="_blank"><spring:message code="user.acceptTerms"/></a><acme:checkbox code="user.blank" path="acceptTerms"/>
	<acme:submit name="save" code="user.save"  />
	<acme:cancel code="user.cancel" url="welcome/index.do" />
</form:form>