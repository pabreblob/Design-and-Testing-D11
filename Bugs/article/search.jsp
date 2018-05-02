<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<form:form action="article/list-search.do" method="GET">

	<label for="keyword">
		<spring:message code="newspaper.keyword" />
	</label>
	<input type="text" name="keyword" id="keyword"/><br />
	
	<input type="submit" name="search" value="<spring:message code="newspaper.search" /> "/>
	<input type="button" name="cancel" value="<spring:message code="newspaper.cancel" /> "
	onclick="javascript: relativeRedir('/index.do');"/>
	
	
</form:form>