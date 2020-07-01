package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.com.newloading.bean.Menu;
import cn.com.newloading.dao.provider.MenuProvider;

@Mapper
public interface MenuMapper {

	/**
	 * 新增
	 * @param menu
	 * @return
	 */
	@Insert("insert into t_menu(menuName,pid,path,serialNumber,status,createTime) "
			+ "values (#{menuName},#{pid},#{path},#{serialNumber},#{status},#{createTime})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	Integer add(Menu menu);
	
	/**
	 * 获取当前最大的序号
	 * @return
	 */
	@Select("SELECT MAX(serialNumber) FROM t_menu WHERE status != 'D'")
	Integer getMaxSn();
	
	/**
	 * 菜单列表
	 * @param menu
	 * @return
	 */
	@SelectProvider(type = MenuProvider.class, method = "queryMenuByParams")
	List<Menu> queryMenuByParams(Menu menu);
	
	/**
	 * 菜单数量
	 * @param menu
	 * @return
	 */
	@SelectProvider(type = MenuProvider.class, method = "queryMenuCountByParams")
	Integer queryMenuCountByParams(Menu menu);
	
	/**
	 * 路径查重
	 * @param path
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM t_menu WHERE path = #{path}")
	Integer checkPath(String path);
	
	/**
	 * 编辑菜单
	 * @param menu
	 * @return
	 */
	@UpdateProvider(type = MenuProvider.class, method = "edit")
	Integer edit(Menu menu);
	
	/**
	 * 根据用户id获取菜单
	 * @param userId
	 * @return
	 */
	@Select("SELECT m.* FROM t_menu m INNER JOIN t_user_menu um ON m.id = um.menuId WHERE um.userId = #{userId} ")
	List<Menu> queryMenuByUserId(@Param("userId")String userId);
}
