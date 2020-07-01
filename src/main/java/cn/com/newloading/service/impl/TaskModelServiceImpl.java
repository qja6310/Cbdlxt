package cn.com.newloading.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.TaskModel;
import cn.com.newloading.dao.mapper.TaskModelMapper;
import cn.com.newloading.service.TaskModelService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;
import cn.com.newloading.utils.TimeUtil;

@Service
public class TaskModelServiceImpl implements TaskModelService {
	
	@Autowired
	private TaskModelMapper taskModelMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(TaskModel taskModel) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		//检查是否唯一
		int check = taskModelMapper.checkName(taskModel.getPid(), taskModel.getType(),taskModel.getProjectId(),taskModel.getName());
		if(check != 0) {
			map.put("code","0001");
			map.put("type",MyUtil.TM);
			return map;
		}
		taskModel.setCreateTime(TimeUtil.dateToString(new Date()));
		taskModelMapper.add(taskModel);
		if(StringUtil.isNotBlank(taskModel.getId())) {
			map.put("id", taskModel.getId());
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}

	@Override
	public List<TaskModel> queryInfoByParams(TaskModel taskModel) {
		// TODO Auto-generated method stub
		return taskModelMapper.queryInfoByParams(taskModel);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> edit(TaskModel taskModel) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		//检查是否唯一
		int check = taskModelMapper.checkName(taskModel.getPid(), taskModel.getType(),taskModel.getProjectId(),taskModel.getName());
		if(check != 0) {
			map.put("code","0001");
			map.put("type",MyUtil.TM);
			return map;
		}
		taskModel.setCreateTime(TimeUtil.dateToString(new Date()));
		Integer ret = taskModelMapper.edit(taskModel);
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
	public Map<String, String> delete(TaskModel taskModel) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		int ret = 0;
		ret = taskModelMapper.deleteById(taskModel.getId());
		if(ret != 1) {
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		taskModelMapper.deleteByPid(taskModel.getId());
		map.put("code","0000");
		map.put("type",MyUtil.COMMON);
		return map;
	}

}
