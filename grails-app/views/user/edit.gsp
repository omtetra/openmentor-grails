<%@ page import="uk.org.openmentor.auth.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /> ${fieldValue(bean: userInstance, field: "username")}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:set var='url'><g:createLink action="update"/></g:set>
            <g:form action="update" method="get" data-url="${url}">
                <g:hiddenField name="id" value="${userInstance.username}" />
                <g:hiddenField name="version" value="${userInstance.version}" />
	            <div class="dialog">
	                <table>
	                    <tbody>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="user.username.label" default="Username" /></td>
	                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "username")}</td>
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="user.roles.label" default="Additional roles" colspan="2"/></td>
	                        </tr>
	                        <tr class="prop">
	                            <td valign="top" class="value" colspan="2">
	                            <g:each in="${availableRoles}" status="i" var="availableRole">
	                            	<g:checkBox id="${'role_' + availableRole}" 
	                            	            name="${availableRole}" 
	                            	            value="${availableRole}" 
	                            	            checked="${userRoles.contains(availableRole)}" />
	                            	<label for="${'role_' + availableRole }">${availableRole}</label><br/>
	                            </g:each>
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	            <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
	            </div>
	         </g:form>
        </div>
    </body>
</html>
