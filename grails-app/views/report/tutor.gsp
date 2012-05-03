
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${course.idAndTitle}" />
        <title><g:message code="report.tutor.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="report.tutor.label" args="[entityName]" /></h1>
               
            <p>
            The following chart shows the actual comment counts for the different tutors
            within this course. 
            </p>

            <div id="tutor_placeholder" style="width:600px;height:300px"></div>
                
            <g:clusteredActualChart ref="tutor_placeholder" summary="${tutorSummary}"/>
                     
        </div>
        </div>
    </body>
</html>
