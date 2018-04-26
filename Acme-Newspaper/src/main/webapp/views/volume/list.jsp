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
<display:table name="volumes" id="v" requestURI="${requestURI}" keepStatus="true"
	pagesize="5" class="displaytag">
	<jstl:if test="${requestURI == 'volume/user/list-created.do' }">
		<display:column>
			<a href="volume/user/edit.do?volumeId=${v.id}"> <spring:message
					code="volume.edit" />
			</a>

		</display:column>
		<display:column>
			<a href="newspaper/user/list-available.do?volumeId=${v.id}"> <spring:message
					code="volume.newspaper.add" />
			</a>

		</display:column>
	</jstl:if>

	<spring:message code="volume.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	<spring:message code="volume.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	<spring:message code="volume.year" var="yearHeader" />
	<display:column property="year" title="${yearHeader}" />
	<spring:message code="volume.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" />
	<jstl:if test="${requestURI == 'volume/customer/list.do' }">
		<display:column>
		<jstl:set var="contains" value="false"/>
			<jstl:forEach var="customerVolume" items="${customerVolumes}">
				<jstl:if test="${customerVolume.id ==v.id}">
					<jstl:set var="contains" value="true"/>
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${not contains}">

			<a href="subscription/customer/subscribeVolume.do?volumeId=${v.id}"> <spring:message
					code="volume.subscribe" />
			</a>
		</jstl:if>
		</display:column>
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<a href="volume/display.do?volumeId=${v.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
	<display:column>
	<a href="volume/customer/display.do?volumeId=${v.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	</security:authorize>
	<security:authorize access="isAnonymous()">
	<display:column>
	<a href="volume/display.do?volumeId=${v.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('AGENT')">
	<display:column>
	<a href="volume/display.do?volumeId=${v.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('USER')">
	<display:column>
	<a href="volume/user/display.do?volumeId=${v.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	</security:authorize>
	
</display:table>
<jstl:if test="${requestURI == 'volume/user/list-created.do' }">
			<a href="volume/user/create.do"> <spring:message
					code="volume.create" />
			</a>

	</jstl:if>



