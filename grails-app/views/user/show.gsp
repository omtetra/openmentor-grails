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
            <h1><g:message code="default.show.label" args="[entityName]" /> ${fieldValue(bean: userInstance, field: "username")}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
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
                            	            disabled="${true}"
                            	            checked="${userRoles.contains(availableRole)}" />
                            	<label for="${'role_' + availableRole }">${availableRole}</label><br/>
                            </g:each>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="nav">
            	<sec:ifAnyGranted roles="ROLE_OPENMENTOR-USER">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${userInstance.username}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
            	</sec:ifAnyGranted>
            </div>
        </div>
    </body>
</html>
