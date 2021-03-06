<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div id="page">
        <div class="body">
            <h2><g:message code="default.list.label" args="[entityName]" /></h2>
            <g:if test="${flash.message}">
              <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
            </g:if>
            <div class="buttons">
            </div>
            <div class="list">
                <table class="table table-striped">
                    <thead>
                        <tr>         
                            <g:sortableColumn property="courseId" title="${message(code: 'course.courseId.label', default: 'Course Code')}" />
                            <g:sortableColumn property="courseTitle" title="${message(code: 'course.courseTitle.label', default: 'Course Title')}" />
                			<th>Actions</th>
						</tr>
                    </thead>
                    <tbody>
                    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" params="${[courseId: fieldValue(bean: courseInstance, field: 'courseId')]}">${fieldValue(bean: courseInstance, field: "courseId")}</g:link></td>
                            <td>${fieldValue(bean: courseInstance, field: "courseTitle")}</td>
                            <td>
                                <g:link class="btn btn-info btn-small" action="show" params="${[courseId: fieldValue(bean: courseInstance, field: 'courseId')]}">${message(code: 'default.button.show.label', default: 'Show')}</g:link>
                                <sec:ifAnyGranted roles="MANAGE_COURSEINFO_ROLE">
                                    <g:link class="btn btn-danger btn-small handle-delete" action="delete" data-id="${fieldValue(bean: courseInstance, field: 'courseId')}">${message(code: 'default.button.delete.label', default: 'Delete')}</g:link>
                                </sec:ifAnyGranted>
                           	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${courseInstanceTotal}"/>
            </div>
            
            <div class="nav">
                <sec:ifAnyGranted roles="MANAGE_COURSEINFO_ROLE">
                	<span class="menuButton"><g:link class="btn btn-primary create" action="create"><g:message code="default.new.label" default="New {0}" args="${[entityName] }"/></g:link></span>
                </sec:ifAnyGranted>
            </div>
        </div>
        </div>
		<div id="modal-from-dom" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-header">
		      <a href="#" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
		      <h3>Delete ${entityName}</h3>
		    </div>
		    <div class="modal-body">
		      <p>You are about to delete ${entityName.toLowerCase()} <span class="insert-id"></span>, this cannot be undone.</p>
		      <p>Are you sure you want to proceed?</p>
		    </div>
		    <div class="modal-footer">
		      <a href="delete?id=0" class="btn btn-danger handle-delete-confirm">Yes</a>
		      <a class="btn btn-secondary handle-delete-confirm">No</a>
		    </div>
		</div>
        <content tag="postJQuery">
            <g:javascript>
jQuery(document).ready(function() {
  jQuery(".handle-delete").click(function(e) { 
    e.preventDefault();
    var id = $(this).data('id');
    jQuery('#modal-from-dom').data('id', id).modal('show');
  });
  jQuery(".handle-delete-confirm").click(function(e) {
    jQuery('#modal-from-dom').modal('hide');
    return true;
  });
  jQuery('#modal-from-dom').bind('show', function() {
    var id = jQuery(this).data('id'),
    removeBtn = jQuery(this).find('.btn-danger'),
    href = removeBtn.attr('href');
    removeBtn.attr('href', href.replace(/\?id=[^&;]*/, '?id=' + id));
    jQuery('#modal-from-dom .insert-id').text(id);
  });
});
            </g:javascript>
        </content>
    </body>
</html>
