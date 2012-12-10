
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<%@ page import="uk.org.openmentor.courseinfo.Student" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assignment.label', default: 'Assignment')}" />
        <title><g:message code="assignment.create.label" args="${[courseInstance.id]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="assignment.create.label" args="${[courseInstance.id]}" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="save" method="post">
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courseId"><g:message code="assignment.courseId.label" default="Course ID" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: assignmentInstance, field: 'courseId', 'errors')}">
                                <g:textField name="courseId" value="${courseInstance.id}" readonly="readonly" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="code"><g:message code="assignment.code.label" default="Code" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: assignmentInstance, field: 'code', 'errors')}">
                            	<g:hasErrors bean="${assignmentInstance}" field="code">
                            	<g:renderErrors bean="${assignmentInstance}" as="list" field="code"/>
                            	</g:hasErrors>
                                <g:textField name="code" value="${assignmentInstance?.code}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="title"><g:message code="assignment.title.label" default="Title" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: assignmentInstance, field: 'title', 'errors')}">
                                <g:hasErrors bean="${assignmentInstance}" field="title">
                            	<g:renderErrors bean="${assignmentInstance}" as="list" field="title"/>
                            	</g:hasErrors>
                                <g:textField name="title" value="${assignmentInstance?.title}" />
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
