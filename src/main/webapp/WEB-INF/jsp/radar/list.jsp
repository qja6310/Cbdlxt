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
	<input type="hidden" id="jcmId">
	<div>
		<div class="layui-card model-div" style="width: 300px;">
			<div class="layui-card-header" style="text-align: center;font-size: 25px;font-weight: 600;background-color: #5FB878;">
				任务模型
			</div>
			<div class="layui-card-body" style="overflow: auto" id="treeDiv"></div>
		</div>
		<div style="position: absolute; left: 325px;width: 1260;height: 850px;">
			<div class="layui-card model-div" style="width: 300px;">
				<div class="layui-card-header" style="text-align: center;text-align: center;font-size: 25px;font-weight: 600;background-color: #5FB878;">
					船舶装备模型
				</div>
				<div class="layui-card-body" style="overflow: auto" id="yglTreeDiv"></div>
			</div>
			<div style="position: absolute; left: 310px;height: 850px;width: 1050;">
				<div style="border: 1px solid #5FB878;">
					<div class="layui-card-header" style="font-size: 20px;font-weight: 600;background-color: #5FB878;">
						雷达图
					</div>
					<div class="layui-card-body" id="radarDiv">
						
					</div>
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
						var label = $("<label style=\"margin-left: 5px;width: 50px;\">"+list[i].name+"</label>");
						label.attr("key",list[i].id);
						label.attr("weight",list[i].weight);
						label.on("click",function(){
							var id = $(this).attr("key");
							$("#taskModelId").val(id);
							$("#treeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							getYglJcm("-99","yglTreeDiv");
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
		
		//文件夹的打开与关闭
		function packageOpenAndCloseForJcm(id,fDiv){
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
		
		//显示已关联的树
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
						var icon = $("<i class=\"icon icon-folder-close-alt\" id=\"ypIcon"+list[i].id+"\"></i>");
						icon.attr("key",list[i].id);
						icon.on("click",function(){
							var id = $(this).attr("key");
							packageOpenAndCloseForJcm(id,'ypDiv'+id);
						});
						var label = $("<label style=\"margin-left: 5px;\">"+list[i].name+"</label>");
						li.append(icon);
						li.append(label);
						ul.append(li);
					}else{
						var li = $("<li id=\"ypDiv"+list[i].id+"\"></li>");
						var icon = $("<i class=\"layui-icon layui-icon-file-b\"></i>");
						var label = $("<label style=\"margin-left: 5px;\">"+list[i].name+"</label>");
						label.attr("key",list[i].id);
						label.attr("name",list[i].name);
						label.on("click",function(){
							var id = $(this).attr("key");
							$("#jcmId").val(id);
							$("#yglTreeDiv label").each(function(i){
								$(this).css("background-color","#FFFFFF");
							});
							$(this).css("background-color","#5FB878");
							getJsf();
						});
						li.append(icon);
						li.append(label);
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
		
		//获取六性
		function getJsf(){
			var jcmId = $("#jcmId").val();
			if(jcmId == '' || jcmId == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$('#radarDiv').load('<%=request.getContextPath() %>/radar/showRadar?jcmId='+jcmId);  
		}

	</script>
</body>
</html>