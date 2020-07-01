package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.utils.StringUtil;

public class JsfProvider {

	private static final String T_JSF = "t_jsf";
	private static final String T_DICT = "t_dict";
	
	public String queryInfoByParams(Jsf jsf) {
		SQL sql = new SQL();
		sql.SELECT("jsf.*,dict.name as funName").FROM(T_JSF + " jsf inner join " + T_DICT + " dict on jsf.funCode = dict.code");
		sql.WHERE("dict.type = 'FUN'");
		if(StringUtil.isNotBlank(jsf.getJcmId())) {
			sql.WHERE("jsf.jcmId = #{jcmId}");
		}
		if(StringUtil.isNotBlank(jsf.getFunCode())) {
			sql.WHERE("jsf.funCode = #{funCode}");
		}
		if(StringUtil.isNotBlank(jsf.getStatus())) {
			sql.WHERE("jsf.status = #{status}");
		}
		return sql.toString();
	}
	
}
