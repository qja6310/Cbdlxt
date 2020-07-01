package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Jqf;

public interface JqfService {

	Map<String, String> changeQFunctionStatus(String jcmId,String funCode,String status);
	
	List<Jqf> getQFunction(Jqf jqf);
	
	List<Jqf> getQFunction(Jqf jqf,String pid);
	
	Map<String, String> edit(Jqf jqf);
	
}
