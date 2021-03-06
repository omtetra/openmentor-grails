<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="default.edit.label" args="[entityName]" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <g:form action="update" method="post" class="form-horizontal">
              <g:hiddenField name="id" value="${courseInstance.id}" />
              <g:hiddenField name="version" value="${courseInstance?.version}" />
              <div class="control-group ${hasErrors(bean: courseInstance, field: 'courseId', 'error')}">
                <label class="control-label" for="courseId"><g:message code="course.courseId.label" default="Course Code" />:</label>
                <div class="controls">
                  <g:textField name="courseId" value="${courseInstance?.courseId}" readonly="true" />
                  <g:hasErrors bean="${courseInstance}" field="courseId">
                    <span class="help-inline"><g:renderErrors bean="${courseInstance}" as="list" field="courseId"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: courseInstance, field: 'courseTitle', 'error')}">
                <label class="control-label" for="courseTitle"><g:message code="course.courseTitle.label" default="Course Title" />:</label>
                <div class="controls">
                  <g:textField name="courseTitle" value="${courseInstance?.courseTitle}" placeholder="Course Title" />
                  <g:hasErrors bean="${courseInstance}" field="courseTitle">
                    <span class="help-inline"><g:renderErrors bean="${courseInstance}" as="list" field="courseTitle"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:submitButton class="save btn btn-primary" name="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                </div>
              </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
