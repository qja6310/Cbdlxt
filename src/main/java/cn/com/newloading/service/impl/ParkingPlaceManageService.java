package cn.com.newloading.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.newloading.bean.Config;
import cn.com.newloading.bean.ParkingPlace;
import cn.com.newloading.dao.mapper.ConfigMapper;
import cn.com.newloading.dao.mapper.ParkingPlaceMapper;
import cn.com.newloading.utils.MyUtil;

@Service
public class ParkingPlaceManageService {
	
	@Autowired
	public ParkingPlaceMapper parkingPlaceMapper;
	@Autowired
	public ConfigMapper configMapper;

	public List<ParkingPlace> queryParkPlaceInfo(ParkingPlace parkingPlace){
		return parkingPlaceMapper.queryParkPlaceInfo(parkingPlace);
	}
	
	public int queryInfoCountByParams(ParkingPlace parkingPlace){
		return parkingPlaceMapper.queryInfoCountByParams(parkingPlace);
	}
	
	public List<String> queryAllArea(){
		return parkingPlaceMapper.queryAllArea();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> editStatus(ParkingPlace parkingPlace) {
		Map<String, String> map = new HashMap<String, String>();
		int i = parkingPlaceMapper.editStatus(parkingPlace);
		if(i == 1) {
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> add(ParkingPlace parkingPlace) {
		Map<String, String> map = new HashMap<String, String>();
		int count = parkingPlaceMapper.check(parkingPlace);
		if(count != 0) {
			map.put("code","0001");
			map.put("type",MyUtil.PPM);
			return map;
		}
		int i = parkingPlaceMapper.add(parkingPlace);
		if(i == 1) {
			map.put("code","0000");
			map.put("type",MyUtil.COMMON);
			return map;
		}
		map.put("code","0001");
		map.put("type",MyUtil.COMMON);
		return map;
	}
	
	public List<Map<String, String>> count(){
		List<Map<String, String>> count = parkingPlaceMapper.count();
		Config config = new Config();
		config.setType("pps");
		List<Config> list = configMapper.queryInfoByparam(config);
		boolean flag;
		if(count.size() != list.size()) {
			for (int i = 0; i < list.size(); i++) {
				flag = true;
				for (int j = 0; j < count.size(); j++) {
					if(list.get(i).getCode().equals(count.get(j).get("status"))) {
						count.get(j).put("param", list.get(i).getParam());
						flag = false;
						break;
					}
				}
				if(flag) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("count", "0");
					map.put("status", list.get(i).getCode());
					map.put("param", list.get(i).getParam());
					count.add(map);
				}
			}
		}else {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < count.size(); j++) {
					if(list.get(i).getCode().equals(count.get(j).get("status"))) {
						count.get(j).put("param", list.get(i).getParam());
					}
				}
			}
		}
		return count;
	}
}
