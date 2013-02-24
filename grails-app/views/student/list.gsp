<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
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
                            <g:sortableColumn property="studentId" title="${message(code: 'student.studentId.label', default: 'Student ID')}" />
                            <g:sortableColumn property="name" title="${message(code: 'student.name.label', default: 'Name')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${studentInstanceList}" status="i" var="studentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" params="${[studentId: fieldValue(bean: studentInstance, field: 'studentId')]}">${fieldValue(bean: studentInstance, field: "studentId")}</g:link></td>
                            <td>${fieldValue(bean: studentInstance, field: "name")}</td>
                            <td>
                                <g:link class="btn btn-small" action="show" params="${[studentId: fieldValue(bean: studentInstance, field: 'studentId')]}">${message(code: 'default.button.show.label', default: 'View')}</g:link>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${studentInstanceTotal}"/>
            </div>
            
            <div class="nav">
                <sec:ifAnyGranted roles="ROLE_OPENMENTOR-POWERUSER">
                	<span class="menuButton"><g:link class="create btn" action="create"><g:message code="default.button.create.label" default="Create" /></g:link></span>
                </sec:ifAnyGranted>
            </div>
        </div>
        </div>
    </body>
</html>
