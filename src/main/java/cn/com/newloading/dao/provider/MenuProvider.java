package cn.com.newloading.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.com.newloading.bean.Menu;
import cn.com.newloading.utils.StringUtil;

public class MenuProvider {

	private final String T_MENU = "t_menu";
	private final String T_USER_MENU = "t_user_menu";
	
	public String queryMenuByParams(Menu menu) {
		SQL sql = new SQL();
		sql.SELECT("t1.*,t2.menuName AS fMenuName");
		sql.FROM(T_MENU + " t1 LEFT JOIN " + T_MENU + " t2 ON t1.pid = t2.id");
		if(StringUtil.isNotBlank(menu.getId())) {
			sql.WHERE("t1.id = #{id}");
		}
		if(StringUtil.isNotBlank(menu.getMenuName())) {
			menu.setMenuName("%" + menu.getMenuName() + "%");
			sql.WHERE("t1.menuName LIKE #{menuName}");
		}
		if(StringUtil.isNotBlank(menu.getStatus())) {
			sql.WHERE("t1.status = #{status}");
		}
		if(StringUtil.isNotBlank(menu.getPid())) {
			sql.WHERE("t1.pid = #{pid}");
		}
		sql.WHERE("t1.status != 'D'");
		String desc = "t1.serialNumber,t1.id";
		if(menu.getStart() != null && menu.getLimit() != null) {
			desc += " limit #{start},#{limit}";
		}
		sql.ORDER_BY(desc);
		return sql.toString();
	}
	
	public String queryMenuCountByParams(Menu menu) {
		SQL sql = new SQL();
		sql.SELECT("COUNT(*)");
		sql.FROM(T_MENU + " t1 LEFT JOIN " + T_MENU + " t2 ON t1.pid = t2.id");
		if(StringUtil.isNotBlank(menu.getMenuName())) {
			menu.setMenuName("%" + menu.getMenuName() + "%");
			sql.WHERE("t1.menuName LIKE #{menuName}");
		}
		if(StringUtil.isNotBlank(menu.getStatus())) {
			sql.WHERE("t1.status = #{status}");
		}
		if(StringUtil.isNotBlank(menu.getPid())) {
			sql.WHERE("t1.pid = #{pid}");
		}
		sql.WHERE("t1.status != 'D'");
		return sql.toString();
	}
	
	public String edit(Menu menu) {
		SQL sql = new SQL();
		sql.UPDATE(T_MENU);
		if(StringUtil.isNotBlank(menu.getStatus())) {
			sql.SET("status = #{status}");
		}
		if(StringUtil.isNotBlank(menu.getPid())) {
			sql.SET("pid = #{pid}");
		}
		if(StringUtil.isNotBlank(menu.getPath())) {
			sql.SET("path = #{path}");
		}
		if(StringUtil.isNotBlank(menu.getMenuName())) {
			sql.SET("menuName = #{menuName}");
		}
		if(menu.getSerialNumber() != null && menu.getSerialNumber() > 0) {
			sql.SET("serialNumber = #{serialNumber}");
		}
		sql.WHERE("id = #{id}");
		return sql.toString();
	}
	
}
