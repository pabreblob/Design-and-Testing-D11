<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<a href="tabooword/admin/create.do"><spring:message code="tabooword.create"/></a>

<display:table class="displaytag" name="words" id="row" requestURI="tabooword/admin/list.do" pagesize="20">
	
	<spring:message code="tabooword.word" var="wordHeader" />
	<display:column property="word" title="${wordHeader}" />
	
	<display:column><a href="tabooword/admin/delete.do?taboowordId=${row.id}"><spring:message code="tabooword.delete"/></a></display:column>
	
</display:table>