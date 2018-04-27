<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do"><img src="images/logo.jpg" alt="Sample Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<!-- Lista de Users -->
		<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
		
		<!-- Acciones de Newspaper -->
		<li><a class="fNiv"><spring:message code="master.page.newspaper" /></a>
			<ul>
				<li class="arrow"></li>
				
				<security:authorize access="!hasRole('ADMIN') and !hasRole('AGENT')">
					<!-- Acciones para todos los usuarios menos Admin -->
					<li><a href="newspaper/list.do"><spring:message code="master.page.newspaper.list" /></a></li>
				</security:authorize>

				
				<security:authorize access="hasRole('USER')">
				<!-- Acciones para los Users -->
					<li><a href="newspaper/user/list.do"><spring:message code="master.page.user.newspaper.created" /></a></li>
					<li><a href="newspaper/user/create.do"><spring:message code="master.page.user.newspaper.create" /></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('AGENT')">
				<!-- Acciones para Agents -->
					<li><a href="newspaper/agent/list.do"><spring:message code="master.page.newspaper.advertised" /></a></li>
					<li><a href="newspaper/agent/list-without.do"><spring:message code="master.page.newspaper.not-advertised" /></a></li>
				</security:authorize>
						 	
										
				<li><a href="newspaper/search.do"><spring:message code="master.page.newspaper.search" /></a></li>
			</ul>
		</li>
		
		<!-- Acciones de Article -->
		<li><a class="fNiv"><spring:message code="master.page.article" /></a>
			<ul>
				<li class="arrow"></li>
				
				<!-- Acciones para todos los usuarios -->
				<li><a href="article/search.do"><spring:message code="master.page.article.search"/></a>
				
				<security:authorize access="hasRole('USER')">
				<!-- Acciones para los User -->
					<li><a href="article/user/list-editable.do"><spring:message code="master.page.article.user.editable" /></a></li>
					<li><a href="article/user/create.do"><spring:message code="master.page.article.user.create" /></a></li>
					<li><a href="article/user/list-published.do"><spring:message code="master.page.article.user.followup" /></a></li>
				</security:authorize>
				
			</ul>
		</li>
		
		<security:authorize access="!hasRole('ADMIN')">
		<!-- Acciones de Volume -->
		<li><a class="fNiv"><spring:message code="master.page.volume" /></a>
			<ul>
				<li class="arrow"></li>
				
				<security:authorize access="!hasRole('CUSTOMER')">
				<!-- Acciones para todos los usuarios excepto Customers -->
					<li><a href="volume/list.do"><spring:message code="master.page.volume.list"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('CUSTOMER')">
				<!-- Acciones para los Customer -->
					<li><a href="volume/customer/list.do"><spring:message code="master.page.customer.volume.list"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('USER')">
				<!-- Acciones para Users -->
					<li><a href="volume/user/create.do"><spring:message code="master.page.user.volume.create"/></a></li>
					<li><a href="volume/user/list-created.do"><spring:message code="master.page.user.volume.list-created"/></a></li>
				</security:authorize>
		
			</ul>
		</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
		<!-- Acciones para Admins -->
			<li><a class="fNiv"><spring:message	code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/admin/dashboard.do"><spring:message code="master.page.admin.dashboard" /></a></li>
					<li><a href="newspaper/admin/list-marked.do"><spring:message code="master.page.admin.newspaper.marked" /></a></li>
					<li><a href="article/admin/list-marked.do"><spring:message code="master.page.admin.article.marked" /></a></li>
					<li><a href="followUp/admin/list-marked.do"><spring:message code="master.page.admin.followup.marked" /></a></li>
					<li><a href="chirp/admin/list-marked.do"><spring:message code="master.page.admin.chirp.marked" /></a></li>
					<li><a href="advertisement/admin/list-marked.do"><spring:message code="master.page.admin.advertisement.marked" /></a></li>
					<li><a href="tabooword/admin/list.do"><spring:message code="master.page.admin.tabooword.list" /></a></li>
				</ul>
			</li>
			
			<!-- Listas de todo para los Admins -->
			<li><a class="fNiv"><spring:message	code="master.page.admin.lists" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="newspaper/admin/list.do"><spring:message code="master.page.admin.newspaper.list" /></a></li>
					<li><a href="article/admin/list.do"><spring:message code="master.page.admin.article.list" /></a></li>
					<li><a href="followUp/admin/list-all.do"><spring:message code="master.page.admin.followup.list"/></a></li>
					<li><a href="chirp/admin/list.do"><spring:message code="master.page.admin.chirp.list" /></a></li>
					<li><a href="volume/admin/list.do"><spring:message code="master.page.admin.volume.list"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
		<!-- Acciones para Users -->
			<li><a class="fNiv"><spring:message code="master.page.user"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/display.do"><spring:message code="master.page.profile.display" /></a></li>
					<li><a href="user/user/followers.do"><spring:message code="master.page.user.followers" /></a></li>
					<li><a href="user/user/following.do"><spring:message code="master.page.user.following" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message code="master.page.access"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/login.do"><spring:message code="master.page.login" /></a></li>
					<li><a href="user/create.do"><spring:message code="master.page.user.register" /></a></li>
					<li><a href="customer/create.do"><spring:message code="master.page.customer.register" /></a></li>
					<li><a href="agent/create.do"><spring:message code="master.page.agent.register" /></a></li>
				</ul>
			</li>
			
		</security:authorize>
			
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
			<li><a class="fNiv" href="folder/actor/list.do"><spring:message code="master.page.folder.list"/></a></li>
		</security:authorize>
		
		<!-- Cambio de idioma -->
		<li id="rightB">
			<a class="fNiv" href="<spring:message code="master.page.language.url"/>"><spring:message code="master.page.language"/></a>
		</li>
	</ul>
</div>


