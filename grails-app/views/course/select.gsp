<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1>Choose a ${entityName}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" action="select">
            	<table>
                <tr class="prop">
                  	<td class="name">
                        <label for="courseIdPicker"><g:message code="course.courseId.label" default="Course Code" /></label>
                    </td>
                    <td class="value ${hasErrors(bean: course, field: 'courseId', 'errors')}">
                    	<g:select 
              			    noSelection="['':'-Choose course-']"
              				name="courseId" 
              				from="${Course.findAll().sort()}" 
              				optionKey="courseId" 
              				optionValue="idAndTitle" />
                    </td>
                </tr>
                <tr>
                	<td colspan="2">
                		<g:actionSubmit value="Choose" action="select" />
                	</td>
                </tr>
                </table>
            </g:form>
        </div>
        </div>
    </body>
</html>
