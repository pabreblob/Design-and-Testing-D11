<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<display:table name="followUps" id="f" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="followUp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	<spring:message code="followUp.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />
	<spring:message code="followUp.article" var="newspaperHeader" />
	<display:column property="article.title" title="${newspaperHeader}" />
	<security:authorize access="hasRole('USER')">
		<display:column>

			<a href="followUp/user/display.do?followUpId=${f.id}"> <spring:message
					code="followUp.display" />
			</a>

		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<display:column>

			<a href="followUp/admin/display.do?followUpId=${f.id}"> <spring:message
					code="followUp.display" />
			</a>

		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>

			<a href="followUp/customer/display.do?followUpId=${f.id}"> <spring:message
					code="followUp.display" />
			</a>

		</display:column>
	</security:authorize>
	<security:authorize access="isAnonymous()">
		<display:column>

			<a href="followUp/display.do?followUpId=${f.id}"> <spring:message
					code="followUp.display" />
			</a>

		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>

			<a href="followUp/admin/delete.do?followUpId=${f.id}"> <spring:message
					code="followUp.delete" />
			</a>

		</display:column>
	</security:authorize>

</display:table>



