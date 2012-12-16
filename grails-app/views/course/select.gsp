<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2>Choose a ${entityName}</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" action="select" class="form-horizontal">
              <div class="control-group ${hasErrors(bean: course, field: 'id', 'error')}">
			    <label class="control-label" for="courseIdPicker"><g:message code="course.id.label" default="Course Code" /></label>
			    <div class="controls">
			      <g:select 
                    noSelection="['':'-Choose course-']"
                    name="id" 
                    from="${Course.findAll().sort()}" 
                    optionKey="id" 
                    optionValue="idAndTitle" />
			    </div>
			  </div>
              <div class="control-group">
                <div class="buttons controls">
                  <span class="button"><g:actionSubmit action="select" class="save btn btn-primary" value="${message(code: 'default.button.choose.label', default: 'Choose')}" /></span>
                </div>
              </div>
            </g:form>
        </div>
        </div>
    </body>
</html>
