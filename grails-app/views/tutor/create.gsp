
<%@ page import="uk.org.openmentor.courseinfo.Course" %>
<%@ page import="uk.org.openmentor.courseinfo.Tutor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tutor.label', default: 'Tutor')}" />
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
                            	<label for="id"><g:message code="tutor.id.label" default="Tutor ID" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'id', 'errors')}">
                                <g:hasErrors bean="${tutorInstance}" field="id">
                            	<g:renderErrors bean="${tutorInstance}" as="list" field="id"/>
                            	</g:hasErrors>
                                <g:textField name="id" value="${tutorInstance?.id}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="givenName"><g:message code="tutor.givenName.label" default="Given Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'givenName', 'errors')}">
                                <g:hasErrors bean="${tutorInstance}" field="givenName">
                            	<g:renderErrors bean="${tutorInstance}" as="list" field="givenName"/>
                            	</g:hasErrors>
                                <g:textField name="givenName" value="${tutorInstance?.givenName}" />
                            </td>
                        </tr>
                        <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="familyName"><g:message code="tutor.familyName.label" default="Family Name" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'familyName', 'errors')}">
                                <g:hasErrors bean="${tutorInstance}" field="familyName">
                            	<g:renderErrors bean="${tutorInstance}" as="list" field="familyName"/>
                            	</g:hasErrors>
                                <g:textField name="familyName" value="${tutorInstance?.familyName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                           <tr class="prop">
                        	<td valign="top" class="name">
                            	<label for="courses"><g:message code="tutor.courses.label" default="Courses" />:</label>
                        	</td>
                            <td valign="top" class="value ${hasErrors(bean: tutorInstance, field: 'courses', 'errors')}">
                                <g:hasErrors bean="${tutorInstance}" field="courses">
                            	<g:renderErrors bean="${tutorInstance}" as="list" field="courses"/>
                            	</g:hasErrors>
                                <g:select id="courses" name="courses" optionKey="id" optionValue="id" multiple="${true}"
                                          from="${Course.findAll()}" 
                                          value="${tutorInstance?.courses}"/>
                            </td>
                        </tr>
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
