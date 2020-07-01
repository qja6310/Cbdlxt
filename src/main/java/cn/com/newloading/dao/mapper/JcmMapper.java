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

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.dao.provider.JcmProvider;

@Mapper
public interface JcmMapper {

	@Insert("insert into t_jcm(pid,projectId,name,modelNum,type,createTime,userId) " + 
			"values (#{pid},#{projectId},#{name},#{modelNum},#{type},#{createTime},#{userId})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	Integer add(Jcm jcm);
	
	@SelectProvider(type = JcmProvider.class, method = "queryInfoByParams")
	List<Jcm> queryInfoByParams(Jcm jcm);
	
	@UpdateProvider(type = JcmProvider.class, method = "edit")
	Integer edit(Jcm jcm);
	
	@Select("select count(*) from t_jcm where pid = #{pid} and type = #{type} and projectId = #{projectId} and name = #{name}")
	Integer checkName(@Param("pid")String pid,@Param("type")String type,@Param("projectId")String projectId,@Param("name")String name);
	
	@Delete("delete from t_jcm where id = #{id}")
	Integer deleteById(@Param("id")String id);
	
	@Delete("delete from t_jcm where pid = #{pid}")
	Integer deleteByPid(@Param("pid")String pid);
}
