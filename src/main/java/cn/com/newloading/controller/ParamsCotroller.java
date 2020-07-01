package cn.com.newloading.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Params;
import cn.com.newloading.service.ParamsService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/params")
public class ParamsCotroller extends BaseController{
	
	@Autowired
	private ParamsService paramsService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "paramsSet/list";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String targetName = request.getParameter("targetName");
		if(StringUtil.isBlank(targetName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String weight = request.getParameter("weight");
		String scoreWay = request.getParameter("scoreWay");
		String targetValue = request.getParameter("targetValue");
		String tolerance = request.getParameter("tolerance");
		Params params = new Params();
		params.setScoreWay(scoreWay);
		params.setTargetName(targetName);
		params.setTargetValue(targetValue);
		params.setTolerance(tolerance);
		params.setWeight(weight);
		
		map = paramsService.add(params, taskModelId, jcmId, projectId);
		return responseMsg(map);
	}
	
	@RequestMapping("/getParams")
	@ResponseBody
	public JSONObject getParams(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		List<Params> params = paramsService.queryInfo(taskModelId, jcmId, projectId);
		json.put("params", params);
		return json;
	}
	
	@RequestMapping("/doDel")
	@ResponseBody
	public JSONObject doDel(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map = paramsService.delete(id);
		return responseMsg(map);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String targetName = request.getParameter("targetName");
		if(StringUtil.isBlank(targetName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String weight = request.getParameter("weight");
		String scoreWay = request.getParameter("scoreWay");
		String targetValue = request.getParameter("targetValue");
		String tolerance = request.getParameter("tolerance");
		Params params = new Params();
		params.setId(id);
		params.setScoreWay(scoreWay);
		params.setTargetName(targetName);
		params.setTargetValue(targetValue);
		params.setTolerance(tolerance);
		params.setWeight(weight);
		map = paramsService.edit(params);
		return responseMsg(map);
	}
}
