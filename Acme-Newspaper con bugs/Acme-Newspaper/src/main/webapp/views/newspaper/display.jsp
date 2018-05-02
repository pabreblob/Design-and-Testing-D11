<%--
 * action-1.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
	<div>
	<p>
	<spring:message code="newspaper.title"/>: <jstl:out value="${newspaper.title}"/>
	</p>
	</div>
	
	<div>
	<p>
	<spring:message code="newspaper.description"/>: <jstl:out value="${newspaper.description}"/>
	</p>
	</div>
	
	
	<jstl:if test="${pictureSize > 0}">
	<div>
	<img alt="newspaper.picture" src="<jstl:out value="${newspaper.pictureUrl}"/>">
	</div>
	</jstl:if>
	
	
	
	<display:table name="articles" id="a" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<spring:message code="newspaper.title" var="titleHeader" />
	
	<jstl:if test="${link == true }">
	<security:authorize access ="hasRole('CUSTOMER')">
	<display:column  title="${titleHeader}" >
			<a href="article/customer/display.do?articleId=${a.id}"> <jstl:out value="${a.title}"></jstl:out>
			</a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column  title="${titleHeader}" >

			<a href="article/admin/display.do?articleId=${a.id}"> <jstl:out value="${a.title}"></jstl:out>
			</a>
	</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('AGENT')">
	<display:column  title="${titleHeader}" >

			<a href="article/display.do?articleId=${a.id}"> <jstl:out value="${a.title}"></jstl:out>
			</a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('USER')">
	<display:column title="${titleHeader}" >

			<a href="article/user/display.do?articleId=${a.id}"> <jstl:out value="${a.title}"></jstl:out>
			</a>
	</display:column>
	</security:authorize>
	<security:authorize access="!isAuthenticated()">
	<display:column  title="${titleHeader}" >

			<a href="article/display.do?articleId=${a.id}"> <jstl:out value="${a.title}"></jstl:out>
			</a>
	</display:column>
	</security:authorize>
	</jstl:if>
	
	<jstl:if test="${link == false }">
	<display:column property="title" title="${titleHeader}" />
	</jstl:if>
	
	<spring:message code="article.writer" var="writerHeader" />
	<display:column title="${writerHeader}" >

			<a href="user/display.do?userId=${a.creator.id}"> <jstl:out value="${a.creator.name}"></jstl:out>
			</a>
	</display:column>
	
	
	<spring:message code="article.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />
	
	
	
</display:table>

<jstl:if test="${needPay == true}">
	<p><a href="subscription/customer/subscribe.do?newspaperId=${newspaper.id}"> <spring:message
					code="newspaper.subscribe" />
			</a> </p>
	</jstl:if>
		
	

	
	
