package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.newloading.bean.PerformanceIndex;

@Mapper
public interface PerformanceIndexMapper {
	
	@Insert("insert into t_performance_index (taskModelId,jcmId,sixFunction,pi1,pi2,pi3,pi4,pi5,pi6,pi7,pi8,pi9,pi10,pi11,pi12,pi13,pi14,"
			+ "pi15,pi16,pi17,pi18,pi19,pi20,pi21,pi22,pi23,pi24,pi25) "
			+ "values (#{taskModelId},#{jcmId},#{sixFunction},#{pi1},#{pi2},#{pi3},#{pi4},#{pi5},#{pi6},#{pi7},#{pi8},#{pi9},#{pi10},#{pi11},#{pi12},#{pi13},#{pi14},"
			+ "#{pi15},#{pi16},#{pi17},#{pi18},#{pi19},#{pi20},#{pi21},#{pi22},#{pi23},#{pi24},#{pi25})")
	Integer add(PerformanceIndex pi);
	
	@Select("select * from t_performance_index where taskModelId = #{taskModelId} and jcmId = #{jcmId} and sixFunction = #{sixFunction}")
	List<PerformanceIndex> queryInfoByParams(@Param("taskModelId")String taskModelId,@Param("jcmId")String jcmId,@Param("sixFunction")String sixFunction);
	
	@Delete("delete from t_performance_index where id = #{id}")
	Integer delete(@Param("id")String id);
	
	@Update("update t_performance_index set pi1 = #{pi1},pi2 = #{pi2},pi3 = #{pi3},pi4 = #{pi4},pi5 = #{pi5},pi6 = #{pi6},pi7 = #{pi7},pi8 = #{pi8},pi9 = #{pi9},"
			+ "pi10 = #{pi10},pi11 = #{pi11},pi12 = #{pi12},pi13 = #{pi13},pi14 = #{pi14},pi15 = #{pi15},pi16 = #{pi16},pi17 = #{pi17},pi18 = #{pi18},pi19 = #{pi19},"
			+ "pi20 = #{pi20},pi21 = #{pi21},pi22 = #{pi22},pi23 = #{pi23},pi24 = #{pi24},pi25 = #{pi25} where id = #{id}")
	Integer edit(PerformanceIndex pi);
}
