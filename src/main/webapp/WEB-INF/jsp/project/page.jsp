<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${fn:length(projects)>0 }">
		<c:forEach items="${projects}" var="item" varStatus="status">
			<tr>
				<td class="center">${status.index + 1 }</td>
				<td>${item.pgNum }</td>
				<td>${item.pgName }</td>
				<td>${item.jczbName }</td>
				<td>${item.jczbNum }</td>
				<td>${item.userName }</td>
				<td>${item.createTime }</td>
				<td>${item.pgDesc }</td>
				<td class="center">
					<button type="button" class="layui-btn layui-btn-sm" onclick="openJcm('${item.id}','${item.pgNum }')">
						<i class="layui-icon layui-icon-file"></i> 打开
					</button>
					<button type="button" class="layui-btn layui-btn-normal layui-btn-sm" onclick="edit('${item.id}')">
						<i class="layui-icon layui-icon-edit"></i> 修改
					</button>
					<button type="button" class="layui-btn layui-btn-danger layui-btn-sm" onclick="del('${item.id}')">
						<i class="layui-icon layui-icon-delete"></i> 删除
					</button>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr><td colspan="8">查无此数据</td></tr>
	</c:otherwise>
</c:choose>
