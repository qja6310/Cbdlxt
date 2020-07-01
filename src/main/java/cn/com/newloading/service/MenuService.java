package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Menu;

public interface MenuService {

	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	Map<String, String> add(Menu menu);
	
	/**
	 * 编辑菜单
	 * @param menu
	 * @return
	 */
	Map<String, String> edit(Menu menu);
	
	/**
	 * 菜单列表
	 * @param menu
	 * @return
	 */
	List<Menu> queryMenuByParams(Menu menu);
	
	/**
	 * 菜单数量
	 * @param menu
	 * @return
	 */
	Integer queryMenuCountByParams(Menu menu);
	
	/**
	 * 修改状态
	 * @param menu
	 * @return
	 */
	Map<String, String> updateStatus(Menu menu);
	
	
	/**
	 * 用户拥有的菜单列表
	 * @param menu
	 * @return
	 */
	List<Menu> queryMenuByUserId(String userId);
	
}
