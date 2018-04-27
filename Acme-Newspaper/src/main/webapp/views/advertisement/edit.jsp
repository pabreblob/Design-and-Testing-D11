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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="advertisement/agent/edit.do" modelAttribute="advertisement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="newspaper" />
	
	<acme:textbox code="advertisement.title" path="title" />
	<acme:textbox code="advertisement.bannerUrl" path="bannerUrl" />
	<acme:textbox code="advertisement.pageUrl" path="pageUrl" />
	
	<acme:textbox code="advertisement.holderName" path="creditCard.holderName" />
	<acme:textbox code="advertisement.brandName" path="creditCard.brandName" />
	<acme:textbox code="advertisement.number" path="creditCard.number" />
	<acme:textbox code="advertisement.expMonth" path="creditCard.expMonth" />
	<acme:textbox code="advertisement.expYear" path="creditCard.expYear" />
	<acme:textbox code="advertisement.cvv" path="creditCard.cvv" />
	
	<acme:submit name="submit" code="advertisement.submit" />
	<acme:cancel url="/newspaper/agent/list-without.do" code="advertisement.cancel"/>

</form:form>