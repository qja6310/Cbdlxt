package cn.com.newloading.service;

import java.util.List;

import cn.com.newloading.bean.Hint;

public interface HintService {

	/**
	 * 查询字典表
	 * @param dict
	 * @return
	 */
	List<Hint> queryHint(Hint dict);
	
}
