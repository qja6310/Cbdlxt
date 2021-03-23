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
	<input type="hidden" value="${areastr }" id="areastr">
	<form class="layui-form my-form" lay-filter="form">
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>区域</label>
			<div class="layui-input-inline">
				<select class="form-control" id="area">
					<option value="">请选择</option>
					<c:forEach items="${areas }" var="item">
						<option value="${item }">${item }区</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw">新区域</label>
			<div class="layui-input-inline">
				<input type="text" id="newArea" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">区</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>车位号码</label>
			<div class="layui-input-inline">
				<input type="text" id="number" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label lw"><em>*</em>注意</label>
			<div class="layui-form-mid layui-word-aux">优先使用下拉框中的区域</div>
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
			var number = $("#number").val().trim();
			if(number == '' || number == undefined){
				layer.msg('请输入车位号码',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var area = $("#area").val();
			var newArea = $("#newArea").val().trim();
			if(area == '' && newArea == ''){
				layer.msg('请选择区域或输入新区域',{
					icon: 2,
					time: 2000
				});
				return;
			}
			if(area != ''){
				$("#newArea").val("");
			}else{
				area = newArea;
				if($("#areastr").val().indexOf(area) != -1){
					layer.msg('该区域已存在，请在下拉框中选择',{
						icon: 2,
						time: 2000
					});
					return;
				}
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/cwgl/doAdd",
				data : {
					"number" : number,
					"area" : area
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						setTimeout('close()',2000);//jq  时间延时器
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
