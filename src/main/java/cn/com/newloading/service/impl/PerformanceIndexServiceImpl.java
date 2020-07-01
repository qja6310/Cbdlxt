package cn.com.newloading.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.PerformanceIndex;
import cn.com.newloading.dao.mapper.PerformanceIndexMapper;
import cn.com.newloading.service.PerformanceIndexService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;
@Service
public class PerformanceIndexServiceImpl implements PerformanceIndexService {

	@Autowired
	private PerformanceIndexMapper piMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(PerformanceIndex pi) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = piMapper.add(pi);
		if(ret == 1) {
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}

	@Override
	public List<PerformanceIndex> queryInfoByParams(String taskModelId, String jcmId,String sixFunction) {
		// TODO Auto-generated method stub
		return piMapper.queryInfoByParams(taskModelId, jcmId, sixFunction);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> delete(String id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = piMapper.delete(id);
		if(ret == 1) {
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> edit(PerformanceIndex pi) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = piMapper.edit(pi);
		if(ret == 1) {
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}

}
