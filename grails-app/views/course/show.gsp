
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.id.label" default="Course Code" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "id")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.courseTitle.label" default="Course Title" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "courseTitle")}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="nav">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${courseInstance.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
