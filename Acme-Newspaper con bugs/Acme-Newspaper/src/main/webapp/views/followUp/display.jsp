<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<div>
<p>
<spring:message code="followUp.title"/>:<jstl:out value="${followUp.title}"/>
</p>
</div>


<div>
<spring:message code="followUp.dateFormat2" var="dateFormat2" />
<p>
<spring:message code="followUp.moment"/>:<fmt:formatDate value="${followUp.moment}" pattern="${dateFormat2}" />
</p>
</div>

<div>
<p>
<spring:message code="followUp.summary"/>:<jstl:out value="${followUp.summary}"/>
</p>
</div>
<div>
<p>
<spring:message code="followUp.body"/>:<jstl:out value="${followUp.body}"/>
</p>
</div>
<jstl:if test="${hasPictures== true}">
	<jstl:forEach var="picture" items="${pictures}">
	<img src="<jstl:out value="${picture}"/>">
	</jstl:forEach>
	</jstl:if>
<div>
<p>
<spring:message code="followUp.article"/>:<jstl:out value="${followUp.article.title}"/>
</p>
</div>
