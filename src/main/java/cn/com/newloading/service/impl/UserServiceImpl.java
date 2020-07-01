package cn.com.newloading.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.User;
import cn.com.newloading.dao.mapper.UserMapper;
import cn.com.newloading.service.UserService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;
import cn.com.newloading.utils.TimeUtil;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User doLogin(String number, String password) {
		// TODO Auto-generated method stub
		return userMapper.doLogin(number, password);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(User user) {
		Map<String, String> map = new HashMap<String, String>();
		//新增之前查询号码是否可用
		int count = userMapper.checkNumber(user.getNumber());
		if(count != 0) {
			map.put("code","0001");
			map.put("type",MyUtil.PRO);
			return map;
		}
		user.setStatus("E");
		user.setCreateTime(TimeUtil.dateToString(new Date()));
		Integer ret = userMapper.add(user);
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
	public Integer queryInfoCountByParams(User user) {
		// TODO Auto-generated method stub
		return userMapper.queryInfoCountByParams(user);
	}

	@Override
	public List<User> queryInfoByParams(User user) {
		// TODO Auto-generated method stub
		return userMapper.queryInfoByParams(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> edit(User user) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = userMapper.edit(user);
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
	public Map<String, String> delete(User user) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = userMapper.delete(user.getId());
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
	public Map<String, String> configLimits(String userId, String ids) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Integer ret = 0;
			if(StringUtil.isBlank(ids)) {//没有给该用户分配权限，删除其他的即可
				ret = userMapper.deleteLimits(userId);
				map.put("code","0000");
				map.put("type",MyUtil.COMMON);
				return map;
			}
			
			//有权限
			ret = 0;
			String[] menuIds = ids.split(",");
			ret = userMapper.deleteLimits(userId);
			for (int i = 0; i < menuIds.length; i++) {
				ret = 0;
				ret = userMapper.configLimits(userId, menuIds[i]);
				if(ret != 1) {//不为1，说明发生异常
					map.put("code","0001");
					map.put("type",MyUtil.COMMON);
					return map;
				}
			}
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}catch (Exception e) {
			map.put("code","0001");
			map.put("type",MyUtil.COMMON);
			return map;
		}
	}
	
}
