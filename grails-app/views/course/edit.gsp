
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
            <g:form action="update" method="post">
            <g:hiddenField name="version" value="${courseInstance?.version}" />
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="id"><g:message code="course.id.label" default="Course Code" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'id', 'errors')}">
                                <g:hasErrors bean="${courseInstance}" field="id">
                            	<g:renderErrors bean="${courseInstance}" as="list" field="id"/>
                            	</g:hasErrors>
                                <g:textField disabled="disabled" name="id" value="${courseInstance.id}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courseTitle"><g:message code="course.courseTitle.label" default="Course Title" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'courseTitle', 'errors')}">
                                <g:hasErrors bean="${courseInstance}" field="courseTitle">
                            	<g:renderErrors bean="${courseInstance}" as="list" field="courseTitle"/>
                            	</g:hasErrors>
                                <g:textField name="courseTitle" value="${courseInstance?.courseTitle}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <span class="button"><g:submitButton name="save" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
            </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
