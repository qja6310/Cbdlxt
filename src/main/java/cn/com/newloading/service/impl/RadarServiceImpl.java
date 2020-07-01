package cn.com.newloading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.dao.mapper.JsfMapper;
import cn.com.newloading.service.RadarService;
import cn.com.newloading.utils.StringUtil;
@Service
public class RadarServiceImpl implements RadarService {

	@Autowired
	private JsfMapper jsfMapper;
	
	@Override
	public List<Jsf> getJsf(Jsf jsf) {
		// TODO Auto-generated method stub
		List<Jsf> jsfList = jsfMapper.queryInfoByParams(jsf);
		//状态为F的或者calResult为null的设置为0
		for (int i = 0; i < jsfList.size(); i++) {
			if("F".equals(jsfList.get(i).getStatus()) || StringUtil.isBlank(jsfList.get(i).getCalResult())) {
				jsfList.get(i).setCalResult("0");
			}
		}
		return jsfList;
	}
}
