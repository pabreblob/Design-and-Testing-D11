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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="users" requestURI="${requestURI}" id="row">
	<spring:message code="user.username" var="uNameHeader" />
	<display:column property="userAccount.username" title="${uNameHeader}" />
	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />
	<display:column>
		<a href="user/display.do?userId=${row.id}"> <spring:message
				code="user.display" />
		</a>
	</display:column>
	<jstl:if test="${unfollow}">
		<display:column>
			<a href="user/user/unfollow.do?userId=${row.id}"> <spring:message
					code="user.unfollow" />
			</a>
		</display:column>
	</jstl:if>
</display:table>