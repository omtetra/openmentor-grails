
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
            <h2><g:message code="report.student.label" args="[entityName]" /></h2>
            
            <g:each var="band" in="${bands}" status="i">

            <g:set var="bandData" value="${summary.filter([null, band])}" />
            
            <g:set var="data" value="${bandData.data}" />
            <g:set var="keys" value="${data.keySet() as List}" />
            <g:set var="idealValues" value="${keys.collect { val -> data.get(val)?.ideal ?: 0 }}" />
            <g:set var="actualValues" value="${keys.collect { val -> data.get(val)?.actual ?: 0 }}" />

            <g:if test="${keys.size() > 0}">

            <h3>${band}: ${bandLabels[band]}</h3>

            <table id="tutor_placeholder-${i}-table" class="actual-ideal table table-striped table-condensed">
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
                        <td class='bullet-label'><g:link controller="report" action="tutor" id="${keys[j]}">${keys[j]}</g:link></td>
                        <td class='bullet-ideal'>${idealValues[j]}</td>
                        <td class='bullet-actual'>${actualValues[j]}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            
            </g:if>

            </g:each>
        </div>
        </div>
        <g:javascript>
jQuery(document).ready(function() {
  jQuery(".actual-ideal").bulletChart();
});
        </g:javascript>
    </body>
</html>
