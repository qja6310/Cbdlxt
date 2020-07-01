package cn.com.newloading.service;

import java.util.List;

import cn.com.newloading.bean.Dict;

public interface DictService {

	List<Dict> queryInfoByParams(String type);
	
	List<Dict> queryInfoByParams(String type,String pid);
	
	List<Dict> queryInfoByParams(String type,String pid,String code);
	
}
