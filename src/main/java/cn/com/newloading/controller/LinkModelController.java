package cn.com.newloading.controller;

import java.util.ArrayList;
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

import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.JcmService;
import cn.com.newloading.service.LinkModelService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/linkModel")
public class LinkModelController extends BaseController{

	@Autowired
	private JcmService jcmService;
	@Autowired
	private LinkModelService linkModelService; 
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "linkModel/list";
	}
	
	@RequestMapping("/getWglJcm")
	@ResponseBody
	public JSONObject getWglJcm(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pid = request.getParameter("pid");
		if(StringUtil.isBlank(pid)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Jcm jcm = new Jcm();
		jcm.setPid(pid);
		jcm.setProjectId(projectId);
		List<Jcm> jcms = linkModelService.queryInfoByParams(jcm,taskModelId);
		if(jcms != null && jcms.size() > 0) {
			json.put("jcms", jcms);
			json.put("retCode", "0000");
		}else {
			jcms = new ArrayList<Jcm>();
			json.put("jcms", jcms);
			json.put("retCode", "0001");
		}
		return json;
	}
	
	@RequestMapping("/getJcmPid")
	@ResponseBody
	public JSONObject getJcmPid(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		List<String> pids = new ArrayList<String>();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			json.put("pids", pids);
			return json;
		}
		pids = linkModelService.getJcmPid(id);
		json.put("pids", pids);
		return json;
	}
	
	@RequestMapping("/getJcmIds")
	@ResponseBody
	public JSONObject getJcmIds(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		List<String> ids = new ArrayList<String>();
		String pid = "";
		if(StringUtil.isBlank(id)) {
			json.put("pids", ids);
			json.put("pid", pid);
			return json;
		}
		Jcm jcm = new Jcm();
		jcm.setId(id);
		//获取pid
		List<Jcm> queryInfoByParams = jcmService.queryInfoByParams(jcm);
		jcm = queryInfoByParams.get(0);
		pid = jcm.getPid();
		json.put("pid", pid);
		
		//获取同一级别的id
		ids = linkModelService.getJcmIds(pid);
		json.put("ids", ids);
		return json;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jcmIds = request.getParameter("jcmIds");
		if(StringUtil.isBlank(jcmIds)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String[] jcmIdsArr = jcmIds.split(",");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map = linkModelService.add(projectId, taskModelId, jcmIdsArr, user.getId());
		return responseMsg(map);
	}
	
	@RequestMapping("/getYglJcm")
	@ResponseBody
	public JSONObject getYglJcm(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jcmPid = request.getParameter("pid");
		if(StringUtil.isBlank(jcmPid)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		
		List<Jcm> jcms = linkModelService.getYglJcmByParams(taskModelId, jcmPid, projectId);
		if(jcms != null && jcms.size() > 0) {
			json.put("jcms", jcms);
			json.put("retCode", "0000");
		}else {
			jcms = new ArrayList<Jcm>();
			json.put("jcms", jcms);
			json.put("retCode", "0001");
		}
		return json;
	}
	
	@RequestMapping("/getYglJcmIds")
	@ResponseBody
	public JSONObject getYglJcmIds(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String taskModelId = request.getParameter("taskModelId");
		String id = request.getParameter("id");
		String projectId = (String)request.getSession().getAttribute("projectId");
		List<String> ids = new ArrayList<String>();
		String pid = "";
		if(StringUtil.isBlank(id) || StringUtil.isBlank(taskModelId) || StringUtil.isBlank(projectId)) {
			json.put("pids", ids);
			json.put("pid", pid);
			return json;
		}
		Jcm jcm = new Jcm();
		jcm.setId(id);
		//获取pid
		List<Jcm> queryInfoByParams = jcmService.queryInfoByParams(jcm);
		jcm = queryInfoByParams.get(0);
		pid = jcm.getPid();
		json.put("pid", pid);
		
		//获取同一级别的id
		ids = linkModelService.getYglJcmIds(taskModelId, pid, projectId);
		json.put("ids", ids);
		return json;
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public JSONObject remove(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String taskModelId = request.getParameter("taskModelId");
		String ids = request.getParameter("jcmIds");
		String projectId = (String)request.getSession().getAttribute("projectId");
		String pid = "";
		if(StringUtil.isBlank(ids) || StringUtil.isBlank(taskModelId) || StringUtil.isBlank(projectId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String[] jcmIds = ids.split(",");
		map = linkModelService.delete(projectId, taskModelId, jcmIds);
		return responseMsg(map);
	}
}
