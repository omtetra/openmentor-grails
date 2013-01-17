<%@ page import="uk.org.openmentor.config.Category" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${course.idAndTitle}" />
        <title><g:message code="report.detailed.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="report.detailed.label" args="[entityName]" /></h2>
            
            <p>
            This page shows the report in more detail, including the full text for the
            comments. From here, you can also:
            </p>
            
            <ul>
                <li><g:link action="course" id="${course.id}">Go back to the chart view</g:link></li>
                <li><a href="#">See all comments</a></li>
            </ul>
            
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th><g:message code="summary.category.label"/></th>
                        <th><g:message code="summary.actual.label"/></th>
                        <th><g:message code="summary.ideal.label"/></th>
                        <th><g:message code="summary.commentText.label"/></th>
                    </tr>
                </thead>

                <tbody>
                    <g:each var="band" in="${Category.getBands()}" status="bandIndex">
                        <tr>
                            <td>
                                <p><em>${band}</em></p>
                            </td>
                            <td class="number"><p>${summary.data.getAt(band).actual}</p></td>
                            <td class="number"><p>${summary.data.getAt(band).ideal}</p></td>
                            <td>
                                <g:set var="limit" value="${3}"/>
                                <g:set var="comments" value="${summary.data.getAt(band).comments}" />
                                <g:each var="comment" in="${comments}" status="commentIndex">
                                    <p><g:if test="${commentIndex < limit}"><g:set var="actual" value="${commentIndex}" />${comment}</g:if></p>
                                </g:each>
                                <g:if test="${comments.size() > limit}">
                                    <p><i>...(and ${comments.size() - limit} more)</i></p>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
            
        </div>
        </div>
    </body>
</html>
