package cn.com.newloading.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Hint;
import cn.com.newloading.service.HintService;

public class BaseController {

	@Autowired
	private HintService hintService;
	
	/* 错误码返回 */
	public JSONObject responseMsg(Map<String, String> map) {
		JSONObject json = new JSONObject();
		String code = map.get("code");
		String type = map.get("type");
		Hint hint = new Hint();
		hint.setCode(code);
		hint.setType(type);
		List<Hint> hintList = hintService.queryHint(hint);
		hint = hintList.get(0);
		json.put("retCode", hint.getCode());
		json.put("retMsg", hint.getDescription());
		return json;
	}
	
	public static void main(String[] args) {
        String s = "[{\"id\":1,\"gradSchoolName\":\"555\",\"gradSchoolType\":\"1\",\"gradSchoolTypeText\":\"综合类\",\"academic\":\"1\",\"academicText\":\"硕士研究生\",\"degree\":\"5555\",\"specialty\":\"5555\",\"gradCertNo\":\"555\",\"degreeNo\":\"55\",\"enterDate\":\"2020-10-01\",\"gradDate\":\"2020-10-28\",\"enterSchoolAge\":\"55\",\"enterSchoolAgeRemark\":\"555\",\"fileId\":\"\"}]";
        JSONArray arr = JSONArray.parseArray(s);
        System.out.println(arr.toArray());
	}
	
}
