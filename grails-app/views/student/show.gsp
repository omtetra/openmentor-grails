
<%@ page import="uk.org.openmentor.courseinfo.Student" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
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
                            <td valign="top" class="name"><g:message code="student.studentId.label" default="Student ID" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: studentInstance, field: "studentId")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="student.givenName.label" default="Given Name" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: studentInstance, field: "givenName")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="student.familyName.label" default="Family Name" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: studentInstance, field: "familyName")}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="nav">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${studentInstance.studentId}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
