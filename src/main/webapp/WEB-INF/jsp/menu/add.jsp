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
			<label class="layui-form-label lw"><em>*</em>父级菜单</label>
			<div class="layui-input-inline">
				<select name="fMenu" id="fMenu" lay-verify="fMenu">
					<option value="-99">无</option>
					<c:forEach items="${menus}" var="menu">
						<option value="${menu.id }">${menu.menuName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>菜单名</label>
			<div class="layui-input-inline">
				<input type="text" name="menuName" id="menuName" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>访问路径</label>
			<div class="layui-input-inline">
				<input type="text" name="path" id="path" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">例如:/menu/list,没有路径用输入<em>*</em></div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-inline">
				<button type="button" class="layui-btn" onclick="doAdd()" style="margin: 0 0 0 100px;"><i class="layui-icon layui-icon-ok-circle"></i> 确认新增</button>
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
		function doAdd(){
			var fMenu = $("#fMenu").val().trim();
			if(fMenu == '' || fMenu == undefined){
				layer.msg('请选择父级菜单',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var menuName = $("#menuName").val();
			if(menuName == '' || menuName == undefined){
				layer.msg('菜单名不为空',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var path = $("#path").val();
			if(path == '' || path == undefined){
				layer.msg('菜单路径不为空',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/menu/doAdd",
				data : {
					"pid" : fMenu,
					"menuName" : menuName,
					"path" : path
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
