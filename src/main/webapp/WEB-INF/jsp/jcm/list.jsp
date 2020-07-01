<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/layer/layer.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/newloading/css/newloading.css">
</head>
<body>
	<div>
		<div style="position: absolute; border: 0px solid #5FB878; width: 400px; height: 635px;">
			<div style="margin: 10px; text-align: center;">
				<i class="icon icon-folder-close-alt icon-2x" style="margin: 3px 10px;" id="package" onclick="toAdd('P')"></i>
				<i class="layui-icon layui-icon-file-b icon-2x" style="margin: 3px 10px;" id="file" onclick="toAdd('F')"></i>
				<i class="layui-icon layui-icon-refresh icon-2x" style="margin: 3px 10px;" id="file" onclick="loadTree()"></i>
				<i class="layui-icon layui-icon-return icon-2x" style="margin: 3px 10px;" id="file" onclick="hiddenInfo()"></i>
			</div>
			<hr style="height: 1px; background-color: #5FB878">
			<div style="overflow: auto" id="treeDiv"></div>
		</div>
		<div style="position: absolute; left: 420px; display: none;width: 1260px;" id="infoDiv">
			<input type="hidden" id="id"> <input type="hidden" id="pid">
			<input type="hidden" id="type"> <input type="hidden" id="projectId" value="${projectId }">
			<div class="layui-card" style="border: 1px solid #5FB878;">
				<div class="layui-card-header" style="background: #5FB878" id="infoTitleDiv">基本信息</div>
				<div class="layui-card-body layui-form">
					<div class="layui-form-item" style="margin: 0 200px;">
						<label class="layui-form-label">名称</label>
						<div class="layui-input-inline">
							<input type="text" id="infoName" name="infoName"
								class="layui-input">
						</div>
						<label class="layui-form-label">型号</label>
						<div class="layui-input-inline">
							<input type="text" id="modelNum" name="modelNum"
								class="layui-input">
						</div>
					</div>
					<div class="layui-form-item" style="margin: 0 45%;">
						<div class="layui-input-inline" id="addBtnDiv">
							<button type="button" class="layui-btn" onclick="doAdd()">
								<i class="layui-icon layui-icon-ok-circle"></i> 确定
							</button>
						</div>
						<div class="layui-input-inline" id="editBtnDiv"
							style="display: none;">
							<button type="button" class="layui-btn layui-btn-normal"
								onclick="doEdit()">
								<i class="layui-icon layui-icon-edit"></i> 确定
							</button>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-card" style="border: 1px solid #5FB878;display: none;" lay-filter="form" id="sixFunctionDiv">
				<div class="layui-card-header" style="background: #5FB878" id="infoTitleDiv">六性选择</div>
				<div class="layui-card-body" style="text-align: center;">
					<c:forEach items="${functions }" var="item">
						<div class="switch switch-inline" style="margin-left: 50px;">
						  	<input type="checkbox" id="fun${item.code }" onclick="changeSixFunctionStatus('${item.code}')">
							<label>${item.name }</label>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="layui-card" style="border: 1px solid #5FB878;display: none;" lay-filter="form" id="qFunctionDiv">
				<div class="layui-card-header" style="background: #5FB878" id="infoTitleDiv">性能选择</div>
				<div class="layui-card-body" style="text-align: center;">
					<c:forEach items="${qfunctions }" var="item">
						<div class="switch switch-inline" style="margin-left: 50px;">
						  	<input type="checkbox" id="qfun${item.code }" onclick="changeQFunctionStatus('${item.code}')">
							<label>${item.name }</label>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			layui.form.render('checkbox', 'form');
			/* form.on('select(fMenu)', function(data){
				  getFMenu();
			}); */
		});
	</script>
	<script type="text/javascript">
		/*页面初始化*/
		$(document).ready(function() {
			loadTree();
		});
		//首级刷新
		function loadTree(){
			var projectId = $("#projectId").val().trim();
			if(projectId == '' || projectId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$("#treeDiv").html("");
			getJcm('-99',projectId,'treeDiv');
		}
		//选中文件夹或者文件图标换颜色
		function toAdd(type){
			if(type == 'P'){//文件夹
				$("#package").css("color","#5FB878");
				$("#file").css("color","#000000");
			}else if(type == 'F'){//文件
				$("#file").css("color","#5FB878");
				$("#package").css("color","#000000");
			}
			$("#id").val("")
			showInfo('','-99',type);
			changeBtn();
			$("#treeDiv label").each(function(i){
				$(this).css("background-color","#FFFFFF");
			});
			$("#treeDiv i").each(function(i){
				$(this).css("background-color","#FFFFFF");
			});
		}
		//点击文件显示右边信息
		function showInfo(name,pid,type,modelNum){
			$("#pid").val(pid);
			$("#type").val(type);
			if(type == 'P'){
				$("#modelNum").prop("readonly",true);
				$("#infoTitleDiv").html("");
				var icon = $("<i class=\"icon icon-folder-close-alt\" style=\"margin: 0px 20px;\"></i>");
				$("#infoTitleDiv").append("基本信息");
				$("#infoTitleDiv").append(icon);
			}else if(type == 'F'){
				$("#modelNum").prop("readonly",false);
				$("#infoTitleDiv").html("");
				var icon = $("<i class=\"layui-icon layui-icon-file-b\" style=\"margin: 0px 20px;\"></i>");
				$("#infoTitleDiv").append("基本信息");
				$("#infoTitleDiv").append(icon);
			}
			$("#infoDiv").css("display","block");
			$("#infoName").val(name);
			if(modelNum != '' && modelNum != undefined){
				$("#modelNum").val(modelNum);
			}else{
				$("#modelNum").val("");
			}
			
		}
		//点击文件夹隐藏右边信息
		function hiddenInfo(){
			$("#package").css("color","#000000");
			$("#file").css("color","#000000");
			$("#treeDiv label").each(function(i){
				$(this).css("background-color","#FFFFFF");
			});
			$("#treeDiv i").each(function(i){
				$(this).css("background-color","#FFFFFF");
			});
			$("#infoDiv").css("display","none");
		}
		//新增按钮和修改按钮的切换
		function changeBtn(){
			var id = $("#id").val().trim();
			if(id == '' || id == undefined){//新增按钮隐藏，修改显示
				$("#addBtnDiv").css("display","block");
				$("#editBtnDiv").css("display","none");
			}else{//修改按钮隐藏，新增显示
				$("#editBtnDiv").css("display","block");
				$("#addBtnDiv").css("display","none");
			}
		}
		
		//获取树
		function getJcm(pid,projectId,fDiv){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/getJcm",
				data : {
					"pid" : pid,
					"projectId" : projectId
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					var list = data.jcms;
					showTree(retCode,list,fDiv)
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
		
		//显示树
		function showTree(retCode,list,fDiv){//参数分别是返回码,数组,文件夹id
			var fdiv = $("#"+fDiv);
		 	//fdiv.html('');
			if(retCode == '0000'){
				var ul ;
				if(fDiv == 'treeDiv'){//首级
					ul = $("<ul class=\"tree tree-lines\" data-ride=\"tree\"></ul>");
				}else{
					$("#"+fDiv).addClass("has-list open in");
					ul = $("<ul></ul>");
				}
				for(var i = 0; i < list.length; i++){
					if(list[i].type == 'P'){
						var li = $("<li id=\"pDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"icon icon-folder-close-alt\" id=\"pIcon"+list[i].id+"\"></i>");
						icon.attr("key",list[i].id);
						icon.on("click",function(){
							var id = $(this).attr("key");
							packageOpenAndClose(id,'pDiv'+id);
						});
						var label = $("<label style=\"margin-left: 10px;\" id=\"infoName"+list[i].id+"\">"+list[i].name+"</label>");
						label.attr("key",list[i].id);
						label.attr("name",list[i].name);
						label.attr("pid",list[i].pid);
						label.attr("type",list[i].type);
						label.on("click",function(){
							var key = $(this).attr("key");
							$("#id").val(key);
							changeBtn();
							var name = $(this).attr("name");
							var pid = $(this).attr("pid");
							var type = $(this).attr("type");
							showInfo(name,pid,type);
							
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$("#treeDiv i").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
							
						});
						var pAdd = $("<i class=\"icon icon-folder-close-alt\" style=\"margin-left: 10px;color: rgb(47,64,86);\"></i>");
						pAdd.attr("pid",list[i].id);
						pAdd.attr("type","P");
						pAdd.on("click",function(){
							$("#id").val("");
							changeBtn();
							var pid = $(this).attr("pid");
							var type = $(this).attr("type");
							showInfo('',pid,type);
							
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$("#treeDiv i").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
						});
						var fAdd = $("<i class=\"layui-icon layui-icon-file-b\" style=\"margin-left: 10px;color: rgb(47,64,86);\"></i>");
						fAdd.attr("pid",list[i].id);
						fAdd.attr("type","F");
						fAdd.on("click",function(){
							$("#id").val("");
							changeBtn();
							var pid = $(this).attr("pid");
							var type = $(this).attr("type");
							showInfo('',pid,type);
							
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$("#treeDiv i").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
						});
						var delIcon = $("<i class=\"layui-icon layui-icon-delete\" style=\"margin-left: 10px;color: #FF5722;\"></i>");
						delIcon.attr("key",list[i].id);
						delIcon.on("click",function(){
							var id = $(this).attr("key");
							doDel(id);
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
						});
						var loading = $("<i class=\"layui-icon layui-icon-refresh\" style=\"margin-left: 10px;color: #2F4056;\"></i>");
						loading.attr("key",list[i].id);
						loading.on("click",function(){
							var id = $(this).attr("key");
							$("#pDiv"+id+" ul").remove();
							$("#pDiv"+id+" h6").remove();
							var projectId = $("#projectId").val().trim();
							if(projectId == '' || projectId == undefined){
								layer.msg('缺失关键参数',{
									icon: 2,
									time: 2000
								});
								return;
							}
							getJcm(id,projectId,"pDiv"+id);
							var flag = $("#pIcon"+id).hasClass("icon-folder-close-alt");
							if(flag){
								$("#pIcon"+id).removeClass("icon-folder-close-alt");
								$("#pIcon"+id).addClass("icon-folder-open-alt");
							}
							
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
						});
						
						li.append(icon);
						li.append(label);
						li.append(pAdd);
						li.append(fAdd);
						li.append(delIcon);
						li.append(loading);
						ul.append(li);
					}else{
						var li = $("<li id=\"pDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"layui-icon layui-icon-file-b\"></i>");
						var label = $("<label style=\"margin-left: 5px;\" id=\"infoName"+list[i].id+"\">"+list[i].name+"</label>");
						label.attr("key",list[i].id);
						label.attr("name",list[i].name);
						label.attr("pid",list[i].pid);
						label.attr("type",list[i].type);
						label.attr("modelNum",list[i].modelNum);
						label.on("click",function(){
							var id = $(this).attr("key");
							$("#id").val(id);
							changeBtn();
							var name = $(this).attr("name");
							var pid = $(this).attr("pid");
							var type = $(this).attr("type");
							var modelNum = $(this).attr("modelNum");
							showInfo(name,pid,type,modelNum);
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$("#treeDiv i").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							
							//显示六性
							$("#sixFunctionDiv").css("display","block");
							getSixFunction();
							//显示性能选择
							$("#qFunctionDiv").css("display","block");
							getQFunction();
						});
						
						var delIcon = $("<i class=\"layui-icon layui-icon-delete\" style=\"margin-left: 10px;color: #FF5722;\"></i>");
						delIcon.attr("key",list[i].id);
						delIcon.on("click",function(){
							var id = $(this).attr("key");
							doDel(id);
							//隐藏六性
							$("#sixFunctionDiv").css("display","none");
							//隐藏性能选择
							$("#qFunctionDiv").css("display","none");
						});
						
						li.append(icon);
						li.append(label);
						li.append(delIcon);
						ul.append(li);
					}
					
				}
				fdiv.append(ul);
			}else{
				if(fDiv == 'treeDiv'){//首级
					var message = '该项目还没添加任何模型，赶紧行动吧！';
					fdiv.append("<h3>"+message+"</h3>");
				}else{
					var message = '该目录下还没添加任何模型，赶紧行动吧！';
					fdiv.append("<h6>"+message+"</h6>");
				}
				
			}
		}
		
		//文件夹的打开与关闭
		function packageOpenAndClose(id,fDiv){
			var flag = $("#pIcon"+id).hasClass("icon-folder-close-alt");
			if(flag){//准备打开
				$("#pIcon"+id).removeClass("icon-folder-close-alt");
				$("#pIcon"+id).addClass("icon-folder-open-alt");
				var projectId = $("#projectId").val().trim();
				if(projectId == '' || projectId == undefined){
					layer.msg('缺失关键参数',{
						icon: 2,
						time: 2000
					});
					return;
				}
				getJcm(id,projectId,fDiv);
			}else{//准备关闭
				$("#pIcon"+id).addClass("icon-folder-close-alt");
				$("#pIcon"+id).removeClass("icon-folder-open-alt");
				$("#"+fDiv).removeClass("has-list open in");
				$("#"+fDiv+" ul").remove();
				$("#"+fDiv+" h6").remove();
			}
		}
		//新增
		function doAdd(){
			var pid = $("#pid").val().trim();
			if(pid == '' || pid == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var type = $("#type").val().trim();
			if(type == '' || type == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var projectId = $("#projectId").val().trim();
			if(projectId == '' || projectId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var name = $("#infoName").val().trim();
			if(name == '' || name == undefined){
				layer.msg('请输入文件夹名或文件名',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var modelNum = $("#modelNum").val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/doAdd",
				data : {
					"pid" : pid,
					"type" : type,
					"projectId" : projectId,
					"name" : name,
					"modelNum" : modelNum
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						$("#id").val(data.id);
						changeBtn();
						if(type == 'F'){
							//显示六性
							$("#sixFunctionDiv").css("display","block");
							//显示性能选择
							$("#qFunctionDiv").css("display","block");
						}
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
		//编辑
		function doEdit(){
			var id = $("#id").val().trim();
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var name = $("#infoName").val().trim();
			if(name == '' || name == undefined){
				layer.msg('请输入文件夹名或文件名',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var modelNum = $("#modelNum").val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/doEdit",
				data : {
					"id" : id,
					"name" : name,
					"modelNum" : modelNum
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						$("#infoName"+id).text(name);
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
			var hint = "是否确认删除？三思啊!!!";
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm(hint, {
					icon : 3,
					title : '提示'
				}, function(index) {
					layer.close(index);
					$.ajax({
						type : "post",
						url : "<%=request.getContextPath()%>/jcm/del",
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
		
		//获取六性
		function getSixFunction(){
			var jcmId = $("#id").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/getSixFunction",
				data : {
					"jcmId" : jcmId
				},
				dataType : "json",
				success : function(data) {
					var list = data.sixFunctions;
					showSixFunction(list);
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
		
		//回显六性
		function showSixFunction(list){
			for(var i = 1; i <= 6; i++){
				$("#fun000"+i).prop("checked",false);
			}
			for(var i = 0; i < list.length; i++){
				$("#fun"+list[i].funCode).prop("checked",true);
			}
		}
		
		//修改状态
		function changeSixFunctionStatus(funCode){
			var jcmId = $("#id").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var isChecked = $("#fun"+funCode).prop('checked');//判断当前复选框是否选中
			var status;
			if(isChecked){
				status = "E";
			}else{
				status = "F";
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/changeSixFunctionStatus",
				data : {
					"jcmId" : jcmId,
					"funCode" : funCode,
					"status" : status
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
		
		//获取性能选择
		function getQFunction(){
			var jcmId = $("#id").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/getQFunction",
				data : {
					"jcmId" : jcmId
				},
				dataType : "json",
				success : function(data) {
					var list = data.qFunctions;
					showQFunction(list);
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
		
		//回显性能选择
		function showQFunction(list){
			for(var i = 1; i <= 4; i++){
				$("#qfun000"+i).prop("checked",false);
			}
			for(var i = 0; i < list.length; i++){
				$("#qfun"+list[i].qfunCode).prop("checked",true);
			}
		}
		
		//修改状态
		function changeQFunctionStatus(funCode){
			var jcmId = $("#id").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var isChecked = $("#qfun"+funCode).prop('checked');//判断当前复选框是否选中
			var status;
			if(isChecked){
				status = "E";
			}else{
				status = "F";
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/jcm/changeQFunctionStatus",
				data : {
					"jcmId" : jcmId,
					"funCode" : funCode,
					"status" : status
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