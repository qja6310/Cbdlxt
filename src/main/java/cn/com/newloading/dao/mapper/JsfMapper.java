package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.dao.provider.JsfProvider;

@Mapper
public interface JsfMapper {

	@Insert("insert into t_jsf (jcmId,funCode,status) values (#{jcmId},#{funCode},#{status})")
	Integer add(Jsf jsf);
	
	@Delete("delete from t_jsf where jcmId = #{jcmId}")
	Integer deleteByJcmId(@Param("jcmId")String jcmId);
	
	@Update("update t_jsf set status = #{status} where jcmId = #{jcmId} and funCode = #{funCode}")
	Integer changeStatus(@Param("jcmId")String jcmId,@Param("funCode")String funCode,@Param("status")String status);
	
	@SelectProvider(type = JsfProvider.class,method = "queryInfoByParams")
	List<Jsf> queryInfoByParams(Jsf jsf);
	
	@Update("update t_jsf set calResult = #{calResult} where jcmId = #{jcmId} and funCode = #{funCode}")
	Integer edit(Jsf jsf);
	
}
