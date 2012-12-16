<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<%@ page import="uk.org.openmentor.courseinfo.Student" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assignment.label', default: 'Assignment')}" />
        <title><g:message code="assignment.edit.label" args="${[courseInstance.id]}" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.edit.label" args="${[courseInstance.id]}" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <g:form action="update" method="post" class="form-horizontal">
              <g:hiddenField name="id" value="${assignmentInstance.id}" />
              <g:hiddenField name="version" value="${assignmentInstance?.version}" />
              <div class="control-group">
                <label class="control-label" for="courseId"><g:message code="assignment.courseId.label" default="Course ID" />:</label>
                <div class="controls ${hasErrors(bean: assignmentInstance, field: 'courseId', 'errors')}">
                  <g:textField name="courseId" value="${courseInstance.id}" readonly="readonly" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="code"><g:message code="assignment.code.label" default="Code" />:</label>
                <div class="controls ${hasErrors(bean: assignmentInstance, field: 'code', 'errors')}">
                  <g:hasErrors bean="${assignmentInstance}" field="code">
                    <g:renderErrors bean="${assignmentInstance}" as="list" field="code"/>
                  </g:hasErrors>
                  <g:textField name="code" value="${assignmentInstance?.code}" placeholder="Code" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="title"><g:message code="assignment.title.label" default="Title" />:</label>
                <div class="controls ${hasErrors(bean: assignmentInstance, field: 'title', 'errors')}">
                  <g:hasErrors bean="${assignmentInstance}" field="title">
                    <g:renderErrors bean="${assignmentInstance}" as="list" field="title"/>
                  </g:hasErrors>
                  <g:textField name="title" value="${assignmentInstance?.title}" placeholder="Title" />
                </div>
              </div>
              <div class="buttons">
                <span class="button"><g:submitButton name="save" class="save btn btn-primary" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
              </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
