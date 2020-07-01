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

import cn.com.newloading.bean.Project;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.ProjectService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("project")
public class ProjectController extends BaseController{

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		
		return "project/list";
	}
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request,Model model) {
		List<Project> projects = projectService.queryInfoByParams();
		model.addAttribute("projects", projects);
		return "project/page";
	}
	
	@RequestMapping("toAdd")
	public String toAdd() {
		
		return "project/add";
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	public JSONObject doAdd(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String pgNum = request.getParameter("pgNum");
		if(StringUtil.isBlank(pgNum)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pgName = request.getParameter("pgName");
		if(StringUtil.isBlank(pgName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jczbNum = request.getParameter("jczbNum");
		if(StringUtil.isBlank(jczbNum)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jczbName = request.getParameter("jczbName");
		if(StringUtil.isBlank(jczbName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pgDesc = request.getParameter("pgDesc");
		if(StringUtil.isBlank(pgDesc)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Project project = new Project();
		project.setPgNum(pgNum);
		project.setPgName(pgName);
		project.setJczbName(jczbName);
		project.setJczbNum(jczbNum);
		project.setPgDesc(pgDesc);
		project.setUserId(user.getId());
		map = projectService.add(project);
		return responseMsg(map);
	}
	
	/**
	 * 确认删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/doDelete")
	@ResponseBody
	public JSONObject doDelete(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map= projectService.delete(id);
		return responseMsg(map);
	}
	
	@RequestMapping("toEdit")
	public String toEdit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			return "common/error";
		}
		Project project = projectService.queryInfoById(id);
		model.addAttribute("project", project);
		return "project/edit";
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	public JSONObject doEdit(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pgNum = request.getParameter("pgNum");
		if(StringUtil.isBlank(pgNum)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pgName = request.getParameter("pgName");
		if(StringUtil.isBlank(pgName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jczbNum = request.getParameter("jczbNum");
		if(StringUtil.isBlank(jczbNum)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jczbName = request.getParameter("jczbName");
		if(StringUtil.isBlank(jczbName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pgDesc = request.getParameter("pgDesc");
		if(StringUtil.isBlank(pgDesc)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Project project = new Project();
		project.setId(id);
		project.setPgNum(pgNum);
		project.setPgName(pgName);
		project.setJczbName(jczbName);
		project.setJczbNum(jczbNum);
		project.setPgDesc(pgDesc);
		map = projectService.edit(project);
		return responseMsg(map);
	}
	
	@RequestMapping("/closeDH")
	@ResponseBody
	public JSONObject closeDH(HttpServletRequest request) {
		request.getSession().removeAttribute("projectId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "0000");
		map.put("type", MyUtil.COMMON);
		return responseMsg(map);
	}
	
}
