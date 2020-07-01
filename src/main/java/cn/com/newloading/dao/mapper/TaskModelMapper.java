package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.com.newloading.bean.TaskModel;
import cn.com.newloading.dao.provider.TaskModelProvider;

@Mapper
public interface TaskModelMapper {

	@Insert("insert into t_taskmodel(pid,name,type,projectId,weight,startTime,endTime,longestTime,scope,createTime,userId) " + 
			"values (#{pid},#{name},#{type},#{projectId},#{weight},#{startTime},#{endTime},#{longestTime},#{scope},#{createTime},#{userId})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	Integer add(TaskModel taskModel);
	
	@SelectProvider(type = TaskModelProvider.class, method = "queryInfoByParams")
	List<TaskModel> queryInfoByParams(TaskModel taskModel);
	
	@UpdateProvider(type = TaskModelProvider.class, method = "edit")
	Integer edit(TaskModel taskModel);
	
	@Select("select count(*) from t_taskmodel where pid = #{pid} and type = #{type} and name = #{name} and projectId = #{projectId}")
	Integer checkName(@Param("pid")String pid,@Param("type")String type,@Param("projectId")String projectId, @Param("name")String name);
	
	@Delete("delete from t_taskmodel where id = #{id}")
	Integer deleteById(@Param("id")String id);
	
	@Delete("delete from t_taskmodel where pid = #{pid}")
	Integer deleteByPid(@Param("pid")String pid);
}
