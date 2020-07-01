package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.com.newloading.bean.User;
import cn.com.newloading.dao.provider.UserProvider;

@Mapper
public interface UserMapper {

	/**
	 * 登陆
	 * @param name
	 * @param password
	 * @return
	 */
	@Select("select * from t_user where number = #{number} AND password = #{password}")
	User doLogin(@Param("number")String number, @Param("password")String password);
	
	/**
	 * 工号查重
	 * @param number
	 * @return
	 */
	@Select("select count(*) from t_user where number = #{number}")
	Integer checkNumber(@Param("number")String number);
	
	/**
	 * 新增用户信息
	 * @param user
	 * @return
	 */
	@Insert("insert into t_user(name,age,sex,phone,password,number,status,createTime) values (#{name},#{age},#{sex},#{phone},#{password},#{number},#{status},#{createTime})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	Integer add(User user);
	
	/**
	 * 用户查询
	 * @param user
	 * @return
	 */
	@SelectProvider(type = UserProvider.class, method = "queryInfoByParams")
	List<User> queryInfoByParams(User user);
	
	/**
	 * 用户数量
	 * @param user
	 * @return
	 */
	@SelectProvider(type = UserProvider.class, method = "queryInfoCountByParams")
	Integer queryInfoCountByParams(User user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@UpdateProvider(type = UserProvider.class, method = "edit")
	Integer edit(User user);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@Delete("delete from t_user where id = #{id}")
	Integer delete(@Param("id")String id);
	
	/**
	 * 配置权限
	 * @param userId
	 * @param menuId
	 * @return
	 */
	@Insert("insert into t_user_menu(userId,menuId) values (#{userId},#{menuId})")
	Integer configLimits(@Param("userId")String userId,@Param("menuId")String menuId);
	
	/**
	 * 根据用户id删除权限
	 * @param userId
	 * @return
	 */
	@Delete("delete from t_user_menu where userId = #{userId}")
	Integer deleteLimits(@Param("userId")String userId);
	
}
