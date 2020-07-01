<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/layui/css/layui.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/zui/css/zui.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/newloading/css/newloading.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/zui/js/zui.min.js"></script>
</head>
<body>
	<input type="hidden" id="funCode" value="${funCode }">
	<input type="hidden" id="taskModelId" value="${taskModelId}">
	<input type="hidden" id="jcmId" value="${jcmId }">
	<form class="layui-form my-form" lay-filter="form">
		<div class="layui-form-item">
			<label class="layui-form-label lw">数据</label>
			<div class="layui-input-inline">
				<input type="text" id="calResult" value="${jsf.calResult }" class="layui-input">
			</div>
			<div class="layui-input-inline">
				<button type="button" class="layui-btn" onclick="save()"><i class="layui-icon layui-icon-ok-circle"></i> 保存</button>
			</div>
		</div>
		
	</form>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			layui.form.render('select', 'form');
			/* form.on('select(fMenu)', function(data){
				  getFMenu();
			}); */
		});
	</script>
	<script type="text/javascript">
		function save(){
			var funCode = $("#funCode").val();
			var taskModelId = $("#taskModelId").val();
			var jcmId = $("#jcmId").val();
			var calResult = $("#calResult").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/sixFunction/saveResult",
				data : {
					"funCode" : funCode,
					"taskModelId" : taskModelId,
					"jcmId" : jcmId,
					"calResult" : calResult,
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
					} else {
						layer.msg(data.retMsg, {
							icon : 2,
							time : 2000,
						});
					}
				},
				error : function(data) {
					layer.msg("操作失败", {
						icon : 2,
						time : 2000,
					});
					return;
				}
			});
		}
	</script>
</body>
</html>
