package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.newloading.bean.User;

public interface UserService {

	/**
	 * 用户登陆
	 * @param name
	 * @param password
	 * @return
	 */
	User doLogin(String number,String password);
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	Map<String, String> add(User user);
	
	/**
	 * 数量
	 * @param user
	 * @return
	 */
	Integer queryInfoCountByParams(User user);
	
	/**
	 * 用户信息
	 * @param user
	 * @return
	 */
	List<User> queryInfoByParams(User user);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	Map<String, String> edit(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	Map<String, String> delete(User user);
	
	/**
	 * 用户配置权限
	 * @param userId
	 * @param ids
	 * @return
	 */
	Map<String, String> configLimits(String userId,String ids);
}
