<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="default.show.label" args="[entityName]" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <form class="form-horizontal">
              <div class="control-group">
                <label class="control-label" for="studentId"><g:message code="student.studentId.label" default="Student ID " />:</label>
                <div class="controls">
                  <g:textField name="studentId" value="${studentInstance?.studentId}" disabled="true" readonly="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="givenName"><g:message code="student.givenName.label" default="Given Name" />:</label>
                <div class="controls">
                  <g:textField name="givenName" value="${studentInstance?.givenName}" disabled="true" readonly="true" />
                </div>
              </div>              
              <div class="control-group">
                <label class="control-label" for="familyName"><g:message code="student.familyName.label" default="Family Name" />:</label>
                <div class="controls">
                  <g:textField name="familyName" value="${studentInstance?.familyName}" disabled="true" readonly="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="courses"><g:message code="student.courses.label" default="Courses" />:</label>
                <div class="controls">
                  <g:select id="courses" name="courses" optionKey="courseId" optionValue="courseId" multiple="${true}" class="chzn-select"
                            from="${courseList}" 
                            value="${studentInstance?.courses}"
                            disabled="true" readonly="true"/>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <sec:ifAnyGranted roles="ROLE_OPENMENTOR-POWERUSER">
                    <g:link class="edit btn btn-primary" name="edit" action="edit" params="${[studentId: studentInstance?.studentId]}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                  </sec:ifAnyGranted>
                </div>
              </div>
            </form>
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
