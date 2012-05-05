
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${course.idAndTitle}" />
        <title><g:message code="report.summary.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="report.summary.label" args="[entityName]" /></h1>
            
            <p>
            OpenMentor has currently recorded ${summary.submissionCount} submissions
            containing ${summary.commentCount} unique comments for the 
            course ${entityName}.
            </p>
            
            <p>
            The following chart shows the expected versus actual comment counts for this course.
            </p>
                        
            <div id="course_placeholder" style="width:600px;height:300px"></div>
                
            <g:actualIdealChart ref="course_placeholder" summary="${summary}"/>
        </div>
        </div>
    </body>
</html>
