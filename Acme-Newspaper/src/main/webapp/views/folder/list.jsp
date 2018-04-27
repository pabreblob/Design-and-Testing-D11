<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${main == false}">
	<jstl:if test="${back != null}">
		<a href="folder/actor/list.do?parentId=${back}"><spring:message code="folder.goBack"/></a><br>
	</jstl:if>
	<jstl:if test="${back == null}">
		<a href="folder/actor/list.do"><spring:message code="folder.goBack"/></a><br>
	</jstl:if>
</jstl:if>

<a><spring:message code="folder.sendMessage"/></a><br>

<display:table name="folders" id="row" requestURI="folder/actor/list.do" pagesize="10" class="displaytag">
	<spring:message code="folder.name" var="name"/>
	<display:column property="name" title="${name}" />
	
	<spring:message code="folder.numberChildren" var="numberChildren"/>
	<display:column title="${numberChildren}" ><p>${row.children.size()}</p></display:column>
	<display:column>
		<a href="folder/actor/list.do?parentId=${row.id}"><spring:message code="folder.seeChildren"/> </a>
	</display:column>
	
	
	<spring:message code="folder.numberMessages" var="numberMessages"/>
	<display:column title="${numberMessages}" ><p>${row.messages.size()}</p></display:column>
	
	<spring:message code="folder.messages" var="messages"/>
	<display:column title="${messages}">
		<jstl:if test="${row.messages.size() != 0 }">
			<a href="message/actor/list.do?folderId=${row.id}"><spring:message code="folder.seeMessages"/> </a>
		</jstl:if>
	</display:column>

	<spring:message code="folder.move" var="move"/>
	<display:column title="${move}">
		<jstl:if test="${row.name != 'In box' and row.name != 'Out box' and row.name != 'Notification box' and row.name != 'Trash box' and row.name != 'Spam box'}">
			<a href="folder/actor/move.do?folderId=${row.id}"><spring:message code="folder.move"/></a>
		</jstl:if>
	</display:column>

	<spring:message code="folder.edit" var="edit"/>
	<display:column title="${edit}">
		<jstl:if test="${row.name != 'In box' and row.name != 'Out box' and row.name != 'Notification box' and row.name != 'Trash box' and row.name != 'Spam box'}">
			<a href="folder/actor/edit.do?folderId=${row.id}"><spring:message code="folder.edit"/></a>
		</jstl:if>
	</display:column>
	
</display:table>

<div id="buttonFolder">
	<p style="color:blue; cursor:pointer; text-decoration:underline"><spring:message code="folder.createChildren"/></p>
</div>

<div id="formFolder" style="display:none">
<p><b><spring:message code="folder.newFolder"/>:</b></p>
<form action="folder/actor/save.do" method="post">
	<input type="text" id="name" name="name"/>
	<jstl:if test="${main}">
		<input type="hidden" id="parent" name="parent" value="">
	</jstl:if>
	<jstl:if test="${main == false}">
		<input type="hidden" id="parent" name="parent" value="${parent}">
	</jstl:if>
	<input type="submit" value="<spring:message code='folder.submit'/>"/>
</form>
</div>

<script>
	$("#buttonFolder").click(function(){
		$("#buttonFolder").css("display", "none");
		$("#formFolder").css("display", "block");
	});
</script>
