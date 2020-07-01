package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Params;

public interface ParamsService {

	Map<String, String> add(Params params,String taskModelId,String jcmId,String projectId);
	
	List<Params> queryInfo(String taskModelId,String jcmId,String projectId);
	
	Map<String, String> delete(String id);
	
	Map<String, String> edit(Params params);
	
	Map<String, String> editScore(String score,String id);
	
}
