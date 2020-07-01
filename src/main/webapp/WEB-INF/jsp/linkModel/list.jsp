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
	<input type="hidden" id="taskModelId">
	<div>
		<div class="layui-card model-div">
			<div class="layui-card-header" style="text-align: center;text-align: center;font-size: 25px;font-weight: 600;background-color: #5FB878;">
				任务模型
			</div>
			<div class="layui-card-body" style="overflow: auto" id="treeDiv"></div>
		</div>
		<div style="position: absolute; left: 500px;width: 1260;height: 850px;">
			<div class="layui-card model-div">
				<div class="layui-card-header" style="text-align: center;text-align: center;font-size: 25px;font-weight: 600;background-color: #5FB878;">
					已关联船舶装备模型
				</div>
				<div class="layui-card-body" style="overflow: auto" id="yglTreeDiv"></div>
			</div>
			<div style="position: absolute; left: 525px;height: 850px;">
				<div style="margin: 100px 0;"><button type="button" class="layui-btn" onclick="add()"><i class="layui-icon layui-icon-prev"></i> </button></div>
				<div style="margin: 100px 0;"><button type="button" class="layui-btn" onclick="remove()"><i class="layui-icon layui-icon-next"></i> </button></div>
			</div>
			<div style="position: absolute; left: 700px;height: 850px;">
				<div class="layui-card model-div">
					<div class="layui-card-header" style="text-align: center;text-align: center;font-size: 25px;font-weight: 600;background-color: #5FB878;">
						未关联船舶装备模型
					</div>
					<div class="layui-card-body" style="overflow: auto" id="wglTreeDiv"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		layui.use('laydate', function(){
			var laydate = layui.laydate;
  			//执行一个laydate实例
  			laydate.render({
    			elem: '#startTime' //指定元素
  			});
  			laydate.render({
    			elem: '#endTime' //指定元素
  			});
		});
	</script>
	<script type="text/javascript">
		/*页面初始化*/
		$(document).ready(function() {
			loadTree();
		});
		//首级刷新
		function loadTree(){
			$("#treeDiv").html("");
			getTaskModel('-99','treeDiv');
		}
		
		//获取树
		function getTaskModel(pid,fDiv){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/taskModel/getTaskModel",
				data : {
					"pid" : pid,
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					var list = data.taskModels;
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
						li.append(icon);
						li.append(label);
						ul.append(li);
					}else{
						var li = $("<li id=\"pDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"layui-icon layui-icon-file-b\"></i>");
						var label = $("<label style=\"margin-left: 5px;width: 50px;\" id=\"infoName"+list[i].id+"\">"+list[i].name+"</label>");
						label.attr("key",list[i].id);
						label.on("click",function(){
							var id = $(this).attr("key");
							$("#taskModelId").val(id);
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							getYglJcm("-99","yglTreeDiv");
							getWglJcm("-99","wglTreeDiv");
						});
						li.append(icon);
						li.append(label);
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
				getTaskModel(id,fDiv);
			}else{//准备关闭
				$("#pIcon"+id).addClass("icon-folder-close-alt");
				$("#pIcon"+id).removeClass("icon-folder-open-alt");
				$("#"+fDiv).removeClass("has-list open in");
				$("#"+fDiv+" ul").remove();
				$("#"+fDiv+" h6").remove();
			}
		}
		
		//获取树
		function getWglJcm(pid,fDiv){
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('请确认任务模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/linkModel/getWglJcm",
				data : {
					"pid" : pid,
					"taskModelId" : taskModelId
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					var list = data.jcms;
					showWglTree(retCode,list,fDiv)
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
		function showWglTree(retCode,list,fDiv){//参数分别是返回码,数组,文件夹id
			var fdiv = $("#"+fDiv);
		 	//fdiv.html('');
			if(retCode == '0000'){
				var ul ;
				if(fDiv == 'wglTreeDiv'){//首级
					$("#wglTreeDiv").html('');
					ul = $("<ul class=\"tree tree-lines\" data-ride=\"tree\"></ul>");
				}else{
					$("#"+fDiv).addClass("has-list open in");
					ul = $("<ul></ul>");
				}
				for(var i = 0; i < list.length; i++){
					if(list[i].type == 'P'){
						var li = $("<li id=\"wpDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"icon icon-folder-close-alt\" id=\"wpIcon"+list[i].id+"\" style=\"position: absolute;\"></i>");
						icon.attr("key",list[i].id);
						icon.on("click",function(){
							var id = $(this).attr("key");
							packageOpenAndCloseForJcm(id,'wpDiv'+id);
						});
						var cbDiv = $("<div class=\"checkbox-primary\" style=\"margin-left: 20px;\"></div>");
						var cbox = $("<input type=\"checkbox\" value=\""+list[i].id+"\" id=\"wcb"+list[i].id+"\" disabled=\"disabled\" id=\"primaryCheckbox"+list[i].id+"\">");
						var label = $("<label for=\"primaryCheckbox"+list[i].id+"\">"+list[i].name+"</label>");
						cbDiv.append(cbox);
						cbDiv.append(label);
						li.append(icon);
						li.append(cbDiv);
						ul.append(li);
					}else{
						var li = $("<li id=\"wpDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"layui-icon layui-icon-file-b\" style=\"position: absolute;\"></i>");
						var cbDiv = $("<div class=\"checkbox-primary\" style=\"margin-left: 20px;\"></div>");
						var cbox = $("<input type=\"checkbox\" value=\""+list[i].id+"\" id=\"wcb"+list[i].id+"\" id=\"primaryCheckbox"+list[i].id+"\">");
						cbox.attr("key",list[i].id);
						cbox.on("click",function(){
							var id = $(this).attr("key");
							wcbChecked(id);
						});
						var label = $("<label for=\"primaryCheckbox"+list[i].id+"\">"+list[i].name+"</label>");
						cbDiv.append(cbox);
						cbDiv.append(label);
						li.append(icon);
						li.append(cbDiv);
						ul.append(li);
					}
				}
				fdiv.append(ul);
			}else{
				if(fDiv == 'wglTreeDiv'){//首级
					fdiv.html('');
				}
				
				var message = '该目录下没有未关联模型！';
				fdiv.append("<h6>"+message+"</h6>");
			}
		}
		//文件夹的打开与关闭
		function packageOpenAndCloseForJcm(id,fDiv){
			var f = fDiv.substring(0,1);
			if(f == 'w'){
				var flag = $("#wpIcon"+id).hasClass("icon-folder-close-alt");
				if(flag){//准备打开
					$("#wpIcon"+id).removeClass("icon-folder-close-alt");
					$("#wpIcon"+id).addClass("icon-folder-open-alt");
					getWglJcm(id,fDiv);
				}else{//准备关闭
					$("#wpIcon"+id).addClass("icon-folder-close-alt");
					$("#wpIcon"+id).removeClass("icon-folder-open-alt");
					$("#"+fDiv).removeClass("has-list open in");
					$("#"+fDiv+" ul").remove();
					$("#"+fDiv+" h6").remove();
				}
			}else if(f == 'y'){
				var flag = $("#ypIcon"+id).hasClass("icon-folder-close-alt");
				if(flag){//准备打开
					$("#ypIcon"+id).removeClass("icon-folder-close-alt");
					$("#ypIcon"+id).addClass("icon-folder-open-alt");
					getYglJcm(id,fDiv);
				}else{//准备关闭
					$("#ypIcon"+id).addClass("icon-folder-close-alt");
					$("#ypIcon"+id).removeClass("icon-folder-open-alt");
					$("#"+fDiv).removeClass("has-list open in");
					$("#"+fDiv+" ul").remove();
					$("#"+fDiv+" h6").remove();
				}
			}
		}
		
		//未关联复选框点击触发事件
		function wcbChecked(id){
			var isChecked = $("#wcb"+id).prop('checked');//判断当前复选框是否选中
			if(isChecked){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/linkModel/getJcmPid",
					data : {
						"id" : id
					},
					dataType : "json",
					success : function(data) {
						var list = data.pids;
						wchangeCheckbox(list,'Y')
					},
					error : function(data) {
						layer.msg("操作失败", {
							icon : 2,
							time : 2000,
						});
						return;
					}
				});
			}else{
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/linkModel/getJcmIds",
					data : {
						"id" : id
					},
					dataType : "json",
					success : function(data) {
						var list = data.ids;
						var pid = data.pid;
						wchangeCheckbox(list,'N',pid)
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
		}
		
		//未关联复选框变化
		function wchangeCheckbox(list,flag,pid){
			if('Y' == flag){
				for(var i = 0; i < list.length; i++){
					$("#wcb"+list[i]).prop("checked",true);
				}
			}else if('N' == flag){
				if(pid != '-99' && pid != ''){
					//debugger;
					//var isChecked = false;
					for(var i = 0; i < list.length; i++){
						var isChecked = $("#wcb"+list[i]).prop('checked');//判断当前复选框是否选中
						if(isChecked){
							return;
						}
					}
					//if(!isChecked){
						$("#wcb"+pid).prop("checked",false);
						wcbChecked(pid);
					//}
				}
			}
		}
		
		//关联
		function add(){
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('请确认任务模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			
			var idsArr = [];
			//获取某个div块下的所有选中的复选框
			$.each($('#wglTreeDiv input:checkbox:checked'),function(){
				idsArr.push($(this).val());	
			});
			var ids = "";
			for(var i = 0; i < idsArr.length; i++){
				if(i == 0){
					ids = idsArr[i];
				}else{
					ids += "," + idsArr[i];
				}
			}
			if(ids == '' || ids == undefined){
				layer.msg('请确认船舶装备模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/linkModel/add",
				//processData: false,
				data : {
					"taskModelId" : taskModelId,
					"jcmIds" : ids
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						//$("#wglTreeDiv").html('');
						getYglJcm("-99","yglTreeDiv");
						getWglJcm("-99","wglTreeDiv");
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
		
		//获取已关联的树
		function getYglJcm(pid,fDiv){
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('请确认任务模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/linkModel/getYglJcm",
				data : {
					"pid" : pid,
					"taskModelId" : taskModelId
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					var list = data.jcms;
					showYglTree(retCode,list,fDiv)
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
		
		//显示未关联的树
		function showYglTree(retCode,list,fDiv){//参数分别是返回码,数组,文件夹id
			var fdiv = $("#"+fDiv);
		 	//fdiv.html('');
			if(retCode == '0000'){
				var ul ;
				if(fDiv == 'yglTreeDiv'){//首级
					$("#yglTreeDiv").html('');
					ul = $("<ul class=\"tree tree-lines\" data-ride=\"tree\"></ul>");
				}else{
					$("#"+fDiv).addClass("has-list open in");
					ul = $("<ul></ul>");
				}
				for(var i = 0; i < list.length; i++){
					if(list[i].type == 'P'){
						var li = $("<li id=\"ypDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"icon icon-folder-close-alt\" id=\"ypIcon"+list[i].id+"\" style=\"position: absolute;\"></i>");
						icon.attr("key",list[i].id);
						icon.on("click",function(){
							var id = $(this).attr("key");
							packageOpenAndCloseForJcm(id,'ypDiv'+id);
						});
						var cbDiv = $("<div class=\"checkbox-primary\" style=\"margin-left: 20px;\"></div>");
						var cbox = $("<input type=\"checkbox\" value=\""+list[i].id+"\" id=\"ycb"+list[i].id+"\" disabled=\"disabled\" id=\"primaryCheckbox"+list[i].id+"\">");
						var label = $("<label for=\"primaryCheckbox"+list[i].id+"\">"+list[i].name+"</label>");
						cbDiv.append(cbox);
						cbDiv.append(label);
						li.append(icon);
						li.append(cbDiv);
						ul.append(li);
					}else{
						var li = $("<li id=\"ypDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"layui-icon layui-icon-file-b\" style=\"position: absolute;\"></i>");
						var cbDiv = $("<div class=\"checkbox-primary\" style=\"margin-left: 20px;\"></div>");
						var cbox = $("<input type=\"checkbox\" value=\""+list[i].id+"\" id=\"ycb"+list[i].id+"\" id=\"primaryCheckbox"+list[i].id+"\">");
						cbox.attr("key",list[i].id);
						cbox.on("click",function(){
							var id = $(this).attr("key");
							ycbChecked(id);
						});
						var label = $("<label for=\"primaryCheckbox"+list[i].id+"\">"+list[i].name+"</label>");
						cbDiv.append(cbox);
						cbDiv.append(label);
						li.append(icon);
						li.append(cbDiv);
						ul.append(li);
					}
				}
				fdiv.append(ul);
			}else{
				if(fDiv == 'yglTreeDiv'){//首级
					fdiv.html('');
				}
				var message = '该目录下没有未关联模型！';
				fdiv.append("<h6>"+message+"</h6>");
			}
		}
		
		//已关联复选框选中事件
		function ycbChecked(id){
			var isChecked = $("#ycb"+id).prop('checked');//判断当前复选框是否选中
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('请确认任务模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			if(isChecked){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/linkModel/getYglJcmIds",
					data : {
						"id" : id,
						"taskModelId" : taskModelId
					},
					dataType : "json",
					success : function(data) {
						var list = data.ids;
						var pid = data.pid;
						ychangeCheckbox(list,'Y',pid)
					},
					error : function(data) {
						layer.msg("操作失败", {
							icon : 2,
							time : 2000,
						});
						return;
					}
				});
			}else{
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/linkModel/getJcmPid",
					data : {
						"id" : id
					},
					dataType : "json",
					success : function(data) {
						var list = data.pids;
						ychangeCheckbox(list,'N')
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
		}
		
		//已关联复选框变化
		function ychangeCheckbox(list,flag,pid){
			//debugger;
			if('Y' == flag){
				if(pid != '-99' && pid != ''){
					for(var i = 0; i < list.length; i++){
						var isChecked = $("#ycb"+list[i]).prop('checked');//判断当前复选框是否选中
						if(!isChecked){
							return;
						}
					}
					$("#ycb"+pid).prop("checked",true);
					ycbChecked(pid);
				}
			}else if('N' == flag){
				for(var i = 0; i < list.length; i++){
					$("#ycb"+list[i]).prop("checked",false);
				}
			}
		}
		
		//移除
		function remove(){
			var taskModelId = $("#taskModelId").val();
			if(taskModelId == '' || taskModelId == undefined){
				layer.msg('请确认任务模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			
			var idsArr = [];
			//获取某个div块下的所有选中的复选框
			$.each($('#yglTreeDiv input:checkbox:checked'),function(){
				idsArr.push($(this).val());	
			});
			var ids = "";
			for(var i = 0; i < idsArr.length; i++){
				if(i == 0){
					ids = idsArr[i];
				}else{
					ids += "," + idsArr[i];
				}
			}
			if(ids == '' || ids == undefined){
				layer.msg('请确认船舶装备模型',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/linkModel/remove",
				data : {
					"taskModelId" : taskModelId,
					"jcmIds" : ids
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						//$("#wglTreeDiv").html('');
						getYglJcm("-99","yglTreeDiv");
						getWglJcm("-99","wglTreeDiv");
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