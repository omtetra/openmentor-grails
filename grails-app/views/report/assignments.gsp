
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${course.idAndTitle}" />
        <title><g:message code="report.assignment.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h1><g:message code="report.assignment.label" args="[entityName]" /></h1>
            
            <h2>Positive comments</h2>
            
            <div id="placeholder_positive" style="width:600px;height:300px"></div>
			<g:differenceChart 
				ref="placeholder_positive" 
				summary="${summary}"
				band="${"A"}"
				action="assignment" />
			 
            <h2>Teaching points</h2>
            
            <div id="placeholder_teaching" style="width:600px;height:300px"></div>
			<g:differenceChart 
				ref="placeholder_teaching" 
				summary="${summary}"
				band="${"B"}"
				action="assignment" />

			<h2>Questions</h2>
			
            <div id="placeholder_questions" style="width:600px;height:300px"></div>
			<g:differenceChart 
				ref="placeholder_questions" 
				summary="${summary}"
				band="${"C"}"
				action="assignment" />

			<h2>Negative comments</h2>
                        
            <div id="placeholder_negative" style="width:600px;height:300px"></div>
			<g:differenceChart 
				ref="placeholder_negative" 
				summary="${summary}"
				band="${"D"}"
				action="assignment" />

        </div>
        </div>
    </body>
</html>
