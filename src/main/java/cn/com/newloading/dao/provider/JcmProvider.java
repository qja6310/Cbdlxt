package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.utils.StringUtil;

public class JcmProvider {

private static final String T_JCM = "t_jcm";
	
	public String queryInfoByParams(Jcm jcm) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(T_JCM);
		if(StringUtil.isNotBlank(jcm.getId())) {
			sql.WHERE("id = #{id}");
		}
		if(StringUtil.isNotBlank(jcm.getPid())) {
			sql.WHERE("pid = #{pid}");
		}
		if(StringUtil.isNotBlank(jcm.getProjectId())) {
			sql.WHERE("projectId = #{projectId}");
		}
		
		sql.ORDER_BY("type desc");
		return sql.toString();
	}
	
	public String edit(Jcm jcm) {
		SQL sql = new SQL();
		sql.UPDATE(T_JCM);
		if(StringUtil.isNotBlank(jcm.getName())) {
			sql.SET("name = #{name}");
		}
		if(StringUtil.isNotBlank(jcm.getModelNum())) {
			sql.SET("modelNum = #{modelNum}");
		}
		sql.WHERE("id = #{id}");
		return sql.toString();
	}
	
	
}
