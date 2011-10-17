
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
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="buttons">
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>         
                            <g:sortableColumn property="courseId" title="${message(code: 'course.courseId.label', default: 'Course Code')}" />
                            <g:sortableColumn property="courseTitle" title="${message(code: 'course.courseTitle.label', default: 'Course Title')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${fieldValue(bean: courseInstance, field: 'courseId')}">${fieldValue(bean: courseInstance, field: "courseId")}</g:link></td>
                            <td>${fieldValue(bean: courseInstance, field: "courseTitle")}</td>
                            <td>
                                <g:link action="show" id="${fieldValue(bean: courseInstance, field: 'courseId')}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${courseInstanceTotal}"/>
            </div>
        </div>
        </div>
    </body>
</html>
