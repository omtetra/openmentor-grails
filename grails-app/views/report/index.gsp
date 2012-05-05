
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <p>Now you can now view the following reports for ${course.idAndTitle}</p>
            
            <h2>Summary charts</h2>
            
            <ul style="margin: 1em 0em">
            	<li>
            		<g:link action="course"> 
            		Show course report
            		</g:link>
            	</li>
            	<li>
            		<g:link action="assignments"> 
            		Show assignment report
            		</g:link>
            	</li>
            	<li>
            		<g:link action="tutors"> 
            		Show tutor reports
            		</g:link>
            	</li>
            	<li>
	            	<g:link action="students"> 
            		Show student reports
            		</g:link>
            	</li>
            </ul>

            <p>For more detail, first choose a summary report, then you will be able to explore the analysis in more detail.</p>

        </div>
        </div>
    </body>
</html>
