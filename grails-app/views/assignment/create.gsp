<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assignment.label', default: 'Assignment')}" />
        <title><g:message code="assignment.create.label" args="${[courseInstance.id]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.create.label" args="${[courseInstance.id]}" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <g:form action="save" method="post" class="form-horizontal">
              <div class="control-group">
                <label class="control-label" for="courseId"><g:message code="assignment.courseId.label" default="Course Code" />:</label>
                <div class="controls">
                  <g:textField name="courseId" value="${courseInstance.courseId}" readonly="readonly" />
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: assignmentInstance, field: 'code', 'error')}">
                <label class="control-label" for="code"><g:message code="assignment.code.label" default="Assignment Code" />:</label>
                <div class="controls">
                  <g:textField name="code" value="${assignmentInstance?.code}" placeholder="Assignment Code" />
                  <g:hasErrors bean="${assignmentInstance}" field="code">
                    <span class="help-inline"><g:renderErrors bean="${assignmentInstance}" as="list" field="code"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: assignmentInstance, field: 'title', 'error')}">
                <label class="control-label" for="title"><g:message code="assignment.title.label" default="Assignment Title" />:</label>
                <div class="controls">
                  <g:textField name="title" value="${assignmentInstance?.title}" placeholder="Assignment Title" />
                  <g:hasErrors bean="${assignmentInstance}" field="title">
                    <span class="help-inline"><g:renderErrors bean="${assignmentInstance}" as="list" field="title"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:submitButton class="save btn btn-primary" name="create" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
              </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
