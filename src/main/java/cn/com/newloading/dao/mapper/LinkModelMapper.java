package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.LinkModel;

@Mapper
public interface LinkModelMapper {
	
	@Select("select pid from t_jcm where id = #{id}")
	String getJcmPidByJcmId(@Param("id")String id);
	
	@Select("select id from t_jcm where pid = #{pid}")
	List<String> getJcmIdByPid(@Param("pid")String pid);
	
	@Insert("insert into t_linkmodel(taskModelId,jcmId,projectId,createTime,userId)" + 
			"values (#{taskModelId},#{jcmId},#{projectId},#{createTime},#{userId})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	Integer add(LinkModel linkModel);
	
	@Select("select count(*) from t_linkmodel where taskModelId = #{taskModelId} and jcmId = #{jcmId}")
	Integer check(@Param("taskModelId")String taskModelId,@Param("jcmId")String jcmId);
	
	@Select("select jcmId from t_linkmodel where taskModelId = #{taskModelId}")
	List<String> getJcmIdsByTaskModelId(@Param("taskModelId")String taskModelId);
	
	/**
	 * 获取以关联的jcm
	 * @param taskModelId
	 * @param jcmPid
	 * @param projectId
	 * @return
	 */
	@Select("select jcm.* from t_jcm jcm inner join t_linkmodel lm on jcm.id = lm.jcmId where lm.taskModelId = #{taskModelId} and jcm.pid = #{jcmPid} and lm.projectId = #{projectId}")
	List<Jcm> getYglJcmByParams(@Param("taskModelId")String taskModelId,@Param("jcmPid")String jcmPid,@Param("projectId")String projectId);
	
	/**
	 * 获取已关联的jcmId
	 * @param taskModelId
	 * @param jcmPid
	 * @param projectId
	 * @return
	 */
	@Select("select lm.jcmId from t_linkmodel lm inner join t_jcm jcm on lm.jcmId = jcm.id where lm.taskModelId = #{taskModelId} and jcm.pid = #{jcmPid} and lm.projectId = #{projectId}")
	List<String> getYglJcmIds(@Param("taskModelId")String taskModelId,@Param("jcmPid")String jcmPid,@Param("projectId")String projectId);
	
	@Delete("delete from t_linkmodel where taskModelId = #{taskModelId} and jcmId = #{jcmId} and projectId = #{projectId}")
	Integer delete(LinkModel linkModel);
	
	@Select("select id from t_linkmodel where taskModelId = #{taskModelId} and jcmId = #{jcmId} and projectId = #{projectId}")
	String getLinkModelId(@Param("taskModelId")String taskModelId,@Param("jcmId")String jcmId,@Param("projectId")String projectId);
}
