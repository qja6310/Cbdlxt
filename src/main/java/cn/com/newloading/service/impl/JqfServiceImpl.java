package cn.com.newloading.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Dict;
import cn.com.newloading.bean.Jqf;
import cn.com.newloading.dao.mapper.DictMapper;
import cn.com.newloading.dao.mapper.JqfMapper;
import cn.com.newloading.service.JqfService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Service
public class JqfServiceImpl implements JqfService {

	@Autowired
	private JqfMapper jqfMapper;
	@Autowired
	private DictMapper dictMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> changeQFunctionStatus(String jcmId, String funCode, String status) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		try {
			Integer ret = jqfMapper.changeStatus(jcmId, funCode, status);
			if(ret != 1) {
				map.put("code","0001");
				map.put("type",MyUtil.COMMON);
				return map;
			}
			//获取子性能
			List<Dict> dicts = dictMapper.queryInfoByParams2("QFUN", funCode);
			for (int i = 0; i < dicts.size(); i++) {
				jqfMapper.changeStatus(jcmId, dicts.get(i).getCode(), status);
			}
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
	}

	@Override
	public List<Jqf> getQFunction(Jqf jqf) {
		// TODO Auto-generated method stub
		return jqfMapper.queryInfoByParams(jqf);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> edit(Jqf jqf) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = jqfMapper.edit(jqf);
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
	public List<Jqf> getQFunction(Jqf jqf,String pid) {
		// TODO Auto-generated method stub
		jqf.setPid(pid);
		List<Jqf> jqfList = jqfMapper.queryInfoByParams(jqf);
		for (int i = 0; i < jqfList.size(); i++) {
			if("F".equals(jqfList.get(i).getStatus()) || StringUtil.isBlank(jqfList.get(i).getCalResult())) {
				jqfList.get(i).setCalResult("0");
			}
		}
		return jqfList;
	}

}
