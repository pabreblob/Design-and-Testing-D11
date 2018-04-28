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
<jstl:if test="${advertisementSize>0}">
<img src="${bannerUrl}" alt=<spring:message code="article.advertisementBanner"/>/>
</jstl:if>
</div>


<div>
<p>
<spring:message code="article.title"/>:<jstl:out value="${article.title}"/>
</p>
</div>
<div>
<p>
<spring:message code="article.creator"/>:<jstl:out value="${article.creator.userAccount.username}"/>
</p>
</div>
<jstl:if test="${article.moment!=null}">
<div>
<spring:message code="article.dateFormat2" var="dateFormat2" />
<p>
<spring:message code="article.moment"/>:<fmt:formatDate value="${article.moment}" pattern="${dateFormat2}" />
</p>
</div>
</jstl:if>
<div>
<p>
<spring:message code="article.summary"/>:<jstl:out value="${article.summary}"/>
</p>
</div>
<div>
<p>
<spring:message code="article.body"/>:<jstl:out value="${article.body}"/>
</p>
</div>
<jstl:if test="${hasPictures== true}">

	<jstl:forEach var="picture" items="${pictures}">
	<img src="<jstl:out value="${picture}"/>">
	</jstl:forEach>
	</jstl:if>
<div>
<p>
<spring:message code="article.newspaper"/>:<jstl:out value="${article.newspaper.title}"/>
</p>
</div>
<jstl:if test="${hasFollowUps}">
	<security:authorize access="hasRole('ADMIN')">
<div>
<a href="followUp/admin/list.do?articleId=${article.id}">
<spring:message	code="article.followUps" />
</a>
</div>
</security:authorize>
<security:authorize access="hasRole('USER')">
<div>
<a href="followUp/user/list.do?articleId=${article.id}">
<spring:message	code="article.followUps" />
</a>
</div>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
<div>
<a href="followUp/customer/list.do?articleId=${article.id}">
<spring:message	code="article.followUps" />
</a>
</div>
</security:authorize>
<security:authorize access="isAnonymous()">
<div>
<a href="followUp/list.do?articleId=${article.id}">
<spring:message	code="article.followUps" />
</a>
</div>
</security:authorize>
	</jstl:if>
