<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/layer/layer.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/newloading/css/newloading.css">
<title></title>
<style type="text/css">
</style>
</head>
<body>
	<form class="layui-form" lay-filter="form">
		<div class="layui-row">
			<div class="layui-col-md12">
				<div class="layui-form-item">
					<div class="layui-input-inline">
						<button type="button" class="layui-btn layui-btn-normal" onclick="add()">
							<i class="layui-icon layui-icon-add-1"></i> 新增质量评估
						</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div>
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="200">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th style="text-align: center; width: 6%;">序号</th>
					<th style="text-align: center;">评估代号</th>
					<th style="text-align: center;">评估名称</th>
					<th style="text-align: center;">船舶装备名称</th>
					<th style="text-align: center;">船舶装备代号</th>
					<th style="text-align: center;">创建者</th>
					<th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">评估描述</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody id="proPageDiv">

			</tbody>
		</table>
	</div>
	<div id="paging"></div>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			//layui.form.render('select', 'form');
		});
		/*页面初始化*/
		$(document).ready(function() {
			page();
		});
		
		function page() {
			$('#proPageDiv').load('<%=request.getContextPath() %>/project/page');
		}
		//弹出一个页面层
		function add(){
	    	layer.open({
	        	type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	        	title: ['新增质量评估','font-size: 15px;font-weight: 500;'],
	        	area: ['700px', '500px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/project/toAdd',
	        	closeBtn:2,
	        	end: function(){
	        		page();
	        	}
	       });
	    }
		
		//弹出一个页面层
		function edit(id){
	    	layer.open({
	        	type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	        	title: ['修改质量评估信息','font-size: 15px;font-weight: 500;'],
	        	area: ['700px', '500px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/project/toEdit?id='+id,
	        	closeBtn:2,
	        	end: function(){
	        		page();
	        	}
	       });
	    }
		
		//删除
		function del(id){
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var message = "是否确认删除？三思啊!!!";
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm(message, {
					icon : 3,
					title : '提示'
				}, function(index) {
					layer.close(index);
					$.ajax({
						type : "post",
						url : "<%=request.getContextPath() %>/project/doDelete",
						data : {
							'id' : id
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
							page();
						},
						error : function(data) {
							layer.msg("操作失败", {
								icon : 2,
								time : 2000,
							});
							return;
						}
					});
				});
			});
		}
		
		//打开
		function openJcm(id,dh){
			$("#dh").text(dh);
			$("#dhDiv").css("display","block");
			$('#content').load('<%=request.getContextPath() %>/jcm/list?projectId='+id);  
		}
	</script>
</body>
</html>
