package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Project;
import cn.com.newloading.utils.StringUtil;

public class ProjectProvider {

	private static final String T_PROJECT = "t_project";
	
	public String edit(Project project) {
		SQL sql = new SQL();
		sql.UPDATE(T_PROJECT);
		if(StringUtil.isNotBlank(project.getPgNum())) {
			sql.SET("pgNum = #{pgNum}");
		}
		if(StringUtil.isNotBlank(project.getPgName())) {
			sql.SET("pgName = #{pgName}");
		}
		if(StringUtil.isNotBlank(project.getJczbName())) {
			sql.SET("jczbName = #{jczbName}");
		}
		if(StringUtil.isNotBlank(project.getJczbNum())) {
			sql.SET("jczbNum = #{jczbNum}");
		}
		if(StringUtil.isNotBlank(project.getPgDesc())) {
			sql.SET("pgDesc = #{pgDesc}");
		}
		sql.WHERE("id = #{id}");
		return sql.toString();
	}
}
