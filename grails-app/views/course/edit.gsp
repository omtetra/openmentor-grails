
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courseId"><g:message code="course.courseId.label.label" default="Course Code" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'courseId', 'errors')}">
                                <g:textField name="courseId" value="${courseInstance?.courseId}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courseTitle"><g:message code="course.courseTitle.label.label" default="Course Title" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'courseTitle', 'errors')}">
                                <g:textField name="courseTitle" value="${courseInstance?.courseTitle}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${courseInstance?.courseId}" />
                    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                </g:form>
            </div>
        </div>
        </div>
    </body>
</html>
