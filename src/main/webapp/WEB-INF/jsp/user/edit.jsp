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
	<input type="hidden" id="id" value="${user.id }"/>
	<form class="layui-form my-form" lay-filter="form">
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>工号</label>
			<div class="layui-input-inline">
				<input type="text" id="number" value="${user.number }" class="layui-input" readonly>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>姓名</label>
			<div class="layui-input-inline">
				<input type="text" id="userName" value="${user.name }" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>年龄</label>
			<div class="layui-input-inline">
				<input type="text" id="age" value="${user.age }" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">岁</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>性别</label>
			<div class="layui-input-inline">
				<select name="sex" id="sex" lay-verify="sex">
					<option value="1" <c:if test="${user.sex == 1 }">selected</c:if>>男</option>
					<option value="0" <c:if test="${user.sex == 0 }">selected</c:if>>女</option>					
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>联系号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" id="phone" value="${user.phone }" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>密码</label>
			<div class="layui-input-inline">
				<input type="password" id="password" value="${user.password }" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-inline">
				<button type="button" class="layui-btn" onclick="doEdit()" style="margin: 0 0 0 100px;"><i class="layui-icon layui-icon-ok-circle"></i> 确认修改</button>
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
		function doEdit(){
			var id = $("#id").val().trim();
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var number = $("#number").val().trim();
			if(number == '' || number == undefined){
				layer.msg('请确认工号',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var userName = $("#userName").val().trim();
			if(userName == '' || userName == undefined){
				layer.msg('请输入姓名',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var age = $("#age").val();
			if(age == '' || age == undefined){
				layer.msg('请输入年龄',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var sex = $("#sex").val();
			if(sex == '' || sex == undefined){
				layer.msg('请输入性别',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var phone = $("#phone").val();
			if(phone == '' || phone == undefined){
				layer.msg('请输入联系号码',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var password = $("#password").val();
			if(password == '' || password == undefined){
				layer.msg('请输入密码',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/user/doEdit",
				data : {
					"id" : id,
					"number" : number,
					"name" : userName,
					"age" : age,
					"sex" : sex,
					"phone" : phone,
					"password" : password
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						setTimeout('close()',2000);
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
		
		function close(){
			//关闭弹框
			var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
		}
	</script>
</body>
</html>
