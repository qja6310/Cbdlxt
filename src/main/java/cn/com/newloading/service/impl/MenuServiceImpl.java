package cn.com.newloading.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Menu;
import cn.com.newloading.dao.mapper.MenuMapper;
import cn.com.newloading.service.MenuService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.TimeUtil;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(Menu menu) {
		Map<String, String> map = new HashMap<String, String>();
		// 路径不可重复
		if(!"*".equals(menu.getPath())) {
			Integer checkPath = menuMapper.checkPath(menu.getPath());
			if(checkPath != 0) {
				map.put("code","0001");
				map.put("type",MyUtil.USER);
				return map;
			}
		}
		
		menu.setCreateTime(TimeUtil.dateToString(new Date()));
		menu.setStatus("E");
		Integer sn = menuMapper.getMaxSn();
		sn++;
		menu.setSerialNumber(sn);
		Integer ret = menuMapper.add(menu);
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
	public List<Menu> queryMenuByParams(Menu menu) {
		List<Menu> menus = menuMapper.queryMenuByParams(menu);
		return menus;
	}

	@Override
	public Integer queryMenuCountByParams(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.queryMenuCountByParams(menu);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> updateStatus(Menu menu) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = menuMapper.edit(menu);
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
	public Map<String, String> edit(Menu menu) {
		Map<String, String> map = new HashMap<String, String>();
		Integer ret = menuMapper.edit(menu);
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
	public List<Menu> queryMenuByUserId(String userId) {
		// TODO Auto-generated method stub
		return menuMapper.queryMenuByUserId(userId);
	}

}
