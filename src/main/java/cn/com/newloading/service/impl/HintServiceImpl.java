package cn.com.newloading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.newloading.bean.Hint;
import cn.com.newloading.dao.mapper.HintMapper;
import cn.com.newloading.service.HintService;

@Service
public class HintServiceImpl implements HintService {

	@Autowired
	private HintMapper hintMapper;
	
	@Override
	public List<Hint> queryHint(Hint hint) {
		// TODO Auto-generated method stub
		return hintMapper.queryHint(hint);
	}

}
