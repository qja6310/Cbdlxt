package cn.com.newloading.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import cn.com.newloading.bean.Config;
import cn.com.newloading.dao.provider.ConfigProvider;

@Mapper
public interface ConfigMapper {

	@SelectProvider(type = ConfigProvider.class,method = "queryInfoByparam")
	List<Config> queryInfoByparam(Config config);
	
}
