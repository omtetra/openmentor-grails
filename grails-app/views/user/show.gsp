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
            <h2><g:message code="default.show.label" args="[entityName]" /> ${fieldValue(bean: userInstance, field: "username")}</h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <form class="form-horizontal">
              <div class="control-group">
                <label class="control-label" for="username"><g:message code="user.username.label" default="Username" />:</label>
                <div class="controls">
                  <g:textField name="username" value="${userInstance.username}" disabled="true" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label"><g:message code="user.roles.label" default="Roles" />:</label>
                <div class="controls">
                  <g:each in="${availableRoles}" status="i" var="availableRole">
                    <label class="checkbox">
                      <g:checkBox id="${'role_' + availableRole}" 
                        name="${availableRole}" 
                        value="${availableRole}"
                        checked="${userRoles.contains(availableRole)}"
                        disabled="true" />
                      ${availableRole}
                    </label>
                  </g:each>
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <g:link class="edit btn btn-primary" name="edit" action="edit" id="${userInstance?.username}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                </div>
              </div>
            </form>
        </div>
    </body>
</html>
