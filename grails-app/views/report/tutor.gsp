
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${params.id}" />
        <title><g:message code="report.summary.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="report.summary.label" args="[entityName]" /></h2>
            
            <p>
            The following chart shows the expected versus actual comment counts for this 
            tutor.
            </p>
                        
            <g:set var="data" value="${summary.data}" />
            <g:set var="keys" value="${data.keySet() as List}" />
            <g:set var="idealValues" value="${keys.collect { val -> data.get(val)?.ideal ?: 0 }}" />
            <g:set var="actualValues" value="${keys.collect { val -> data.get(val)?.actual ?: 0 }}" />
            <table id="tutor_placeholder-table" class="actual-ideal table table-striped table-condensed">
                <thead>
                    <tr> 
                        <td></td>
                        <th scope="col">Ideal</th>
                        <th scope="col">Actual</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${ (0..keys.size()-1)}" var="j">
                    <tr class='bullet'>
                        <td class='bullet-label'>${keys[j]}</td>
                        <td class='bullet-ideal'>${idealValues[j]}</td>
                        <td class='bullet-actual'>${actualValues[j]}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>

            <g:if test="${summary.submissionCount > 0}">
            <h4><g:link action="tutor_details" params="${[id: params.id]}">See detailed information</g:link></h4>
            </g:if>
        </div>
        </div>
        <g:javascript>
jQuery(document).ready(function() {
  jQuery(".actual-ideal").bulletChart();
});
        </g:javascript>
    </body>
</html>
