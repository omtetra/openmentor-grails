
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
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
                            	<label for="courseId"><g:message code="course.courseId.label" default="Course Code" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'courseId', 'errors')}">
                                <g:textField name="courseId" value="${courseInstance?.courseId}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courseTitle"><g:message code="course.courseTitle.label" default="Course Title" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'courseTitle', 'errors')}">
                                <g:textField name="courseTitle" value="${courseInstance?.courseTitle}" />
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
