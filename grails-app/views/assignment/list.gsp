
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="assignment.list.label" args="${[courseInstance.id]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.list.label" args="${[courseInstance.id]}" /></h2>
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
                            <g:sortableColumn property="courseId" title="${message(code: 'assignment.courseId.label', default: 'Course Code')}" />
                            <g:sortableColumn property="code" title="${message(code: 'assignment.code.label', default: 'Assignment')}" />
                            <g:sortableColumn property="title" title="${message(code: 'assignment.title.label', default: 'Title')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${assignmentInstanceList}" status="i" var="assignmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: assignmentInstance, field: "courseId")}</td>
                            <td>${fieldValue(bean: assignmentInstance, field: "code")}</td>
                            <td>${fieldValue(bean: assignmentInstance, field: "title")}</td>
                            <td>
                                <g:link class="btn btn-small" action="show" id="${fieldValue(bean: assignmentInstance, field: 'id')}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
            <div class="nav">
            	<span class="menuButton"><g:link class="btn create" action="create"><g:message code="default.button.create.label" default="Create" /></g:link></span>
            </div>
        </div>
        </div>
    </body>
</html>
