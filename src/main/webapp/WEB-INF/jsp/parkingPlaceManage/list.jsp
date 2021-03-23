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
	<div class="layui-card" style="width: 1230px;position: absolute;padding-top: 20px;">
		<div class="layui-card-header">
			<form class="layui-form" lay-filter="form">
				<div class="layui-row">
					<div class="layui-col-md3">
						<div class="layui-form-item">
							<label class="layui-form-label lw">区域</label>
							<div class="layui-input-inline">
								<select class="form-control" id="area">
									<option value="">请选择</option>
									<c:forEach items="${areas }" var="item">
										<option value="${item }">${item }区</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="layui-col-md3">
						<div class="layui-form-item">
							<label class="layui-form-label lw">车位号</label>
							<div class="layui-input-inline">
								<input type="text" id="number" name="number" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-col-md6">
						<div class="layui-form-item">
							<label class="layui-form-label lw">状态</label>
							<div class="layui-input-inline">
								<select class="form-control" id="status">
									<option value="">请选择</option>
									<c:forEach items="${configList }" var="item">
										<option value="${item.code }">${item.param }</option>
									</c:forEach>
								</select>
							</div>
							<div class="layui-input-inline" style="width: 90px;">
								<button type="button" class="layui-btn" onclick="dataCount(1)">
									<i class="layui-icon layui-icon-search"></i> 搜索
								</button>
							</div>
							<div class="layui-input-inline">
								<button type="button" class="layui-btn layui-btn-normal" onclick="add()">
									<i class="layui-icon layui-icon-add-1"></i> 新增车位
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="layui-card-body">
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
							<th style="text-align: center;">区域</th>
							<th style="text-align: center;">车位号</th>
							<th style="text-align: center;">当前状态</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					<tbody id="pageDiv">
		
					</tbody>
				</table>
			</div>
			<div id="paging" style="text-align: right"></div>
  		</div>
	</div>
	<div class="layui-card" style="position: absolute;left: 1250px;">
		<div class="layui-card-header">
			<h3>图表统计</h3>
		</div>
		<div class="layui-card-body" id="CountDiv" style="width: 450px;height: 635px;"></div>
	</div>
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
			Count();
		});
		
		function Count(){
			$('#CountDiv').load('<%=request.getContextPath() %>/cwgl/count');
		}

		function page() {
			var area = $("#area").val().trim();
			var number = $("#number").val().trim();
			var status = $("#status").val().trim();
			$('#pageDiv').load('<%=request.getContextPath() %>/cwgl/page?area='+area
					+'&number='+number
					+'&status='+status
					+'&currPage='+currPage
					+'&limit='+limit);
		}

		//查询数量的总数
		function dataCount(index) {
			if (index == 1) {
				currPage = 1;
			}
			var area = $("#area").val().trim();
			var number = $("#number").val().trim();
			var status = $("#status").val().trim();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/cwgl/dataCount",
				data : {
					'area' : area,
					'number' : number,
					'status' : status,
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
	        	title: ['新增车位','font-size: 15px;font-weight: 500;'],
	        	area: ['500px', '350px'],
	        	shadeClose: true, //点击遮罩关闭
	        	content: '<%=request.getContextPath()%>/cwgl/toAdd',
	        	closeBtn:2,
	        	end: function(){
	        		dataCount(-99);
	        	}
	       });
	    }
		
		/* function inputCarNumber(id){
			var status = $("#pps"+id).val();
			if(status == '3'){
				$("#pcn"+id).show();
			}else{
				$("#pcn"+id).hide();
			}
		} */
		
		function edit(id,ystatus){
			if(id == '' || id == undefined){
				layer.msg('缺失关键参数',{
					icon: 2,
					time: 2000
				});
				return;
			}
			var status = $("#pps"+id).val();
			if(ystatus == '2' && (status == '3' || status == '4' || status == '5')){
				layer.msg('该车位已停有车辆，不可维修，停用',{
					icon: 2,
					time: 2000
				});
				return;
			}
			if(ystatus == '3' && (status == '4' || status == '5')){
				layer.msg('该车位已被预约，不可维修，停用',{
					icon: 2,
					time: 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/cwgl/editStatus",
				data : {
					'id' : id,
					'status' : status
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == "0000") {
						Count();
						dataCount(-99);
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
