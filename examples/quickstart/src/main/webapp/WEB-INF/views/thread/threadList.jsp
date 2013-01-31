<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctxpth" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Thread List</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>

	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>Title:</label> <input type="text" name="search_LIKE_title"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>

	<div class="row">
		<c:forEach items="${threads.content}" var="thread">
			<tags:thread ctxpth="${ctxpth}" thread="${thread}" />
		</c:forEach>
	</div>

	<tags:pagination page="${threads}" paginationSize="7" />

	<div>
		<a class="btn" href="#add-thread" data-toggle="modal">Create
			Thread</a>
	</div>


	<div id="add-thread" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">Ã</button>
			<h4 id="myModalLabel">Create New Thread</h4>
			<small>Thread Management</small>
		</div>
		<div class="modal-body">

			<form id="inputForm" action="${ctxpth}/thread/create" method="post"
				class="form-horizontal">
				<fieldset>

					<div class="control-group">
						<label for="thread_title" class="control-label">Thread
							Title:</label>
						<div class="controls">
							<input type="text" id="thread_title" name="title"
								value="${thread.title}" class="input-large required"
								minlength="3" />
						</div>
					</div>
					<div class="control-group">
						<label for="description" class="control-label">Thread
							Desc:</label>
						<div class="controls">
							<textarea id="description" name="description" class="input-large">${thread.description}</textarea>
						</div>
					</div>
					<!--
						 <input id="cancel_btn" class="btn"
							type="button" value="返回" onclick="history.back()" /> -->

					<div class="control-group">
						<label for="submit_btn" class="control-label">Operation:</label>
						<div class="controls">
							<input id="submit_btn" class="btn btn-primary" type="submit"
								value="Create" />&nbsp; <input id="cancel_btn"
								data-dismiss="modal" class="btn" type="button" value="Cancel" />
						</div>
					</div>

				</fieldset>
			</form>
		</div>
	</div>


</body>
</html>
