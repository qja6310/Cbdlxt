package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Jqf;
import cn.com.newloading.utils.StringUtil;

public class JqfProvider {

	private static final String T_JQF = "t_jqf";
	private static final String T_DICT = "t_dict";
	
	public String queryInfoByParams(Jqf jqf) {
		SQL sql = new SQL();
		sql.SELECT("jqf.*,dict.name as funName").FROM(T_JQF + " jqf inner join " + T_DICT + " dict on jqf.qfunCode = dict.code");
		sql.WHERE("dict.type = 'QFUN'");
		if(StringUtil.isNotBlank(jqf.getJcmId())) {
			sql.WHERE("jqf.jcmId = #{jcmId}");
		}
		if(StringUtil.isNotBlank(jqf.getQfunCode())) {
			sql.WHERE("jqf.qfunCode = #{qfunCode}");
		}
		if(StringUtil.isNotBlank(jqf.getStatus())) {
			sql.WHERE("jqf.status = #{status}");
		}
		if(StringUtil.isNotBlank(jqf.getPid())) {
			sql.WHERE("dict.pid = #{pid}");
		}
		return sql.toString();
	}
	
}
