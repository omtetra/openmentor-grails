
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<%@ page import="uk.org.openmentor.courseinfo.Tutor" %>
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
                <label class="control-label" for="id"><g:message code="course.id.label" default="Tutor ID " />:</label>
                <div class="controls">
                  <g:textField name="id" value="${tutorInstance?.id}" disabled="true" readonly="true" />
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
                  <g:select id="courses" name="courses" optionKey="id" optionValue="id" multiple="${true}"
                            from="${Course.findAll()}" 
                            value="${tutorInstance?.courses}"
                            disabled="true" readonly="true"/>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:link class="edit btn btn-primary" name="edit" action="edit" id="${tutorInstance?.id}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                </div>
              </div>
            </form>
        </div>
        </div>
    </body>
</html>
