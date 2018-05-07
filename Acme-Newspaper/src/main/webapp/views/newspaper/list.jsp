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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<display:table name="newspapers" id="n" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<jstl:if test="${requestURI == 'newspaper/user/list-available.do'}">
	<display:column>
	<a href="volume/user/add-newspaper.do?volumeId=${volumeId}&newspaperId=${n.id}"> <spring:message
					code="newspaper.volume" />
	</a>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${requestURI == 'newspaper/agent/list-without.do'}">
	<display:column>
	<a href="advertisement/agent/create.do?newspaperId=${n.id}"> <spring:message
					code="newspaper.advert" />
	</a>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${requestURI == 'newspaper/user/list.do'}">
	<display:column>
	<jstl:if test="${n.publicationDate == null }">
	<a href="newspaper/user/edit.do?newspaperId=${n.id}"> <spring:message
					code="newspaper.edit" />
	</a>
	</jstl:if>
	</display:column>
	
	<display:column>
	<jstl:if test="${n.publicationDate == null }">
	<a href="newspaper/user/publish.do?newspaperId=${n.id}"> <spring:message
					code="newspaper.publish" />
	</a>
	</jstl:if>
	</display:column>
	</jstl:if>
	<jstl:if test="${requestURI != 'newspaper/user/list.do' }">
	<spring:message code="newspaper.date" var="dateHeader" />
	<spring:message code="newspaper.dateFormat2" var="dateFormatHeader" />
	<display:column title="${dateHeader}">
		<fmt:formatDate value="${n.publicationDate}" pattern="${dateFormatHeader}" />
	</display:column>
	</jstl:if>
	<spring:message code="newspaper.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"/>
	<spring:message code="newspaper.private" var="privateHeader" />
	<display:column title="${privateHeader}" >
	<jstl:if test="${n.free == false}">
	<spring:message code ="newspaper.true"/>
	</jstl:if>
	<jstl:if test="${n.free == true}">
	<spring:message code ="newspaper.false"/>
	</jstl:if>
	</display:column>
	<spring:message code="newspaper.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}"/>
	
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="newspaper.marked" var="markedHeader" />
	<display:column  title="${markedHeader}" >
		<jstl:if test ="${n.marked == false }">
			<spring:message code ="newspaper.false"/>
		</jstl:if>
		<jstl:if test ="${n.marked == true }">
			<spring:message code ="newspaper.true"/>
		</jstl:if>
	</display:column>
	<display:column>
	<a href="newspaper/admin/delete.do?newspaperId=${n.id}&requestURI=${requestURI}"> <spring:message
					code="newspaper.delete" />
			</a>

	</display:column>
	</security:authorize>
	
	<display:column>
	<a href="newspaper/display.do?newspaperId=${n.id}"> <spring:message
					code="newspaper.display" />
	</a>
	</display:column>
	

		
</display:table>

<jstl:if test="${requestURI == 'newspaper/user/list.do' }">
<div><a href="newspaper/user/create.do"> <spring:message
					code="newspaper.create" />
			</a></div>
			</jstl:if>