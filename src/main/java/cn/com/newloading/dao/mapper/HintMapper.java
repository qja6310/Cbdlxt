package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.com.newloading.bean.Hint;

@Mapper
public interface HintMapper {

	/**
	 * 查询字典表
	 * @param dict
	 * @return
	 */
	@Select("SELECT id,code,type,description FROM t_hint WHERE code = #{code} AND type = #{type}")
	List<Hint> queryHint(Hint hint);
}
