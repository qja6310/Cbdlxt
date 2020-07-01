package cn.com.newloading.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.dao.mapper.JsfMapper;
import cn.com.newloading.service.JsfService;
import cn.com.newloading.utils.MyUtil;

@Service
public class JsfServiceImpl implements JsfService{

	@Autowired
	private JsfMapper jsfMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> changeSixFunctionStatus(String jcmId, String funCode, String status) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = jsfMapper.changeStatus(jcmId, funCode, status);
		if(ret != 1) {
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0000");
		map.put("type",MyUtil.COMMON);
		return map;
	}

	@Override
	public List<Jsf> getSixFunction(Jsf jsf) {
		// TODO Auto-generated method stub
		return jsfMapper.queryInfoByParams(jsf);
	}

	@Override
	public Map<String, String> edit(Jsf jsf) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = jsfMapper.edit(jsf);
		if(ret != 1) {
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0000");
		map.put("type",MyUtil.COMMON);
		return map;
	}

}
