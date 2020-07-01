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

import cn.com.newloading.bean.TaskModel;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.TaskModelService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/taskModel")
public class TaskModelController extends BaseController{

	@Autowired
	private TaskModelService taskModelService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "taskModel/list";
	}
	
	@RequestMapping("/getTaskModel")
	@ResponseBody
	public JSONObject getTaskModel(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String pid = request.getParameter("pid");
		if(StringUtil.isBlank(pid)) {
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
		TaskModel taskModel = new TaskModel();
		taskModel.setPid(pid);
		taskModel.setProjectId(projectId);
		List<TaskModel> taskModels = taskModelService.queryInfoByParams(taskModel);
		if(taskModels != null && taskModels.size() > 0) {
			json.put("taskModels", taskModels);
			json.put("retCode", "0000");
		}else {
			taskModels = new ArrayList<TaskModel>();
			json.put("TaskModels", taskModels);
			json.put("retCode", "0001");
		}
		return json;
	}
	
	@RequestMapping("/doAdd")
	@ResponseBody
	public JSONObject doAdd(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String pid = request.getParameter("pid");
		if(StringUtil.isBlank(pid)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String type = request.getParameter("type");
		if(StringUtil.isBlank(type)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String name = request.getParameter("name");
		if(StringUtil.isBlank(name)) {
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
		String weight = request.getParameter("weight");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String longestTime = request.getParameter("longestTime");
		String scope = request.getParameter("scope");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		TaskModel taskModel = new TaskModel();
		taskModel.setName(name);
		taskModel.setType(type);
		taskModel.setPid(pid);
		taskModel.setStartTime(startTime);
		taskModel.setEndTime(endTime);
		taskModel.setWeight(weight);
		taskModel.setLongestTime(longestTime);
		taskModel.setScope(scope);
		taskModel.setUserId(user.getId());
		taskModel.setProjectId(projectId);
		map = taskModelService.add(taskModel);
		JSONObject json = responseMsg(map);
		json.put("id", map.get("id"));
		return json;
	}
	
	@RequestMapping("/doEdit")
	@ResponseBody
	public JSONObject doEdit(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String name = request.getParameter("name");
		if(StringUtil.isBlank(name)) {
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
		String weight = request.getParameter("weight");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String longestTime = request.getParameter("longestTime");
		String scope = request.getParameter("scope");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		
		TaskModel taskModel = new TaskModel();
		taskModel.setName(name);
		taskModel.setStartTime(startTime);
		taskModel.setEndTime(endTime);
		taskModel.setWeight(weight);
		taskModel.setLongestTime(longestTime);
		taskModel.setScope(scope);
		taskModel.setId(id);
		taskModel.setProjectId(projectId);
		map = taskModelService.edit(taskModel);
		return responseMsg(map);
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request) {
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
		TaskModel taskModel = new TaskModel();
		taskModel.setId(id);
		map = taskModelService.delete(taskModel);
		return responseMsg(map);
	}
	
}
