
<%@ page import="uk.org.openmentor.courseinfo.Tutor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tutor.label', default: 'Tutor')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="buttons">
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>         
                            <g:sortableColumn property="tutorId" title="${message(code: 'tutor.tutorId.label', default: 'Tutor ID')}" />
                            <g:sortableColumn property="name" title="${message(code: 'tutor.name.label', default: 'Name')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${tutorInstanceList}" status="i" var="tutorInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${fieldValue(bean: tutorInstance, field: 'tutorId')}">${fieldValue(bean: tutorInstance, field: "tutorId")}</g:link></td>
                            <td>${fieldValue(bean: tutorInstance, field: "name")}</td>
                            <td>
                                <g:link action="show" id="${fieldValue(bean: tutorInstance, field: 'tutorId')}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tutorInstanceTotal}"/>
            </div>
            
            <div class="nav">
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.button.create.label" default="Create" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
