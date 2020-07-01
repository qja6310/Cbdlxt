package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.TaskModel;
import cn.com.newloading.utils.StringUtil;

public class TaskModelProvider {

private static final String T_TASKMODEL = "t_taskmodel";
	
	public String queryInfoByParams(TaskModel taskModel) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(T_TASKMODEL);
		if(StringUtil.isNotBlank(taskModel.getId())) {
			sql.WHERE("id = #{id}");
		}
		if(StringUtil.isNotBlank(taskModel.getPid())) {
			sql.WHERE("pid = #{pid}");
		}
		if(StringUtil.isNotBlank(taskModel.getProjectId())) {
			sql.WHERE("projectId = #{projectId}");
		}
		sql.ORDER_BY("type desc");
		return sql.toString();
	}
	
	public String edit(TaskModel taskModel) {
		SQL sql = new SQL();
		sql.UPDATE(T_TASKMODEL);
		if(StringUtil.isNotBlank(taskModel.getName())) {
			sql.SET("name = #{name}");
		}
		if(StringUtil.isNotBlank(taskModel.getWeight())) {
			sql.SET("weight = #{weight}");
		}
		if(StringUtil.isNotBlank(taskModel.getStartTime())) {
			sql.SET("startTime = #{startTime}");
		}
		if(StringUtil.isNotBlank(taskModel.getEndTime())) {
			sql.SET("endTime = #{endTime}");
		}
		if(StringUtil.isNotBlank(taskModel.getLongestTime())) {
			sql.SET("longestTime = #{longestTime}");
		}
		if(StringUtil.isNotBlank(taskModel.getScope())) {
			sql.SET("scope = #{scope}");
		}
		sql.WHERE("id = #{id}");
		return sql.toString();
	}
	
	
}
