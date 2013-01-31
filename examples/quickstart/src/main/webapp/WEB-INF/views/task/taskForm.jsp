<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>任务管理</title>
<!-- 
<script>
	$(document).ready(function() {
		//聚焦第一个输入框
		$("#task_title").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate();
	});
</script>
 -->
<script src="${ctx}/static/bootstrap-wysi/wysihtml5-0.3.0.js"
	type="text/javascript"></script>
<script src="${ctx}/static/bootstrap-wysi/bootstrap-wysihtml5.js"
	type="text/javascript"></script>
</head>

<body>

	<div>
		<form id="inputForm" action="${ctx}/task/${action}" method="post"
			class="form-horizontal">
			<input type="hidden" name="id" value="${task.id}" /> <input
				type="hidden" name="thread_id" value="${task.thread.id}" />
			<fieldset>
				<legend>
					<small>Task Management in <span class="label">${task.thread.title}</span>
					</small>
				</legend>

				<div class="container">
					<div class="hero-unit">
						<h2>Task title</h2>
						<input type="text" id="task_title" name="title"
							value="${task.title}" class="input-large required" minlength="3" />
						<br /> <br /> <br />

						<h2>
							Task note <small>keep editing</small>
						</h2>
						<textarea id="description" name="description" class="textarea"
							placeholder="${task.description}"
							style="width: 810px; height: 200px"></textarea>
					</div>

					<script>
						$('.textarea').wysihtml5({
							"events": {
								"load": function() { 
									console.log("Loaded!");
								},
								"blur": function() { 
									console.log("Blured");
								}
							}
						});
					</script>
				</div>


				<div class="form-actions">
					<input id="submit_btn" class="btn btn-primary" type="submit"
						value="Submit" />&nbsp; <input id="cancel_btn" class="btn"
						type="button" value="Concel" onclick="history.back()" />
				</div>
			</fieldset>
		</form>

	</div>

</body>
</html>
