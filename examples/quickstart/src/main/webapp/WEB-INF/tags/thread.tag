<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="thread" required="true"
	type="org.springside.examples.quickstart.entity.Thread"%>
<%@ attribute name="ctxpth" required="true"%>

<div class="thread-accordion" id="thread-${thread.id}">
	<div class="accordion-group">
		<div class="accordion-heading thread">

			<a class="accordion-toggle" data-toggle="collapse"
				data-parent="#thread-${thread.id}" href="#collapse-${thread.id}">
				${thread.title} </a>
			<div class="thread-op pull-right">
				<a href="#add-task-to-thread${thread.id}" data-toggle="modal">+Task</a>&nbsp;&nbsp;
				<a href="${ctxpth}/thread/delete/${thread.id}">-Thread</a>
			</div>
		</div>
		<div id="collapse-${thread.id}" class="accordion-body in"
			style="height: auto;">

			<div class="accordion-inner">
				<div class="thread-tasks">
					<ul>
						<c:forEach items="${thread.tasks}" var="task">
							<li><a href="${ctxpth}/task/update/${task.id}">${task.title}</a>&nbsp;
								<a href="${ctxpth}/task/delete/${task.id}">-Task</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="add-task-to-thread${thread.id}" class="modal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h4 id="myModalLabel">Add task to: ${thread.title}</h4>
		<small>Thread Management</small>
	</div>
	<div class="modal-body">

		<form id="inputForm" action="${ctxpth}/task/create" method="post"
			class="form-horizontal">
			<fieldset>

				<div class="control-group">
					<input type="hidden" name="id" value="${task.id}" /> <input
						type="hidden" name="thread_id" value="${thread.id}" />
				</div>

				<div class="control-group">
					<label for="task_title" class="control-label">Task title:</label>
					<div class="controls">
						<input type="text" id="task_title" name="title"
							value="${task.title}" class="input-large required" minlength="3" />
					</div>
				</div>

				<div class="control-group">
					<label for="description" class="control-label">Task Desc:</label>
					<div class="controls">
						<textarea id="description" name="description" class="input-large">${task.description}</textarea>
					</div>
				</div>

				<div class="control-group">
					<label for="submit_btn" class="control-label">Operation:</label>
					<div class="controls">
						<input id="submit_btn" class="btn btn-primary" type="submit"
							value="Submit" />&nbsp;
						<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
					</div>
				</div>

			</fieldset>
		</form>
	</div>
</div>
