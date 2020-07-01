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
			<div class="layui-col-md3">
				<div class="layui-form-item">
					<label class="layui-form-label lw">菜单</label>
					<div class="layui-input-inline">
						<input type="text" id="menuName" name="menuName" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="layui-form-item">
					<label class="layui-form-label lw">父级菜单</label>
					<div class="layui-input-inline">
						<select id="pid" name="pid" lay-verify="required">
							<option value="">请选择</option>
							<option value="-99">一级菜单</option>
							<c:forEach items="${menus}" var="menu">
								<option value="${menu.id }">${menu.menuName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label lw">状态</label>
					<div class="layui-input-inline">
						<select id="status" name="status" lay-verify="required">
							<option value=""></option>
							<option value="E">在用</option>
							<option value="F">禁用</option>
						</select>
					</div>
					<div class="layui-input-inline" style="width: 90px;">
						<button type="button" class="layui-btn" onclick="dataCount(1)">
							<i class="layui-icon layui-icon-search"></i> 搜索
						</button>
					</div>
					<div class="layui-input-inline">
						<button type="button" class="layui-btn layui-btn-normal" onclick="add()">
							<i class="layui-icon layui-icon-add-1"></i> 新增
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
					<th style="text-align: center;">菜单</th>
					<th style="text-align: center;">路径</th>
					<th style="text-align: center;">父级菜单</th>
					<th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">排序号码</th>
					<th style="text-align: center;">状态</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody id="pageDiv">

			</tbody>
		</table>
	</div>
	<div id="paging"></div>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			layui.form.render('select', 'form');
		});
		// 定义每页的记录数
		var limit = 10;
		var currPage = 1;
		var laypage;

		/*页面初始化*/
		$(document).ready(function() {
			dataCount(1);
		});

		function page() {
			var menuName = $("#menuName").val();
			var status = $("#status").val();
			var pid = $("#pid").val();
			$('#pageDiv').load('<%=request.getContextPath() %>/menu/page?menuName='+menuName
					+'&status='+status
					+'&pid='+pid
					+'&currPage='+currPage
					+'&limit='+limit);
		}

		//查询数量的总数
		function dataCount(index) {
			if (index == 1) {
				currPage = 1;
			}
			var menuName = $("#menuName").val();
			var status = $("#status").val();
			var pid = $("#pid").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/menu/dataCount",
				data : {
					'menuName' : menuName,
					'status' : status,
					'pid' : pid
				},
				dataType : "json",
				success : function(data) {
					var total = data.count;
					layui.use([ 'laypage', 'layer' ], function() {
						laypage = layui.laypage, layer = layui.layer;
						//总页数大于页码总数
						laypage.render({
							elem : 'paging',
							count : total, //数据总数,
							limit : limit,
							curr : currPage,
							jump : function(obj, first) {
								currPage = obj.curr; //得到当前页，以便向服务端请求对应页的数据。
								limit = obj.limit; //得到每页显示的条数
								page();
							}
						});
					});
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
		
		//弹出一个页面层
		function add(){
	    	layer.open({
	        	type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	        	title: ['新增菜单','font-size: 15px;font-weight: 500;'],
	        	area: ['650px', '450px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/menu/toAdd',
	        	closeBtn:2,
	        	end: function(){
	        		dataCount(-99);
	        	}
	       });
	    }
		
		//弹出一个页面层
		function edit(id){
	    	layer.open({
	        	type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	        	title: ['编辑菜单','font-size: 15px;font-weight: 500;'],
	        	area: ['650px', '450px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/menu/toEdit?id='+id,
	        	closeBtn:2,
	        	end: function(){
	        		dataCount(-99);
	        	}
	       });
	    }
		
		//修改状态
		function updateStatus(id,status){
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			if(status == '' || status == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var message = "";
			if(status == "D"){
				message = "是否确认删除？三思啊!!!";
			}else if(status == "E"){
				message = "是否确认启用？";
			}else if(status == "F"){
				message = "是否确认禁用";
			}
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm(message, {
					icon : 3,
					title : '提示'
				}, function(index) {
					layer.close(index);
					$.ajax({
						type : "post",
						url : "<%=request.getContextPath() %>/menu/updateStatus",
						data : {
							'status' : status,
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
							if(status == 'D'){
								dataCount(1);
							}else{
								dataCount(-99);
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
</body>
</html>
