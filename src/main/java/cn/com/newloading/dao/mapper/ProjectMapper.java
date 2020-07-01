package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.com.newloading.bean.Project;
import cn.com.newloading.bean.User;
import cn.com.newloading.dao.provider.ProjectProvider;
import cn.com.newloading.dao.provider.UserProvider;

@Mapper
public interface ProjectMapper {

	/**
	 * 工程新增
	 * @param project
	 * @return
	 */
	@Insert("insert into t_project(pgNum,pgName,jczbName,jczbNum,pgDesc,createTime,userId) values (#{pgNum},#{pgName},#{jczbName},#{jczbNum},#{pgDesc},#{createTime},#{userId})")
	Integer add(Project project);
	
	/**
	 * 检查评估代号是否重复
	 * @param pgNum
	 * @return
	 */
	@Select("select count(*) from t_project where pgNum = #{pgNum}")
	Integer checkPgNum(@Param("pgNum")String pgNum);
	
	/**
	 * 查询工程信息
	 * @return
	 */
	@Select("select t1.*,t2.name as userName from t_project t1 left join t_user t2 on t1.userId = t2.id order by t1.createTime desc")
	List<Project> queryInfoByParams();
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Delete("delete from t_project where id = #{id}")
	Integer delete(@Param("id")String id);
	
	/**
	 * 编辑
	 * @param project
	 * @return
	 */
	@UpdateProvider(type = ProjectProvider.class, method = "edit")
	Integer edit(Project project);
	
	/**
	 * 查询工程信息
	 * @return
	 */
	@Select("select t1.* from t_project t1 where t1.id = #{id}")
	Project queryInfoById(@Param("id")String id);
	
}
