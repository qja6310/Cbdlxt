<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${fn:length(users)>0 }">
		<c:forEach items="${users}" var="item" varStatus="status">
			<tr>
				<td class="center">${status.index + 1 }</td>
				<td>${item.name }</td>
				<td>${item.age }</td>
				<td>
					<c:if test="${item.sex == '1'}">男</c:if>
					<c:if test="${item.sex == '0'}">女</c:if>
				</td>
				<td>${item.phone }</td>
				<td>${item.number }</td>
				<td>${item.createTime }</td>
				<td class="center">
					<button type="button" class="layui-btn layui-btn-normal layui-btn-sm" onclick="edit('${item.id}')">
						<i class="layui-icon layui-icon-edit"></i> 编辑
					</button>
					<button type="button" class="layui-btn layui-btn-warm layui-btn-sm" onclick="limits('${item.id}','${item.name }')">
						<i class="layui-icon layui-icon-share"></i> 权限分配
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
