<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>船舶动力系统综合质量评估系统</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/layui/css/layui.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/zui/css/zui.min.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/zui/js/zui.min.js"></script>
<style>
	.menu{
		cursor:pointer;
		text-decoration:none;
	}
	.warn{
		color: rgb(255,87,34);
		margin-top: 20px;
	}
</style>
</head>
<body style="background-color: #eeeeee;">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo" style="font-size: 20px;font-weight: 700;margin-left: 50px;">船舶装备综合质量评估</div>
			<!-- <div style="color: wheat;margin-left: 250px;font-size: 15px;padding-top: 20px;" id="rightTitle"></div> -->
			<div style="color: wheat;margin-left: 600px;font-size: 15px;padding-top: 20px;display: none;" id="dhDiv">
				当前[质量评估]代号为：<label id="dh"></label>
				<i class="layui-icon layui-icon-close-fill" style="color: #FF5722;" onclick="closeDH()"></i> 
			</div>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item">
					<c:choose>
						<c:when test="${user.id != null && user.id != ''}"><a>${user.name }</a></c:when>
						<c:otherwise><a href="<%=request.getContextPath() %>/index">请登录</a></c:otherwise>
					</c:choose>
				</li>
				<li class="layui-nav-item">
					<c:choose>
						<c:when test="${user.id != null && user.id != ''}"><a><i class="layui-icon layui-icon-logout" onclick="logout()"></i></a></c:when>
						<c:otherwise><a href="../index"><i class="layui-icon layui-icon-username"></i></a></c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
        
		<div class="layui-side layui-bg-black" id="menuDiv">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<c:forEach items="${myMenus }" var="myMenu">
						<li class="layui-nav-item" <c:if test="${myMenu.path != '*' }">onclick="showContent('${myMenu.path}','*','${myMenu.menuName }')"</c:if>><a>${myMenu.menuName }</a>
							<c:if test="${fn:length(myMenu.menuList)>0 }">
								<dl class="layui-nav-child">
									<c:forEach items="${myMenu.menuList }" var="myZMenu">
										<dd><a class="menu" onclick="showContent('${myZMenu.path}','${myMenu.menuName }','${myZMenu.menuName }')">${myZMenu.menuName }</a></dd>
									</c:forEach>
								</dl>
							</c:if>							
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;" id="content">
				
			</div>
		</div>
	</div>
	
	<!-- 初始化弹框 -->
	<script type="text/javascript">
		layui.use('layer', function(){ //独立版的layer无需执行这一句
			layer = layui.layer; //独立版的layer无需执行这一句
		});
		layui.use('carousel', function() {
			var carousel = layui.carousel;
			//建造实例
			carousel.render({
				elem : '#bgi',
				width : '100%' //设置容器宽度
				,
				height : '100%',
				arrow : 'always' //始终显示箭头
			//,anim: 'updown' //切换动画方式
			});
		});
		layui.use('element', function() {
			var element = layui.element;

		});
	</script>
	<script type="text/javascript">
		$(document).ready(function () {
			
	    });
		function showContent(path,title,ztitle){
			/* if(title == '*'){
				$('#rightTitle').text(ztitle);
			}else{
				$('#rightTitle').text(title + ' > ' + ztitle);
			} */
			
			$('#content').load('<%=request.getContextPath() %>'+path);  
		}
		function logout(){
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm('确定注销吗？', {
					icon : 3,
					title : '提示'
				}, function(index) {
					layer.close(index);
					window.location.href="<%=request.getContextPath() %>/logout";
				});
			});
		}
		function closeDH(){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/project/closeDH",
				data : {
				},
				dataType : "json",
				success : function(data) {
					$("#dhDiv").css("display","none");
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
