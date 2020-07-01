package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.User;
import cn.com.newloading.utils.StringUtil;

public class UserProvider {

	private static final String T_USER = "t_user";
	
	public String queryInfoByParams(User user) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(T_USER);
		if(StringUtil.isNotBlank(user.getId())) {
			sql.WHERE("id = #{id}");
		}
		if(StringUtil.isNotBlank(user.getName())) {
			user.setName("%" + user.getName() + "%");
			sql.WHERE("name LIKE #{name}");
		}
		if(StringUtil.isNotBlank(user.getNumber())) {
			user.setNumber("%" + user.getNumber() + "%");
			sql.WHERE("number LIKE #{number}");
		}
		String desc = "createTime DESC";
		if(user.getStart() != null && user.getLimit() != null) {
			desc += " limit #{start},#{limit}";
		}
		sql.ORDER_BY(desc);
		return sql.toString();
	}
	
	public String queryInfoCountByParams(User user) {
		SQL sql = new SQL();
		sql.SELECT("COUNT(*)");
		sql.FROM(T_USER);
		if(StringUtil.isNotBlank(user.getName())) {
			user.setName("%" + user.getName() + "%");
			sql.WHERE("name LIKE #{name}");
		}
		if(StringUtil.isNotBlank(user.getNumber())) {
			user.setNumber("%" + user.getNumber() + "%");
			sql.WHERE("number LIKE #{number}");
		}
		return sql.toString();
	}
	
	public String edit(User user) {
		SQL sql = new SQL();
		sql.UPDATE(T_USER);
		if(StringUtil.isNotBlank(user.getName())) {
			sql.SET("name = #{name}");
		}
		if(StringUtil.isNotBlank(user.getAge())) {
			sql.SET("age = #{age}");
		}
		if(StringUtil.isNotBlank(user.getSex())) {
			sql.SET("sex = #{sex}");
		}
		if(StringUtil.isNotBlank(user.getPassword())) {
			sql.SET("password = #{password}");
		}
		if(StringUtil.isNotBlank(user.getPhone())) {
			sql.SET("phone = #{phone}");
		}
		if(StringUtil.isNotBlank(user.getNumber())) {
			sql.SET("number = #{number}");
		}
		sql.WHERE("id = #{id}");
		return sql.toString();
	}
}
