package cn.com.newloading.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Params;
import cn.com.newloading.dao.mapper.LinkModelMapper;
import cn.com.newloading.dao.mapper.ParamsMapper;
import cn.com.newloading.service.ParamsService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Service
public class ParamsServiceImpl implements ParamsService {
	
	@Autowired
	private LinkModelMapper lmMapper;
	@Autowired
	private ParamsMapper paramsMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(Params params,String taskModelId,String jcmId,String projectId) {
		Map<String, String> map = new HashMap<String, String>();
		//获取linkModelId
		String linkModelId = lmMapper.getLinkModelId(taskModelId, jcmId, projectId);
		params.setLinkModelId(linkModelId);
		Integer ret = paramsMapper.add(params);
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
	public List<Params> queryInfo(String taskModelId, String jcmId, String projectId) {
		//获取linkModelId
		String linkModelId = lmMapper.getLinkModelId(taskModelId, jcmId, projectId);
		List<Params> params = paramsMapper.queryInfoByLinkModelId(linkModelId);
		for (int i = 0; i < params.size(); i++) {
			if(StringUtil.isBlank(params.get(i).getScore())) {
				params.get(i).setScore("");
			}
		}
		return params;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> delete(String id) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = paramsMapper.delete(id);
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
	public Map<String, String> edit(Params params) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = paramsMapper.edit(params);
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
	public Map<String, String> editScore(String score, String id) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = paramsMapper.editScore(score,id);
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
