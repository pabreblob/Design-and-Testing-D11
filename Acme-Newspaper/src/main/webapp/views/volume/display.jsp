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
<spring:message code="volume.title"/>:<jstl:out value="${volume.title}"/>
</p>
</div>
<div>
<p>
<spring:message code="volume.description"/>:<jstl:out value="${volume.description}"/>
</p>
</div>
<div>
<p>
<spring:message code="volume.year"/>:<jstl:out value="${volume.year}"/>
</p>
</div>
<div>
<p>
<spring:message code="volume.price"/>:<jstl:out value="${volume.price}"/>
</p>
</div>
<display:table class="displaytag" name="newspapers" requestURI="user/display.do" pagesize="5" id="n">
	<jstl:if test="${volumeCreated == true}">
	<display:column>
	<a href="volume/user/remove-newspaper.do?volumeId=${volume.id}&newspaperId=${n.id}"> <spring:message
					code="volume.remove" />
	</a>
	</display:column>
	</jstl:if>
	<spring:message code="volume.newspaper.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	<spring:message code="volume.newspaper.date" var="dateHeader" />
	<spring:message code="volume.newspaper.dateFormat2" var="dateFormatHeader" />
	<display:column title="${dateHeader}">
		<fmt:formatDate value="${n.publicationDate}" pattern="${dateFormatHeader}" />
	</display:column>
	<spring:message code="volume.newspaper.private" var="privateHeader" />
	<display:column title="${privateHeader}" >
	<jstl:if test="${n.free == false}">
	<spring:message code ="volume.newspaper.true"/>
	</jstl:if>
	<jstl:if test="${n.free == true}">
	<spring:message code ="volume.newspaper.false"/>
	</jstl:if>
	</display:column>
	<spring:message code="volume.newspaper.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}"/>
		<display:column>
	<a href="newspaper/display.do?newspaperId=${n.id}"> <spring:message
					code="volume.display" />
	</a>
	</display:column>
	
</display:table>

	<p><a href="subscription/customer/subscribeVolume.do?volumeId=${volume.id}"> <spring:message
					code="volume.subscribe" />
			</a> </p>

