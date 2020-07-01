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
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/echarts/js/echarts.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/newloading/css/newloading.css">
</head>
<body>
	<div id="main" style="width: 600px; height: 400px; margin-left: 25%"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));

		// 指定图表的配置项和数据
		option = {
			title : {
				text : '质量与性能数据折线图'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '性能' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ ${names} ]
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				name : '性能数据',
				type : 'line',
				stack : '总量',
				data : [ ${values} ]
			} ]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	</script>
</body>
</html>