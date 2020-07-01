package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.bean.PerformanceIndex;

public interface JsfService {

	/**
	 * 修改六性状态
	 * @param jcmId
	 * @param funCode
	 * @param status
	 * @return
	 */
	Map<String, String> changeSixFunctionStatus(String jcmId,String funCode,String status);
	
	List<Jsf> getSixFunction(Jsf jsf);
	
	Map<String, String> edit(Jsf jsf);
	
}
