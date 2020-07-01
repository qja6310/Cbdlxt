package cn.com.newloading.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.LinkModel;
import cn.com.newloading.dao.mapper.JcmMapper;
import cn.com.newloading.dao.mapper.LinkModelMapper;
import cn.com.newloading.service.LinkModelService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.TimeUtil;

@Service
public class LinkModelServiceImpl implements LinkModelService {
	
	@Autowired
	private LinkModelMapper linkModelMapper;
	@Autowired
	private JcmMapper jcmMapper;

	@Override
	public List<String> getJcmPid(String id) {
		List<String> pids = new ArrayList<String>();
		String pid = id;
		while(true) {
			pid = linkModelMapper.getJcmPidByJcmId(pid);
			if("-99".equals(pid)) {
				return pids;
			}else {
				pids.add(pid);
			}
		}
	}

	@Override
	public List<String> getJcmIds(String pid) {
		return linkModelMapper.getJcmIdByPid(pid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(String projectId, String taskModelId, String[] jcmIds,String userId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (int i = 0; i < jcmIds.length; i++) {
				Integer check = linkModelMapper.check(taskModelId, jcmIds[i]);
				if(check == 0) {
					LinkModel linkModel = new LinkModel();
					linkModel.setProjectId(projectId);
					linkModel.setTaskModelId(taskModelId);
					linkModel.setJcmId(jcmIds[i]);
					linkModel.setCreateTime(TimeUtil.dateToString(new Date()));
					linkModel.setUserId(userId);
					linkModelMapper.add(linkModel);
				}
			}
			map.put("code","0000");
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
	public List<Jcm> queryInfoByParams(Jcm jcm,String taskModelId) {
		//获取所有的jcm
		List<Jcm> jcms = jcmMapper.queryInfoByParams(jcm);
		//查询已关联了的jcm
		List<String> jcmIds = linkModelMapper.getJcmIdsByTaskModelId(taskModelId);
		//去掉已关联的
		for (int i = 0; i < jcms.size(); i++) {
			if("F".equals(jcms.get(i).getType())) {
				for (int j = 0; j < jcmIds.size(); j++) {
					if(jcmIds.get(j).equals(jcms.get(i).getId())){
						jcms.remove(i);
						i--;
						break;
					}
				}
			}
		}
		return jcms;
	}

	@Override
	public List<Jcm> getYglJcmByParams(String taskModelId, String jcmPid, String projectId) {
		return linkModelMapper.getYglJcmByParams(taskModelId, jcmPid, projectId);
	}

	@Override
	public List<String> getYglJcmIds(String taskModelId, String jcmPid, String projectId) {
		// TODO Auto-generated method stub
		return linkModelMapper.getYglJcmIds(taskModelId, jcmPid, projectId);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> delete(String projectId, String taskModelId, String[] jcmIds) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			//循环删除集合中的值
			LinkModel linkModel = new LinkModel();
			for (int i = 0; i < jcmIds.length; i++) {
				linkModel.setProjectId(projectId);
				linkModel.setTaskModelId(taskModelId);
				linkModel.setJcmId(jcmIds[i]);
				linkModelMapper.delete(linkModel);
			}
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
	}
}
