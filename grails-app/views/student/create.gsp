
<%@ page import="uk.org.openmentor.courseinfo.Student" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
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
                            	<label for="studentId"><g:message code="student.studentId.label" default="Student ID" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'studentId', 'errors')}">
                                <g:textField name="studentId" value="${studentInstance?.studentId}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="givenName"><g:message code="student.givenName.label" default="Given Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'givenName', 'errors')}">
                                <g:textField name="givenName" value="${studentInstance?.givenName}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="familyName"><g:message code="student.familyName.label" default="Family Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'familyName', 'errors')}">
                                <g:textField name="familyName" value="${studentInstance?.familyName}" />
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
