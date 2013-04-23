<%@ page import="uk.org.openmentor.config.Category" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${params.id}" />
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
                <li><g:link action="assignment" params="${[id: params.id]}">Go back to the chart view</g:link></li>
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
                            <td class="number"><p>${summary.data.get(band)?.actual ?: 0}</p></td>
                            <td class="number"><p>${summary.data.get(band)?.ideal ?: 0}</p></td>
                            <td class="comments">
                                <g:set var="limit" value="${3}"/>
                                <g:set var="comments" value="${summary.data.get(band)?.comments?.unique() ?: []}" />
                                <g:set var="c" value="${comments.size()}" />
                                <g:set var="limit" value="${Math.min(3, c)}" />
                                <g:set var="firstComments" value="${comments[0..<limit]}" />
                                <g:set var="restComments" value="${comments[limit..<c]}" />
                                <g:each var="comment" in="${firstComments}">
                                    <p>${comment}</p>
                                </g:each>
                                <g:if test="${restComments.size() > 0}">
                                <div id="comment-collapse-${bandIndex}">
                                <p><a data-toggle="collapse" data-parent="#comment-collapse-${bandIndex}" href="#comments-${bandIndex}">
                                <i>...(and ${restComments.size()} more)...</i>
                                </a></p>
                                <div id="comments-${bandIndex}" class="collapse out">
                                <g:each var="comment" in="${restComments}">
                                    <p>${comment}</p>
                                </g:each>
                                </div>
                                </div>
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
