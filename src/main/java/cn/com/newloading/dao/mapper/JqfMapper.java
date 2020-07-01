package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import cn.com.newloading.bean.Jqf;
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.dao.provider.JqfProvider;

@Mapper
public interface JqfMapper {

	@Insert("insert into t_jqf (jcmId,qfunCode,status) values (#{jcmId},#{qfunCode},#{status})")
	Integer add(Jqf jqf);
	
	@SelectProvider(type = JqfProvider.class,method = "queryInfoByParams")
	List<Jqf> queryInfoByParams(Jqf jqf);
	
	@Update("update t_jqf set status = #{status} where jcmId = #{jcmId} and qfunCode = #{funCode}")
	Integer changeStatus(@Param("jcmId")String jcmId,@Param("funCode")String funCode,@Param("status")String status);
	
	@Update("update t_jqf set calResult = #{calResult} where jcmId = #{jcmId} and qfunCode = #{qfunCode}")
	Integer edit(Jqf jqf);
}
