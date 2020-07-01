<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>维修性</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/layer/layer.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/newloading/css/newloading.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
	<input type="hidden" id="taskModelId">
	<input type="hidden" id="jcmId">
	<div>
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="200">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th style="text-align: center;width: 40%;">自然环境适应性</th>
					<th style="text-align: center;width: 40%;">诱发环境适应性</th>
					<th style="text-align: center;width: 20%;">操作</th>
				</tr>
			</thead>
			<tbody id="paramsInfoDiv">
				<c:forEach items="${pis }" var="item">
					<tr>
						<td><input type="text" class="form-control" id="pi24_${item.id }" value="${item.pi24 }"></td>
						<td><input type="text" class="form-control" id="pi25_${item.id }" value="${item.pi25 }"></td>
						<td style='text-align: center;'>
							<a onclick="doEdit('${item.id}')">保存</a>
							<a onclick="doDel('${item.id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
				<tr id="addInfo" style="display:none;">
					<td><input type="text" class="form-control" id="pi24"></td>
					<td><input type="text" class="form-control" id="pi25"></td>
					<td style='text-align: center;'>
						<a onclick="doAdd()">保存</a>
						<a onclick="removeTr()">删除</a>
					</td>
				</tr>
				<tr id='addBtn'>
					<td colspan="3"><button type="button" class="layui-btn layui-btn-fluid" onclick="addShow()"><i class="layui-icon layui-icon-add-1"></i> 添加信息</button></td>
				</tr>
			</tbody>
		</table>
	</div>
	<script type="text/javascript">
		/*页面初始化*/
		$(document).ready(function() {
			
		});	
		
		 //新增
		function addShow(){
			$("#addBtn").css("display","none")
			$("#addInfo").css("display","table-row")
		}
		 //删除新增
		 function removeTr(){
			$("#addBtn").css("display","table-row")
			$("#addInfo").css("display","none")
		 }
		
		//新增
		function doAdd(){
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var jcmId = $("#jcmId").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var pi24 = $("#pi24").val().trim();
			var pi25 = $("#pi25").val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/pi/add",
				data : {
					"taskModelId" : taskModelId,
					"jcmId" : jcmId,
					"sixFunction" : '0005',
					"pi24" : pi24,
					"pi25" : pi25
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						sixFunctionParams();
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
		
		//确认编辑
		function doEdit(id){
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var pi24 = $("#pi24_"+id).val().trim();
			var pi25 = $("#pi25_"+id).val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/pi/edit",
				data : {
					"id" : id,
					"pi24" : pi24,
					"pi25" : pi25
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
		
		//删除
		function doDel(id){
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm('确定删除吗？', {
					icon : 3,
					title : '提示'
				}, function(index) {
					layer.close(index);
					if(id == '' || id == undefined){
						layer.msg('缺失关键参数',{
							icon: 2,
							time: 2000
						});
						return;
					}
					$.ajax({
						type : "post",
						url : "<%=request.getContextPath() %>/pi/doDel",
						data : {
							"id" : id
						},
						dataType : "json",
						success : function(data) {
							var retCode = data.retCode;
							if (retCode == "0000") {
								layer.msg(data.retMsg, {
									icon : 1,
									time : 2000,
								});
								sixFunctionParams();
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
				});
			});
		}
	</script>
	<script>
		// 选择时间和日期
		$(".form-datetime").datetimepicker(
		{
		    weekStart: 1,
		    todayBtn:  1,
		    autoclose: 1,
		    todayHighlight: 1,
		    startView: 2,
		    forceParse: 0,
		    showMeridian: 1,
		    format: "yyyy-mm-dd hh:ii"
		});
		layui.use('laydate', function(){
			var laydate = layui.laydate;
  			//执行一个laydate实例
  			laydate.render({
    			elem: '.layui-data' //指定元素
  			});
		});
	</script>
</body>
</html>