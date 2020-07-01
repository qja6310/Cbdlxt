package cn.com.newloading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.newloading.bean.Dict;
import cn.com.newloading.dao.mapper.DictMapper;
import cn.com.newloading.service.DictService;

@Service
public class DictServiceImpl implements DictService{

	@Autowired
	private DictMapper dictMapper;
	
	@Override
	public List<Dict> queryInfoByParams(String type) {
		return dictMapper.queryInfoByParams(type);
	}
	
	@Override
	public List<Dict> queryInfoByParams(String type,String pid) {
		return dictMapper.queryInfoByParams1(type,pid);
	}
	
	@Override
	public List<Dict> queryInfoByParams(String type,String pid,String code) {
		return dictMapper.queryInfoByParams3(type,pid,code);
	}

}
