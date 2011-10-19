
<%@ page import="uk.org.openmentor.courseinfo.Tutor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tutor.label', default: 'Tutor')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="save" method="post">
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="tutorId"><g:message code="tutor.tutorId.label" default="Tutor ID" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'tutorId', 'errors')}">
                                <g:textField name="tutorId" value="${tutorInstance?.tutorId}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="givenName"><g:message code="tutor.givenName.label" default="Given Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'givenName', 'errors')}">
                                <g:textField name="givenName" value="${tutorInstance?.givenName}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="familyName"><g:message code="tutor.familyName.label" default="Family Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'familyName', 'errors')}">
                                <g:textField name="familyName" value="${tutorInstance?.familyName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
            </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
