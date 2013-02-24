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
                            <g:sortableColumn property="courseId" title="${message(code: 'course.courseId.label', default: 'Course Code')}" />
                            <g:sortableColumn property="courseTitle" title="${message(code: 'course.courseTitle.label', default: 'Course Title')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" params="${[courseId: fieldValue(bean: courseInstance, field: 'courseId')]}">${fieldValue(bean: courseInstance, field: "courseId")}</g:link></td>
                            <td>${fieldValue(bean: courseInstance, field: "courseTitle")}</td>
                            <td>
                                <g:link class="btn btn-info btn-small" action="show" params="${[courseId: fieldValue(bean: courseInstance, field: 'courseId')]}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                                <g:if test="${allowDeletion}">
                                    <g:link class="btn btn-danger btn-small" action="delete" params="${[courseId: fieldValue(bean: courseInstance, field: 'courseId')]}">${message(code: 'default.button.delete.label', default: 'Delete')}</g:link>
                                </g:if>
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
                <sec:ifAnyGranted roles="ROLE_OPENMENTOR-POWERUSER">
                	<span class="menuButton"><g:link class="btn btn-primary create" action="create"><g:message code="default.button.create.label" default="Create" /></g:link></span>
                </sec:ifAnyGranted>
            </div>
        </div>
        </div>
    </body>
</html>
