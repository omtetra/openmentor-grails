
<%@ page import="uk.org.openmentor.courseinfo.Tutor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tutor.label', default: 'Tutor')}" />
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
                            <td valign="top" class="name"><g:message code="tutor.tutorId.label" default="Tutor ID" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: tutorInstance, field: "tutorId")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tutor.givenName.label" default="Given Name" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: tutorInstance, field: "givenName")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tutor.familyName.label" default="Family Name" />:</td>
                            <td valign="top" class="value">${fieldValue(bean: tutorInstance, field: "familyName")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tutor.courses.label" default="Courses" />:</td>
                            <td valign="top" class="value"><%= tutorInstance.courses.collect { it.courseId }.sort().join(', ') %></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="nav">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${tutorInstance.tutorId}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
