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
					<label class="layui-form-label lw">姓名</label>
					<div class="layui-input-inline">
						<input type="text" id="userName" name="userName" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-col-md9">
				<div class="layui-form-item">
					<label class="layui-form-label lw">工号</label>
					<div class="layui-input-inline">
						<input type="text" id="number" name="number" class="layui-input">
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
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">年龄</th>
					<th style="text-align: center;">性别</th>
					<th style="text-align: center;">联系电话</th>
					<th style="text-align: center;">工号</th>
					<th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody id="userPageDiv">

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
			var userName = $("#userName").val().trim();
			var number = $("#number").val().trim();
			$('#userPageDiv').load('<%=request.getContextPath() %>/user/page?name='+userName
					+'&number='+number
					+'&currPage='+currPage
					+'&limit='+limit);
		}

		//查询数量的总数
		function dataCount(index) {
			if (index == 1) {
				currPage = 1;
			}
			var userName = $("#userName").val().trim();
			var number = $("#number").val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/user/dataCount",
				data : {
					'userName' : userName,
					'number' : number
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
	        	title: ['新增用户','font-size: 15px;font-weight: 500;'],
	        	area: ['650px', '500px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/user/toAdd',
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
	        	title: ['修改用户信息','font-size: 15px;font-weight: 500;'],
	        	area: ['650px', '500px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/user/toEdit?id='+id,
	        	closeBtn:2,
	        	end: function(){
	        		dataCount(-99);
	        	}
	       });
	    }
		
		function limits(id,name){
	    	layer.open({
	        	type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	        	title: ['权限分配['+name+']','font-size: 15px;font-weight: 500;'],
	        	area: ['300px', '500px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/user/limits?id='+id,
	        	closeBtn:2,
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
						url : "<%=request.getContextPath() %>/user/doDelete",
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
