
<%@ page import="uk.org.openmentor.data.Assignment" %>
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
            <h1><g:message code="assignment.upload.label" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

			<g:hasErrors bean="${sub}">
            <div class="errors">
                <g:renderErrors bean="${sub}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:uploadForm action="save" method="post">
                <div>
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="courseId"><g:message code="course.courseId.label" default="Course ID" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'courseId', 'errors')}">
                                	<g:textField name="courseId" value="${course?.courseId}" disabled="disabled" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.label" default="Assignment" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'assignmentId', 'errors')}">
                         			<g:select 
                         			    noSelection="['':'-Choose assignment-']"
                         				name="assignmentId" 
                         				from="${Assignment.findAllByCourseId(course?.courseId)}" 
                         				optionKey="id" 
                         				optionValue="code" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.filePath.label" default="File" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'filePath', 'errors')}">
                         			<input type="file" name="dataFile" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.grade.label" default="Marks category given" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'grade', 'errors')}">
                         			<g:select 
                         			    noSelection="['':'-Choose grade-']"
                         				name="grade" 
                         				from="${grades}" />
                    			</td>
                    		</tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tutor"><g:message code="default.tutor.label" default="Tutor" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'tutorIds', 'errors')}">
                         			<g:select 
                         			    noSelection="['':'-Choose tutor-']"
                         				name="tutorIds" 
                         				from="${course.tutors}" 
                         				optionKey="tutorId" 
                         				optionValue="idAndName" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="student"><g:message code="default.student.label" default="Student" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: sub, field: 'studentIds', 'errors')}">
                         			<g:select 
                         			    noSelection="['':'-Choose student-']"
                         				name="studentIds" 
                         				from="${course.students}" 
                         				optionKey="studentId" 
                         				optionValue="idAndName" />
                    			</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="upload" class="save" value="${message(code: 'default.button.upload.label', default: 'Upload')}" /></span>
                </div>
            </g:uploadForm>
		</div>
		</div>
	</body>
</html>