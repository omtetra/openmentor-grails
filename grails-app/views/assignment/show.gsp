
<%@ page import="uk.org.openmentor.data.Assignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="assignment.show.label" args="${[assignmentInstance.code, courseInstance.id]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="assignment.show.label" args="${[assignmentInstance.code, courseInstance.id]}" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="assignment.courseId.label" default="Course ID" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: assignmentInstance, field: "courseId")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="assignment.code.label" default="Code" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: assignmentInstance, field: "code")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="assignment.title.label" default="Title" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: assignmentInstance, field: "title")}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="nav">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${assignmentInstance.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
