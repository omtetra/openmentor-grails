
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
            <h2><g:message code="default.list.label" args="[entityName]" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <div class="buttons">
            </div>
            <div class="list">
                <table class="table table-striped">
                    <thead>
                        <tr>         
                            <g:sortableColumn property="id" title="${message(code: 'course.id.label', default: 'Course Code')}" />
                            <g:sortableColumn property="courseTitle" title="${message(code: 'course.courseTitle.label', default: 'Course Title')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${fieldValue(bean: courseInstance, field: 'id')}">${fieldValue(bean: courseInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: courseInstance, field: "courseTitle")}</td>
                            <td>
                                <g:link class="btn btn-small" action="show" id="${fieldValue(bean: courseInstance, field: 'id')}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${courseInstanceTotal}"/>
            </div>
            
            <div class="nav">
            	<span class="menuButton"><g:link class="btn create" action="create"><g:message code="default.button.create.label" default="Create" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
