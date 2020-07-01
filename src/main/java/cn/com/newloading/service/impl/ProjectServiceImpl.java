package cn.com.newloading.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Project;
import cn.com.newloading.dao.mapper.ProjectMapper;
import cn.com.newloading.service.ProjectService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.TimeUtil;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(Project project) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		//评估代号不可重复
		Integer check = projectMapper.checkPgNum(project.getPgNum());
		if(check > 0) {
			map.put("code","0001");
			map.put("type",MyUtil.PRO);
			return map;
		}
		project.setCreateTime(TimeUtil.dateToString(new Date()));
		Integer ret = projectMapper.add(project);
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
	public List<Project> queryInfoByParams() {
		// TODO Auto-generated method stub
		return projectMapper.queryInfoByParams();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> delete(String projectId) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = projectMapper.delete(projectId);
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
	public Map<String, String> edit(Project project) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		//评估代号不可重复
		Integer check = projectMapper.checkPgNum(project.getPgNum());
		if(check > 0) {
			map.put("code","0001");
			map.put("type",MyUtil.PRO);
			return map;
		}
		Integer ret = projectMapper.edit(project);
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
	public Project queryInfoById(String id) {
		// TODO Auto-generated method stub
		return projectMapper.queryInfoById(id);
	}

}
