
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
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
                <label class="control-label" for="id"><g:message code="course.id.label" default="Course Code" />:</label>
                <div class="controls">
                  <g:textField name="id" value="${courseInstance?.id}" disabled="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="courseTitle"><g:message code="course.courseTitle.label" default="Course Title" />:</label>
                <div class="controls">
                  <g:textField name="courseTitle" value="${courseInstance?.courseTitle}" disabled="true" />
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:link class="edit btn btn-primary" name="edit" action="edit" id="${courseInstance?.id}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                </div>
              </div>
            </form>
        </div>
        </div>
    </body>
</html>
