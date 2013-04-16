<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
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
              <g:hiddenField name="id" value="${studentInstance?.id}" />
              <g:hiddenField name="version" value="${studentInstance?.version}" />
              <div class="control-group ${hasErrors(bean: studentInstance, field: 'studentId', 'error')}">
                <label class="control-label" for="studentId"><g:message code="student.studentId.label" default="Student ID " />:</label>
                <div class="controls">
                  <g:textField name="studentId" value="${studentInstance?.studentId}" placeholder="Student ID" />
                  <g:hasErrors bean="${studentInstance}" field="studentId">
                    <span class="help-inline"><g:renderErrors bean="${studentInstance}" as="list" field="studentId"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: studentInstance, field: 'givenName', 'error')}">
                <label class="control-label" for="givenName"><g:message code="student.givenName.label" default="Given Name" />:</label>
                <div class="controls">
                  <g:textField name="givenName" value="${studentInstance?.givenName}" placeholder="Given Name" />
                  <g:hasErrors bean="${studentInstance}" field="givenName">
                    <span class="help-inline"><g:renderErrors bean="${studentInstance}" as="list" field="givenName"/></span>
                  </g:hasErrors>
                </div>
              </div>              
              <div class="control-group ${hasErrors(bean: studentInstance, field: 'familyName', 'error')}">
                <label class="control-label" for="familyName"><g:message code="student.familyName.label" default="Family Name" />:</label>
                <div class="controls">
                  <g:textField name="familyName" value="${studentInstance?.familyName}" placeholder="Family Name" />
                  <g:hasErrors bean="${studentInstance}" field="familyName">
                    <span class="help-inline"><g:renderErrors bean="${studentInstance}" as="list" field="familyName"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: studentInstance, field: 'courses', 'error')}">
                <label class="control-label" for="courses"><g:message code="student.courses.label" default="Courses" />:</label>
                <div class="controls">
                  <g:select id="courses" name="courses" optionKey="id" optionValue="courseId" multiple="${true}" class="chzn-select"
                            from="${courseList}" 
                            value="${studentInstance?.courses}"/>
                  <g:hasErrors bean="${studentInstance}" field="courses">
                    <span class="help-inline"><g:renderErrors bean="${studentInstance}" as="list" field="courses"/></span>
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
        <content tag="postJQuery">
            <g:javascript>
jQuery(document).ready(function() {
  jQuery(".chzn-select").chosen();
});
            </g:javascript>
        </content>
    </body>
</html>
