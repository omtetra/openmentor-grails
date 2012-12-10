
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<%@ page import="uk.org.openmentor.courseinfo.Student" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="save" method="post">
            <div>
                <table>
                    <tbody>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="id"><g:message code="student.id.label" default="Student ID" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'id', 'errors')}">
                                <g:hasErrors bean="${studentInstance}" field="id">
                            	<g:renderErrors bean="${studentInstance}" as="list" field="id"/>
                            	</g:hasErrors>
                                <g:textField name="id" value="${studentInstance?.id}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="givenName"><g:message code="student.givenName.label" default="Given Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'givenName', 'errors')}">
                                <g:hasErrors bean="${studentInstance}" field="givenName">
                            	<g:renderErrors bean="${studentInstance}" as="list" field="givenName"/>
                            	</g:hasErrors>
                                <g:textField name="givenName" value="${studentInstance?.givenName}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="familyName"><g:message code="student.familyName.label" default="Family Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'familyName', 'errors')}">
                                <g:hasErrors bean="${studentInstance}" field="familyName">
                            	<g:renderErrors bean="${studentInstance}" as="list" field="familyName"/>
                            	</g:hasErrors>
                                <g:textField name="familyName" value="${studentInstance?.familyName}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courses"><g:message code="student.courses.label" default="Courses" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: studentInstance, field: 'courses', 'errors')}">
                                <g:hasErrors bean="${studentInstance}" field="courses">
                            	<g:renderErrors bean="${studentInstance}" as="list" field="courses"/>
                            	</g:hasErrors>
                                <g:select id="courses" name="courses" optionKey="id" optionValue="id" multiple="${true}"
                                          from="${Course.findAll()}" 
                                          value="${studentInstance?.courses}"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
            </div>
            </g:form>
        </div>
        </div>
        <content tag="postJQuery">
            <g:javascript>
jQuery(document).ready(function() {
  jQuery("#courses") .multiselect({
     noneSelectedText: 'Select courses',
     selectedList: 4
  }).multiselectfilter();
});
            </g:javascript>
        </content>
    </body>
</html>
