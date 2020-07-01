package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.Jsf;

public interface JcmService {

	/**
	 * 新增
	 * @param Jcm
	 * @return
	 */
	Map<String, String> add(Jcm jcm);
	
	/**
	 * 查询
	 * @param Jcm
	 * @return
	 */
	List<Jcm> queryInfoByParams(Jcm Jcm);
	
	/**
	 * 修改
	 * @param Jcm
	 * @return
	 */
	Map<String, String> edit(Jcm Jcm);
	
	/**
	 * 删除
	 * @param Jcm
	 * @return
	 */
	Map<String, String> delete(Jcm Jcm);
	
}
