
<%@ page import="uk.org.openmentor.data.Assignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'assignment.label', default: 'Assignment')}" />
        <title><g:message code="assignment.uploaded.label" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="assignment.uploaded.label" /></h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
			<g:hasErrors bean="${uploadInstance}">
            <div class="errors">
                <g:renderErrors bean="${uploadInstance}" as="list" />
            </div>
            </g:hasErrors>
		</div>
		</div>
	</body>
</html>