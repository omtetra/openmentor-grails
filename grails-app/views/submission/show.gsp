<%@ page import="uk.org.openmentor.data.Submission" %>
<%@ page import="uk.org.openmentor.config.Category" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'submission.label', default: 'Submission')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
	<body>
        <div id="page">
        <div class="body">
            <h1><g:message code="submission.show.label" args="${[submissionInstance.filename]}" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

			<table>
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
								<p>
									<em>${band}</em>
								</p>
							</td>
							<td class="number"><p>${summary.data.getAt(band).actual}</p></td>
							<td class="number"><p>${summary.data.getAt(band).ideal}</p></td>
							<td>
								<g:set var="limit" value="3"/>
								<g:each var="comment" in="${summary.data.getAt(band).comments}" status="commentIndex">
									<p>
										<g:if test="${commentIndex <= limit}">
											${comment}
										</g:if>
									</p>
								</g:each>
								<g:if test="${commentIndex > (limit + 1)}">
									<p>
										<i>...(and ${commentIndex - limit - 1} more)
										</i>
									</p>
								</g:if></td>
						</tr>
					</g:each>
				</tbody>
			</table>

		</div>
    </div>
</html>