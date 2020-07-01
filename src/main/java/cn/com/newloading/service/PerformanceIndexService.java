package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.bean.PerformanceIndex;

public interface PerformanceIndexService {

	Map<String, String> add(PerformanceIndex pi);
	
	List<PerformanceIndex> queryInfoByParams(String taskModelId,String jcmId,String sixFunction);
	
	Map<String, String> delete(String id);
	
	Map<String, String> edit(PerformanceIndex pi);
}
