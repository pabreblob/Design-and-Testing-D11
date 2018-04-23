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
<jstl:if test="${requestURI == 'article/user/list-published.do' }">
<h1><spring:message code="article.followUp.list"/></h1>
</jstl:if>
<display:table name="articles" id="a" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<jstl:if test="${requestURI == 'article/user/list-editable.do' }">
		<display:column>

			<a href="article/user/edit.do?articleId=${a.id}"> <spring:message
					code="article.edit" />
			</a>

		</display:column>
	</jstl:if>

	<spring:message code="article.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	<spring:message code="article.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />
	<spring:message code="article.newspaper" var="newspaperHeader" />
	<display:column property="newspaper.title" title="${newspaperHeader}" />
	<jstl:if test="${requestURI == 'article/user/list-editable.do' }">
		<display:column>

			<a href="article/user/display.do?articleId=${a.id}"> <spring:message
					code="article.display" />
			</a>

		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI == 'article/user/list-published.do' }">
		<display:column>

			<a href="followUp/user/create.do?articleId=${a.id}"> <spring:message
					code="article.followUp.create" />
			</a>

		</display:column>
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
		<display:column>

			<a href="article/admin/display.do?articleId=${a.id}"> <spring:message
					code="article.display" />
			</a>

		</display:column>
	</security:authorize>
	<jstl:if test="${requestURI == 'article/list-search.do' }">
		<display:column>

			<a href="newspaper/display.do?newspaperId=${a.newspaper.id}"> <spring:message
					code="article.newspaper.display" />
			</a>

		</display:column>
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
		<display:column>

			<a href="article/admin/delete.do?articleId=${a.id}"> <spring:message
					code="article.delete" />
			</a>

		</display:column>
	</security:authorize>

</display:table>



