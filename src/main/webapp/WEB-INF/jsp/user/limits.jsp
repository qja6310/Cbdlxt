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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/zui/css/zui.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/newloading/css/newloading.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/jquery-3.4.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/layui/layui.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/zui/js/zui.min.js"></script>
</head>
<body>
	<input type="hidden" id="userId" value="${userId }"/>
	<div style="margin: 50px 50px 100px 50px;" class="mdiv">
		<ul class="tree tree-lines" data-ride="tree">
			<c:forEach items="${menus}" var="item">
				<li <c:if test="${item.isOwn == '1'}">class="has-list open in"</c:if>>
					<div class="checkbox-primary"><input type="checkbox" id="menu${item.id }" value="${item.id }" <c:if test="${item.isOwn == '1'}">checked="checked"</c:if> onclick="checked1('${item.id}')"><label style="font-weight: 600;color: #2F4056;">${item.menuName }</label></div>
					<c:if test="${fn:length(item.menuList)>0 }">
						<c:forEach items="${item.menuList}" var="item1">
							<ul>
								<li>
									<div class="checkbox-primary"><input type="checkbox" id="menu${item1.id }" value="${item1.id }" <c:if test="${item1.isOwn == '1'}">checked="checked"</c:if> onclick="checked2('${item1.id}','${item1.pid}')"><label style="font-weight: 400;color: #2F4056;">${item1.menuName }</label></div>
								</li>
							</ul>
						</c:forEach>
					</c:if>
			</c:forEach>
		</ul>
	</div>
	<div style="text-align: center;position: fixed;top: 400px;left: 65px;">
		<button type="button" class="layui-btn" onclick="doAdd()"><i class="layui-icon layui-icon-ok-circle"></i> 确认分配</button>
	</div>
	<script type="text/javascript">
		layui.use('form', function() {
			var form = layui.form;
			layui.form.render('select', 'form');
			/* form.on('select(fMenu)', function(data){
				  getFMenu();
			}); */
		});
	</script>
	<script>
		//点击一级菜单复选框事件
		function checked1(id){
			var isChecked = $("#menu"+id).prop('checked');//判断当前复选框是否选中
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/menu/getMenu",
				data : {
					'id' : id
				},
				dataType : "json",
				success : function(data) {
					var menuList = data.menuList;
					for(var i = 0; i < menuList.length; i++){
						if(isChecked){
							$("#menu"+menuList[i].id).prop("checked",true);
						}else{
							$("#menu"+menuList[i].id).prop("checked",false);
						}
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
		
		//点击二级菜单复选框事件
		function checked2(zid,pid){
			var isChecked = $("#menu"+zid).prop('checked');//判断当前复选框是否选中
			if(isChecked){//如果是选中状态，父级需选中
				$("#menu"+pid).prop("checked",true);
			}else{
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath() %>/menu/getMenu",
					data : {
						'id' : pid
					},
					dataType : "json",
					success : function(data) {
						var menuList = data.menuList;
						isChecked = false;
						for(var i = 0; i < menuList.length; i++){
							isChecked = $("#menu"+menuList[i].id).prop('checked');
							if(isChecked){
								$("#menu"+pid).prop("checked",true);
								return;
							}
						}
						$("#menu"+pid).prop("checked",false);
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
		
		//确认分配
		function doAdd(){
			var userId = $("#userId").val().trim();
			if(userId == '' || userId == undefined){
				layer.msg('缺失关键字',{
					icon: 2,
					time: 2000
				});
				return;
			}
			
			var idsArr = [];
			//获取某个div块下的所有选中的复选框
			$.each($('.mdiv input:checkbox:checked'),function(){
				idsArr.push($(this).val());	
			})
			
			
			var ids = "";
			for(var i = 0; i < idsArr.length; i++){
				if(i == 0){
					ids = idsArr[i];
				}else{
					ids += "," + idsArr[i];
				}
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/user/configLimits",
				data : {
					'userId' : userId,
					'ids' : ids
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
