<%@ page import="uk.org.openmentor.auth.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h2><g:message code="default.edit.label" args="[entityName]" /> ${fieldValue(bean: userInstance, field: "username")}</h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
                <g:hasErrors bean="${userInstance}">
                  <div class="errors">
                    <g:renderErrors bean="${userInstance}" as="list" />
                  </div>
                </g:hasErrors>
              </div>
            </g:if>
            <g:form action="update" method="post" class="form-horizontal">
              <g:hiddenField name="id" value="${userInstance.id}" />
              <g:hiddenField name="version" value="${userInstance.version}" />
	            <div class="control-group ${hasErrors(bean: userInstance, field: 'username', 'error')}">
                <label class="control-label" for="username"><g:message code="user.username.label" default="Username" />:</label>
                <div class="controls">
                  <g:textField name="username" value="${userInstance.username}" placeholder="Username" />
                  <g:hasErrors bean="${userInstance}" field="username">
                    <span class="help-inline"><g:renderErrors bean="${userInstance}" as="list" field="username"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: userInstance, field: 'password', 'error')}">
                <label class="control-label" for="password"><g:message code="user.password.label" default="Password" />:</label>
                <div class="controls">
                  <g:passwordField name="password" value="" placeholder="Password" />
                  <g:hasErrors bean="${userInstance}" field="password">
                    <span class="help-inline"><g:renderErrors bean="${userInstance}" as="list" field="password"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <div class="control-group ${hasErrors(bean: userInstance, field: 'confirm', 'error')}">
                <label class="control-label" for="confirm"><g:message code="user.confirm.label" default="Verify" />:</label>
                <div class="controls">
                  <g:passwordField name="confirm" value="" placeholder="Verify" />
                  <g:hasErrors bean="${userInstance}" field="confirm">
                    <span class="help-inline"><g:renderErrors bean="${userInstance}" as="list" field="confirm"/></span>
                  </g:hasErrors>
                </div>
              </div>
              <sec:ifAnyGranted roles="ROLE_OPENMENTOR-ADMIN">
              <div class="control-group">
                <label class="control-label"><g:message code="user.roles.label" default="Roles" />:</label>
                <div class="controls">
                  <g:each in="${availableRoles}" status="i" var="availableRole">
                    <label class="checkbox">
                      <g:checkBox id="${'role_' + availableRole}" 
                        name="${'role_' + availableRole}" 
                        value="${availableRole}"
                        disabled="${availableRole == 'ROLE_OPENMENTOR-USER'}"
                        checked="${userRoles.contains(availableRole)}" />
                      ${availableRole}
                      <g:if test="${availableRole == 'ROLE_OPENMENTOR-USER'}"><g:hiddenField name="role_0" value="${availableRole}" /></g:if>
                    </label>
                  </g:each>
                </div>
              </div>
              </sec:ifAnyGranted>
              <div class="control-group">
                <div class="controls">
                  <g:submitButton class="save btn btn-primary" name="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                </div>
              </div>
	         </g:form>
        </div>
    </body>
</html>
