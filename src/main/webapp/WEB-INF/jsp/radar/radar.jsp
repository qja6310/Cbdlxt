<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/echarts/js/echarts.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/newloading/css/newloading.css">
</head>
<body>
	<div id="radar" style="width: 600px; height: 400px; margin-left: 25%"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('radar'));

		// 指定图表的配置项和数据
		option = {
			title: {
				text: '六性雷达图'
			},
			tooltip: {},
			legend: {
				//data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
			},
			radar: {
				// shape: 'circle',
				name: {
					textStyle: {
						color: '#fff',
						backgroundColor: '#999',
						borderRadius: 3,
						padding: [3, 5]
					}
				},
				indicator: [
					<c:forEach items="${jsfList}" var="item" varStatus="status">
						{name: '${item.funName}'},
					</c:forEach>
					
				]
			},
			series: [{
				type: 'radar',
				data: [{
					value: [${value}],
				}, ]
			}]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	</script>
</body>
</html>