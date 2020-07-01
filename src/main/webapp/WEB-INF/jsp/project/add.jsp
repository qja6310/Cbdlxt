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
	<form class="layui-form my-form" lay-filter="form">
		<div class="layui-form-item">
			<label class="layui-form-label lwp"><em>*</em>评估代号</label>
			<div class="layui-input-block">
				<input type="text" name="pgNum" id="pgNum" class="layui-input" style="width: 500px;">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lwp"><em>*</em>评估名称</label>
			<div class="layui-input-block">
				<input type="text" name="pgName" id="pgName" class="layui-input" style="width: 500px;">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lwp"><em>*</em>船舶装备名称</label>
			<div class="layui-input-block">
				<input type="text" name="jczbName" id="jczbName" class="layui-input" style="width: 500px;">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lwp"><em>*</em>船舶装备代号</label>
			<div class="layui-input-block">
				<input type="text" name="jczbNum" id="jczbNum" class="layui-input" style="width: 500px;">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lwp"><em>*</em>评估描述</label>
			<div class="layui-input-block">
				<textarea placeholder="请输入内容" class="layui-textarea" id="pgDesc" style="width: 500px;"></textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-inline">
				<button type="button" class="layui-btn" onclick="doAdd()" style="margin: 0 0 0 100px;"><i class="layui-icon layui-icon-ok-circle"></i> 确定</button>
			</div>
		</div>
		
	</form>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			//layui.form.render('select', 'form');
			/* form.on('select(fMenu)', function(data){
				  getFMenu();
			}); */
		});
	</script>
	<script type="text/javascript">
		function doAdd(){
			var pgNum = $("#pgNum").val().trim();
			if(pgNum == '' || pgNum == undefined){
				layer.msg('请输入评估代号',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var pgName = $("#pgName").val();
			if(pgName == '' || pgName == undefined){
				layer.msg('请输入评估名称',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var jczbName = $("#jczbName").val();
			if(jczbName == '' || jczbName == undefined){
				layer.msg('请输入船舶装备名称',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var jczbNum = $("#jczbNum").val();
			if(jczbNum == '' || jczbNum == undefined){
				layer.msg('请输入船舶装备代号',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var pgDesc = $("#pgDesc").val();
			if(pgDesc == '' || pgDesc == undefined){
				layer.msg('请输入评估描述',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/project/doAdd",
				data : {
					"pgNum" : pgNum,
					"pgName" : pgName,
					"jczbName" : jczbName,
					"jczbNum" : jczbNum,
					"pgDesc" : pgDesc
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						//关闭弹框
						var index = parent.layer.getFrameIndex(window.name);
			            parent.layer.close(index);
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
