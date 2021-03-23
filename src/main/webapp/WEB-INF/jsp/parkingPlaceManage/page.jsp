<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${fn:length(list)>0 }">
		<c:forEach items="${list}" var="item" varStatus="status">
			<tr>
				<td class="center">${status.index + 1 }</td>
				<td>${item.area }区</td>
				<td>${item.number }</td>
				<td>${item.statusName }</td>
				<td>
					<select class="form-control" id="pps${item.id}" onchange="edit('${item.id}','${item.status }')" 
					<c:if test="${item.status == 2 || item.status == 3}">disabled="disabled"</c:if>>
						<option value="">请选择</option>
						<c:forEach items="${configList}" var="config" varStatus="status">
							<c:if test="${config.code == 1 || config.code == 4 || config.code == 5}">
								<option value="${config.code }">${config.param }</option>
							</c:if>
						</c:forEach>
					</select>
					<%-- <button type="button" class="layui-btn layui-btn-warm layui-btn-sm" onclick="edit('${item.id}','${item.status }')">
						<i class="layui-icon layui-icon-edit"></i> 保存
					</button> --%>
					<%-- <input type="text" class="form-control" id="pcn${item.id}" style="display:none"> --%>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr><td colspan="5">查无此数据</td></tr>
	</c:otherwise>
</c:choose>
