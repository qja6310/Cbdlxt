package cn.com.newloading.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Dict;
import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.Jqf;
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.dao.mapper.DictMapper;
import cn.com.newloading.dao.mapper.JcmMapper;
import cn.com.newloading.dao.mapper.JqfMapper;
import cn.com.newloading.dao.mapper.JsfMapper;
import cn.com.newloading.service.JcmService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;
import cn.com.newloading.utils.TimeUtil;

@Service
public class JcmServiceImpl implements JcmService {
	
	@Autowired
	private JcmMapper jcmMapper;
	@Autowired
	private DictMapper dictMapper;
	@Autowired
	private JsfMapper jsfMapper;
	@Autowired
	private JqfMapper jqfMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(Jcm jcm) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		try {
			//检查是否唯一
			int check = jcmMapper.checkName(jcm.getPid(), jcm.getType(), jcm.getProjectId(), jcm.getName());
			if(check != 0) {
				map.put("code","0001");
				map.put("type",MyUtil.JCM);
				return map;
			}
			jcm.setCreateTime(TimeUtil.dateToString(new Date()));
			jcmMapper.add(jcm);
			if("F".equals(jcm.getType())) {
				//新增六性
				List<Dict> sixFuntions = dictMapper.queryInfoByParams("FUN");
				Jsf jsf = new Jsf();
				for (int i = 0; i < sixFuntions.size(); i++) {
					jsf.setJcmId(jcm.getId());
					jsf.setFunCode(sixFuntions.get(i).getCode());
					jsf.setStatus("F");
					jsfMapper.add(jsf);
				}
				
				//新增性能选择
				List<Dict> qFuntions = dictMapper.queryInfoByParams("QFUN");
				Jqf jqf = new Jqf();
				for (int i = 0; i < qFuntions.size(); i++) {
					jqf.setJcmId(jcm.getId());
					jqf.setQfunCode(qFuntions.get(i).getCode());
					jqf.setStatus("F");
					jqfMapper.add(jqf);
				}
			}
			if(StringUtil.isNotBlank(jcm.getId())) {
				map.put("id", jcm.getId());
				map.put("code","0000");
				map.put("type",MyUtil.COMMON);
				return map;
			}
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
	}

	@Override
	public List<Jcm> queryInfoByParams(Jcm jcm) {
		// TODO Auto-generated method stub
		return jcmMapper.queryInfoByParams(jcm);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> edit(Jcm jcm) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		//检查是否唯一
		int check = jcmMapper.checkName(jcm.getPid(), jcm.getType(), jcm.getProjectId(), jcm.getName());
		if(check != 0) {
			map.put("code","0001");
			map.put("type",MyUtil.JCM);
			return map;
		}
		jcm.setCreateTime(TimeUtil.dateToString(new Date()));
		Integer ret = jcmMapper.edit(jcm);
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
	public Map<String, String> delete(Jcm jcm) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		int ret = 0;
		ret = jcmMapper.deleteById(jcm.getId());
		if(ret != 1) {
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		jcmMapper.deleteByPid(jcm.getId());
		jsfMapper.deleteByJcmId(jcm.getId());
		map.put("code","0000");
		map.put("type",MyUtil.COMMON);
		return map;
	}
	
}
