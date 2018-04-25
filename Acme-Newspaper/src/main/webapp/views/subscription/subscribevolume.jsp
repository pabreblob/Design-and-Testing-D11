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



	<form:form action="${requestURI}" modelAttribute="subscriptionForm">

		<form:hidden path="volume" />
		
		<acme:textbox code="subscription.holderName" path="creditCard.holderName" />
		<acme:textbox code="subscription.brandName" path="creditCard.brandName" />
		<acme:textbox code="subscription.number" path="creditCard.number" />
		<acme:textbox code="subscription.expMonth" path="creditCard.expMonth" />
		<acme:textbox code="subscription.expYear" path="creditCard.expYear" />
		<acme:textbox code="subscription.cvv" path="creditCard.cvv" />


		<acme:submit name="save" code="subscription.save" />
		<acme:cancel code="subscription.cancel" url="${volumeUrl}" />

	</form:form>

