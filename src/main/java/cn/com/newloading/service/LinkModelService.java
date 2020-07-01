package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Jcm;

public interface LinkModelService {

	List<String> getJcmPid(String id);
	
	List<String> getJcmIds(String pid);
	
	Map<String, String> add(String projectId,String taskModelId,String[] jcmIds,String userId);
	
	List<Jcm> queryInfoByParams(Jcm jcm,String taskModelId);
	
	List<Jcm> getYglJcmByParams(String taskModelId,String jcmPid,String projectId);
	
	List<String> getYglJcmIds(String taskModelId,String jcmPid,String projectId);
	
	Map<String, String> delete(String projectId,String taskModelId,String[] jcmIds);
}
