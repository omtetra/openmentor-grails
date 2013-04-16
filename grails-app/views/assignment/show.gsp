<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="assignment.show.label" args="${[assignmentInstance.code, courseInstance.courseId]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.show.label" args="${[assignmentInstance.code, courseInstance.courseId]}" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <form class="form-horizontal">
              <div class="control-group  ${hasErrors(bean: assignmentInstance, field: 'courseId', 'error')}">
                <label class="control-label" for="courseId"><g:message code="assignment.courseId.label" default="Course Code" />:</label>
                <div class="controls">
                  <g:textField name="courseId" value="${assignmentInstance.course.courseId}" readonly="readonly" />
                  <g:hasErrors bean="${assignmentInstance}" field="courseId">
                    <span class="help-inline"><g:renderErrors bean="${assignmentInstance.course}" as="list" field="courseId"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: assignmentInstance, field: 'code', 'error')}">
                <label class="control-label" for="code"><g:message code="assignment.code.label" default="Assignment Code" />:</label>
                <div class="controls">
                  <g:textField name="code" value="${assignmentInstance?.code}" readonly="readonly" />
                  <g:hasErrors bean="${assignmentInstance}" field="code">
                    <span class="help-inline"><g:renderErrors bean="${assignmentInstance}" as="list" field="code"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: assignmentInstance, field: 'title', 'error')}">
                <label class="control-label" for="title"><g:message code="assignment.title.label" default="Assignment Title" />:</label>
                <div class="controls">
                  <g:textField name="title" value="${assignmentInstance?.title}" readonly="readonly" />
                  <g:hasErrors bean="${assignmentInstance}" field="title">
                    <span class="help-inline"><g:renderErrors bean="${assignmentInstance}" as="list" field="title"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <sec:ifAnyGranted roles="MANAGE_COURSEINFO_ROLE">
                    <g:link class="edit btn btn-primary" name="edit" action="edit" id="${assignmentInstance?.code}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                  </sec:ifAnyGranted>
                </div>
              </div>
            </form>
        </div>
        </div>
    </body>
</html>
