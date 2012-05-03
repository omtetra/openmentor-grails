
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${course.idAndTitle}" />
        <title><g:message code="report.student.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="report.student.label" args="[entityName]" /></h1>
                   
			<p>
            The following chart shows the actual comment counts for the different tutors
            within this course. 
            </p>

            <div id="student_placeholder" style="width:600px;height:300px"></div>
                
            <g:clusteredActualChart ref="student_placeholder" summary="${studentSummary}"/>     
        </div>
        </div>
    </body>
</html>
