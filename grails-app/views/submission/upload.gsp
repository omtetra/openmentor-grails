
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
                                    <label for="courseId"><g:message code="course.id.label" default="Course ID" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'courseId', 'errors')}">
                                	<g:textField name="courseId" value="${courseInstance.id}" readonly="readonly" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.label" default="Assignment" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'assignmentId', 'errors')}">
                         			<g:hasErrors bean="${cmd}" field="assignmentId">
                            	    <g:renderErrors bean="${cmd}" as="list" field="assignmentId"/>
                            	    </g:hasErrors>
                                    <g:select 
                         			    noSelection="['':'-Choose assignment-']"
                         				name="assignmentId" 
                         				from="${Assignment.findAllByCourseId(courseInstance.id)}" 
                         				optionKey="id" 
                         				value="${cmd?.assignmentId}"
                         				optionValue="code" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.dataFile.label" default="File" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'dataFile', 'errors')}">
                         			<g:hasErrors bean="${cmd}" field="dataFile">
                            	    <g:renderErrors bean="${cmd}" as="list" field="dataFile"/>
                            	    </g:hasErrors>
                                    <input type="file" name="dataFile" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="assignment"><g:message code="assignment.grade.label" default="Marks category given" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'grade', 'errors')}">
                         			<g:hasErrors bean="${cmd}" field="grade">
                            	    <g:renderErrors bean="${cmd}" as="list" field="grade"/>
                            	    </g:hasErrors>
                                    <g:select 
                         			    noSelection="['':'-Choose grade-']"
                         				name="grade" 
                         				value="${cmd?.grade}"
                         				from="${grades}" />
                    			</td>
                    		</tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tutor"><g:message code="default.tutor.label" default="Tutor" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'tutorIds', 'errors')}">
                         			<g:hasErrors bean="${cmd}" field="tutorIds">
                            	    <g:renderErrors bean="${cmd}" as="list" field="tutorIds"/>
                            	    </g:hasErrors>
                                    <g:select 
                         			    noSelection="['':'-Choose tutor-']"
                         				name="tutorIds" 
                         				from="${courseInstance.tutors}" 
                          				value="${cmd?.tutorIds}"
                         				optionKey="id" 
                         				optionValue="idAndName" />
                    			</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="student"><g:message code="default.student.label" default="Student" />:</label>
                                </td>
                                <td class="value ${hasErrors(bean: cmd, field: 'studentIds', 'errors')}">
                         			<g:hasErrors bean="${cmd}" field="studentIds">
                            	    <g:renderErrors bean="${cmd}" as="list" field="studentIds"/>
                            	    </g:hasErrors>
                                    <g:select 
                         			    noSelection="['':'-Choose student-']"
                         				name="studentIds" 
                         				from="${courseInstance.students}" 
                          				value="${cmd?.studentIds}"
                         				optionKey="id" 
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