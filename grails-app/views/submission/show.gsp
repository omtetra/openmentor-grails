<%@ page import="uk.org.openmentor.data.Submission" %>
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
						<th><g:message code="databook.category.label"/></th>
						<th><g:message code="databook.actual.label"/></th>
						<th><g:message code="databook.ideal.label"/></th>
						<th><g:message code="databook.commentText.label"/></th>
					</tr>
				</thead>

				<tbody>
					<g:each var="band" in="${book.getDataPoints()}" status="bandIndex">
						<tr>
							<td>
								<p>
									<em>${band}</em>
								</p>
							</td>
							<td class="number"><p>${book.getDataSeries("ActualCounts")[bandIndex]}</p></td>
							<td class="number"><p>${book.getDataSeries("IdealCounts")[bandIndex]}</p></td>
							<td>
								<g:set var="limit" value="3"/>
								<g:each var="comment" in="${categorization.getBandComments(band)}" status="commentIndex">
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