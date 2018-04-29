<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table>
  <tr>
  	<th></th>
    <th><spring:message code="admin.average"/></th>
    <th><spring:message code="admin.standardDeviation"/></th>
  </tr>
  <tr>
    <td><spring:message code="admin.newspapersCreatedByUser"/></td>
    <td>${averageNewspaperPerUser}</td>
    <td>${standardDeviationNewspaperPerUser}</td>
  </tr>
  <tr>
  	<td><spring:message code="admin.articlesCreatedByWriter"/></td>
  	<td>${averageArticlesPerWriters}</td>
  	<td>${standardDeviationArticlesPerWriters}</td>
  </tr>
  <tr>
  	<td><spring:message code="admin.articlesPerNewspaper"/></td>
  	<td>${averageArticlePerNewspaper}</td>
  	<td>${standardDeviationArticlePerNewspaper}</td>
  </tr>
  <tr>
  	<td><spring:message code="admin.followUpPerArticle"/></td>
  	<td>${averageFollowUpPerArticle}</td>
  	<td></td>
  </tr>
  <tr>
  	<td><spring:message code="admin.followUpPerArticleUpToOneWeek"/></td>
  	<td>${averageFollowUpPerArticleUpToOneWeek}</td>
  	<td></td>
  </tr>
  <tr>
  	<td><spring:message code="admin.followUpPerArticleUpToTwoWeeks"/></td>
  	<td>${averageFollowUpPerArticleUpToTwoWeek}</td>
  	<td></td>
  </tr>
  <tr>
  	<td><spring:message code="admin.numberOfArticlesPerPrivateNewspaper"/></td>
  	<td>${averageNumberOfArticlesPerPrivateNewspaper}</td>
  	<td></td>
  </tr>
  <tr>
  	<td><spring:message code="admin.numberOfArticlesPerPublicNewspaper"/></td>
  	<td>${averageNumberOfArticlesPerPublicNewspaper}</td>
  	<td></td>
  </tr>
</table>

<p><spring:message code="admin.ratioOfUsersWhoHaveCreatedANewspaper"/>: ${ratioUsersWhoHasCreatedANewspaper}</p>
<p><spring:message code="admin.ratioOfUsersWhoHaveWrittenAnArticle"/>: ${ratioUsersWhoHasWrittenAnArticle}</p>
<p><spring:message code="admin.ratioOfUsersWhoHavePostedAbove75OfTheAverage"/>: ${ratioUserWhoPostAbove75OfAverage}</p>
<p><spring:message code="admin.ratioOfPublicNewspaperVersusPrivateNewspaper"/>: ${ratioOfPublicNewspaperVersusPrivateNewspaper}</p>
<p><spring:message code="admin.ratioOfSubscribersPerPrivateNewspaperVersusTheTotalNumberOfCustomers"/>: ${ratioOfSubscribersVersusAllCustomers}</p>
<p><spring:message code="admin.averageRatioOfPrivateVersusPublicNewspapersPerPublisher"/>: ${averageRatioOfPrivateVersusPublicNewspapersPerPublisher}</p>
<p><spring:message code="admin.ratioOfAdvertisedNewspaperVersusNotAdvertised" />: ${ratioOfAdvertisedNewspaperVersusNotAdvertised}</p>
<p><spring:message code="admin.ratioOfMarkedAdvertisments" />: ${ratioOfMarkedAdvertisments}</p>
<p><spring:message code="admin.averageNewspapersPerVolume" />: ${averageNewspapersPerVolume}</p>
<p><spring:message code="admin.ratioOfSubscriptionsToVolumesVersusNewspapers" />: ${ratioOfSubscriptionsToVolumesVersusNewspapers}</p>



<h2><spring:message code="admin.newspaperWithTenMore"/></h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspaperWithArticlesTenPercentMoreThanAverage" requestURI="admin/admin/dashboard.do" id="row">
	<display:column property="title"/>	
</display:table>
<h2><spring:message code="admin.newspaperWithTenFewer"/></h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspaperWithArticlesTenPercentFewerThanAverage" requestURI="admin/admin/dashboard.do" id="row">
	<display:column property="title"/>	
</display:table>
