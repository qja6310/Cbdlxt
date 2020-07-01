<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>船舶动力系统</title>
<link rel="stylesheet" href="../assets/layui/css/layui.css">
<script type="text/javascript" src="../assets/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="../assets/layui/layui.js"></script>
</head>
<body>
	<div class="layui-carousel" id="bgi">
	  <div carousel-item>
	    <div><img src="../assets/img/bgi1.jpg" style="width:100%;height: 100%;"/></div>
	    <!-- <div><img src="../assets/img/bgi2.jpg" style="width:100%;height: 100%;" /></div> -->
	    <div><img src="../assets/img/bgi3.jpg" style="width:100%;height: 100%;" /></div>
	  </div>
	</div>
	<div class="layui-card" style="width: 600px;height: 300px;top: 150px;left: 35%;position: absolute;background-color: rgba(255,255,255,0.3)">
	  <div class="layui-card-header" style="text-align: center;"><h1>船舶动力系统综合质量评估系统</h1></div>
	  <div class="layui-card-body">
	    <form class="layui-form" action="">
			<div class="layui-form-item" style="margin: 10px 20px 5px 20px;">
				<label class="layui-form-label" style="font-weight: 800;">工号:</label>
				<div class="layui-input-inline">
					<input type="text" id="number" value="GH9527" placeholder="请输入工号" class="layui-input" style="background-color: rgba(255,255,255,0.3);width: 355px;">
				</div>
			</div>
			<div class="layui-form-item" style="margin: 10px 20px 5px 20px;">
				<label class="layui-form-label" style="font-weight: 800;">密码:</label>
				<div class="layui-input-inline">
					<input type="password" id="password" value="123456" placeholder="请输入密码" class="layui-input" style="background-color: rgba(255,255,255,0.3);width: 355px;">
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;margin-top: 50px;">
				<button type="button" class="layui-btn layui-btn-fluid" onclick="doLogin()">登陆</button>
			</div>
		</form>
	  </div>
	</div>
	<!-- 初始化弹框 -->
	<script type="text/javascript">
		layui.use('layer', function() {
			var layer = layui.layer;
		});
		layui.use('carousel', function(){
			  var carousel = layui.carousel;
			  //建造实例
			  carousel.render({
			    elem: '#bgi'
			    ,width: '100%' //设置容器宽度
			    ,height: '100%'
			    ,arrow: 'always' //始终显示箭头
			    //,anim: 'updown' //切换动画方式
			  });
			});
	</script>
	<script type="text/javascript">
		/* 普通用户登录 */
		function doLogin() {
			var number = $("#number").val().trim();
			if (number == '' || number == undefined) {
				layer.msg("工号不为空", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			var password = $("#password").val().trim();
			if (password == '' || password == undefined) {
				layer.msg("密码不可为空", {
					icon : 2,
					time : 2000,
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath() %>/user/doLogin",
				data : {
					"number" : number,
					"password" : password
				},
				dataType : "json",
				success : function(data) {
					var retCode = data.retCode;
					if (retCode == 1) {
						layer.msg(data.retMsg, {
							icon : 1,
							time : 2000,
						});
						window.location.href='<%=request.getContextPath() %>/main';
					} else {
						layer.msg(data.retMsg, {
							icon : 2,
							time : 2000,
						});
					}
				},
				error : function(data) {
					layer.msg("登陆失败", {
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
