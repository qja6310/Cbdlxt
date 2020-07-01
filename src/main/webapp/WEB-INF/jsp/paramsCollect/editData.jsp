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
	<input type="hidden" id="taskModelId" value="${taskModelId}">
	<input type="hidden" id="jcmId" value="${jcmId }">
	<form class="my-form" >
		<div class="layui-form-item">
			<label class="layui-form-label lw">一级性能</label>
			<div class="layui-input-inline">
				<select class="form-control" id="oneLevel" onchange="showData()">
					<c:if test="${isJqfs == 0 || isJqfs == '0' }"><option value="">请添加性能</option></c:if>
					<c:if test="${isJqfs == 1 || isJqfs == '1' }">
						<c:forEach items="${jqfs }" var="item">
							<option value="${item.qfunCode }">${item.funName }</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<!-- <div class="layui-form-item">
			<label class="layui-form-label lw">二级性能</label>
			<div class="layui-input-inline">
				<select class="form-control" id="twoLevel" onchange="showData()">
					<c:if test="${isJqfs == 0 || isJqfs == '0' }"><option value="">请选择一级性能</option></c:if>
					<c:if test="${isJqfs == 1 || isJqfs == '1' }">
						<c:forEach items="${jqfs1 }" var="item">
							<option value="${item.qfunCode }">${item.funName }</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>  -->
		<div class="layui-form-item">
			<label class="layui-form-label lw">数据</label>
			<div class="layui-input-inline">
				<input type="text" id="calResult" value="${calResult }" class="layui-input">
			</div>
			<div class="layui-input-inline">
				<button type="button" class="layui-btn" onclick="save()"><i class="layui-icon layui-icon-ok-circle"></i> 保存</button>
			</div>
		</div>
		
	</form>
	<script type="text/javascript">
		layui.use('layer', function(){ //独立版的layer无需执行这一句
			layer = layui.layer; //独立版的layer无需执行这一句
		});
	</script>
	<script type="text/javascript">
		function save(){
			//var funCode = $("#twoLevel").val();
			var funCode = $("#oneLevel").val();
			if(funCode == '' || funCode == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			var jcmId = $("#jcmId").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			var calResult = $("#calResult").val();
			if(calResult == '' || calResult == undefined){
				calResult = 0;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/paramsCollect/saveResult",
				data : {
					"funCode" : funCode,
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
		
		//显示二级性能
		function showTwoLevel(){
			var funCode = $("#oneLevel").val();
			if(funCode == '' || funCode == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			var jcmId = $("#jcmId").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/paramsCollect/getQFunction",
				data : {
					"funCode" : funCode,
					"jcmId" : jcmId
				},
				dataType : "json",
				success : function(data) {
					var list = data.jqfs;
					showTwoLevel2(list);
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
		
		//显示二级性能
		function showTwoLevel2(list){
			var twoLevel = $("#twoLevel");
			twoLevel.html("");
			for(var i = 0; i < list.length; i++){
				var option = "<option value='"+list[i].qfunCode +"'>"+list[i].funName+"</option>";
				twoLevel.append(option);
			}
			showData();
		}
		
		//回显数据
		function showData(){
			//var funCode = $("#twoLevel").val();
			var funCode = $("#oneLevel").val();
			if(funCode == '' || funCode == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			var jcmId = $("#jcmId").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg("缺失参数", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/paramsCollect/getData",
				data : {
					"funCode" : funCode,
					"jcmId" : jcmId
				},
				dataType : "json",
				success : function(data) {
					var jqf = data.jqf;
					$("#calResult").val(jqf.calResult);
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
