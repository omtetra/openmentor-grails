
<%@ page import="uk.org.openmentor.courseinfo.Assignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assignment.label', default: 'Assignment')}" />
        <title><g:message code="assignment.upload.label" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.upload.label" /></h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

			<g:hasErrors bean="${sub}">
            <div class="errors">
                <g:renderErrors bean="${sub}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:uploadForm action="save" method="post" class="form-horizontal">
              <div class="control-group  ${hasErrors(bean: cmd, field: 'courseId', 'errors')}">
                <label class="control-label" for="courseId"><g:message code="course.courseId.label" default="Course ID" />:</label>
                <div class="controls">
                  <g:textField name="courseId" value="${courseInstance.courseId}" readonly="readonly" />
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: cmd, field: 'assignmentId', 'error')}">
                <label class="control-label" for="assignment"><g:message code="assignment.label" default="Assignment" />:</label>
                <div class="controls">
                  <g:select 
                    noSelection="['':'-Choose assignment-']"
                    name="assignmentId" 
                    from="${assignmentsList}" 
                    optionKey="id" 
                    value="${cmd?.assignmentId}"
                    optionValue="code" />
                  <g:hasErrors bean="${cmd}" field="assignmentId">
                  <span class="help-inline"><g:renderErrors bean="${cmd}" as="list" field="assignmentId"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: cmd, field: 'dataFile', 'error')}">
                <label class="control-label" for="dataFile"><g:message code="assignment.dataFile.label" default="File" />:</label>
                <div class="controls">
                  <input type="file" name="dataFile" id="file" accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" />
                  <span class="help-block">Word files of all types are supported, maximum size is 16Mb</span>
                  <g:hasErrors bean="${cmd}" field="dataFile">
                  <span class="help-inline"><g:renderErrors bean="${cmd}" as="list" field="dataFile"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: cmd, field: 'grade', 'error')}">
                <label class="control-label" for="grade"><g:message code="assignment.grade.label" default="Marks category given" />:</label>
                <div class="controls">
                  <g:select 
                    noSelection="['':'-Choose grade-']"
                    name="grade" 
                    value="${cmd?.grade}"
                    from="${grades}" />
                  <g:hasErrors bean="${cmd}" field="grade">
                  <span class="help-inline"><g:renderErrors bean="${cmd}" as="list" field="grade"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: cmd, field: 'tutorIds', 'error')}">
                <label class="control-label" for="tutorIds"><g:message code="default.tutor.label" default="Tutor" />:</label>
                <div class="controls">
                  <g:select 
                    noSelection="['':'-Choose tutor-']"
                    name="tutorIds" 
                    from="${courseInstance.tutors}" 
                    value="${cmd?.tutorIds}"
                    optionKey="id" 
                    optionValue="idAndName" />
                  <g:hasErrors bean="${cmd}" field="tutorIds">
                  <span class="help-inline"><g:renderErrors bean="${cmd}" as="list" field="tutorIds"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: cmd, field: 'studentIds', 'error')}">
                <label class="control-label" for="studentIds"><g:message code="default.student.label" default="Student" />:</label>
                <div class="controls">
                  <g:select 
	                 noSelection="['':'-Choose student-']"
	                 name="studentIds" 
	                 from="${courseInstance.students}" 
	                 value="${cmd?.studentIds}"
	                 optionKey="id" 
	                 optionValue="idAndName" />
                  <g:hasErrors bean="${cmd}" field="studentIds">
                  <span class="help-inline"><g:renderErrors bean="${cmd}" as="list" field="studentIds"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:submitButton class="save btn btn-primary" name="upload" value="${message(code: 'default.button.upload.label', default: 'Upload')}" />
                </div>
              </div>
            </g:uploadForm>
		</div>
		</div>
	</body>
</html>
