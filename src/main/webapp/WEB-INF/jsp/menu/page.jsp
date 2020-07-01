<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${fn:length(menus)>0 }">
		<c:forEach items="${menus}" var="item" varStatus="status">
			<tr>
				<td class="center">${status.index + 1 }</td>
				<td>${item.menuName }</td>
				<td>${item.path }</td>
				<td>${item.fMenuName }</td>
				<td>${item.createTime }</td>
				<td>${item.serialNumber }</td>
				<td>
					<c:if test="${item.status == 'E'}">在用</c:if>
					<c:if test="${item.status == 'F'}">禁用</c:if>
				</td>
				<td class="center">
					<button type="button" class="layui-btn layui-btn-sm" onclick="auditWPass('${item.id}')">
						<i class="layui-icon layui-icon-about"></i> 查看
					</button>
					<button type="button" class="layui-btn layui-btn-normal layui-btn-sm" onclick="edit('${item.id}')">
						<i class="layui-icon layui-icon-edit"></i> 编辑
					</button>
					<c:if test="${item.status == 'E'}">
						<button type="button" class="layui-btn layui-btn-warm layui-btn-sm" onclick="updateStatus('${item.id}','F')">
							<i class="layui-icon layui-icon-logout"></i> 禁用
						</button>
					</c:if>
					<c:if test="${item.status == 'F'}">
						<button type="button" class="layui-btn layui-btn-warm layui-btn-sm" onclick="updateStatus('${item.id}','E')">
							<i class="layui-icon layui-icon-ok-circle"></i> 启用
						</button>
					</c:if>
					<button type="button" class="layui-btn layui-btn-danger layui-btn-sm" onclick="updateStatus('${item.id}','D')">
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
