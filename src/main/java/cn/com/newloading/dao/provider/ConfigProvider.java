package cn.com.newloading.dao.provider;


import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Config;
import cn.com.newloading.utils.StringUtil;

public class ConfigProvider extends BaseProvider{
	
	public String queryInfoByparam(Config config){
		SQL sql = new SQL();
		sql.SELECT("*").FROM(C);
		if(StringUtil.isNotBlank(config.getType())) {
			sql.WHERE("type = #{type}");
		}
		sql.ORDER_BY("code");
		return sql.toString();
	}
	
}
