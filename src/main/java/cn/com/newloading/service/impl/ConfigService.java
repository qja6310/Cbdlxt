package cn.com.newloading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.newloading.bean.Config;
import cn.com.newloading.dao.mapper.ConfigMapper;

@Service
public class ConfigService {

	@Autowired
	public ConfigMapper configMapper;
	
	public List<Config> queryInfoByparam(Config config){
		return configMapper.queryInfoByparam(config);
	}
	
}
