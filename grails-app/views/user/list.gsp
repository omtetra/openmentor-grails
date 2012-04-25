<%@ page import="uk.org.openmentor.auth.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

            <div class="list">
                <table>
                    <thead>
                        <tr>         
                            <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />
                            <th>${message(code: 'user.role.label', default: 'Roles')}</th>
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="dialog-trigger" action="edit" id="${fieldValue(bean: userInstance, field: 'username')}">${fieldValue(bean: userInstance, field: "username")}</g:link></td>
                            <td>${userInstance.authorities.collect { it.authority }.sort { it }.join(", ")}</td>
                            <td>
                                <g:link class="dialog-trigger" action="edit" id="${fieldValue(bean: userInstance, field: 'username')}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userInstanceTotal}" />
            </div>
        </div>
        </div>
    </body>
</html>