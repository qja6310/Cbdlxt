package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.newloading.bean.Params;

@Mapper
public interface ParamsMapper {

	@Insert("insert into t_params (targetName,weight,scoreWay,targetValue,tolerance,linkModelId) "
			+ "value (#{targetName},#{weight},#{scoreWay},#{targetValue},#{tolerance},#{linkModelId})")
	Integer add(Params params);
	
	@Select("select * from t_params where linkModelId = #{linkModelId}")
	List<Params> queryInfoByLinkModelId(@Param("linkModelId")String linkModelId);
	
	@Update("update t_params set targetName = #{targetName},weight = #{weight},scoreWay = #{scoreWay},"
			+ "targetValue = #{targetValue},tolerance = #{tolerance} where id=#{id}")
	Integer edit(Params params);
	
	@Delete("delete from t_params where id = #{id}")
	Integer delete(@Param("id")String id);
	
	@Update("update t_params set score = #{score} where id=#{id}")
	Integer editScore(@Param("score")String score,@Param("id")String id);
	
}
