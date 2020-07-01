package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.com.newloading.bean.Dict;

@Mapper
public interface DictMapper {

	@Select("select * from t_dict where type = #{type}")
	List<Dict> queryInfoByParams(@Param("type")String type);
	
	@Select("select * from t_dict where type = #{type} and pid = #{pid}")
	List<Dict> queryInfoByParams1(@Param("type")String type,@Param("pid")String pid);
	
	@Select("select * from t_dict where type = #{type} and pid = #{pid} and code = #{code}")
	List<Dict> queryInfoByParams3(@Param("type")String type,@Param("pid")String pid,@Param("code")String code);
	
	@Select("select d1.* from t_dict d1 inner join t_dict d2 on d1.pid = d2.id where d2.type = #{type} and d2.code = #{code}")
	List<Dict> queryInfoByParams2(@Param("type")String type,@Param("code")String code);
}
