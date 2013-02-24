<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tutor.label', default: 'Tutor')}" />
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
                <label class="control-label" for="tutorId"><g:message code="tutor.tutorId.label" default="Tutor ID " />:</label>
                <div class="controls">
                  <g:textField name="tutorId" value="${tutorInstance?.tutorId}" disabled="true" readonly="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="givenName"><g:message code="tutor.givenName.label" default="Given Name" />:</label>
                <div class="controls">
                  <g:textField name="givenName" value="${tutorInstance?.givenName}" disabled="true" readonly="true" />
                </div>
              </div>              
              <div class="control-group">
                <label class="control-label" for="familyName"><g:message code="tutor.familyName.label" default="Family Name" />:</label>
                <div class="controls">
                  <g:textField name="familyName" value="${tutorInstance?.familyName}" disabled="true" readonly="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="courses"><g:message code="tutor.courses.label" default="Courses" />:</label>
                <div class="controls">
                  <g:select id="courses" name="courses" optionKey="courseId" optionValue="courseId" multiple="${true}" class="chzn-select"
                            from="${courseList}" 
                            value="${tutorInstance?.courses}"
                            disabled="true" readonly="true"/>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <sec:ifAnyGranted roles="ROLE_OPENMENTOR-POWERUSER">
                    <g:link class="edit btn btn-primary" name="edit" action="edit" params="${[tutorId: tutorInstance?.tutorId]}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
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
