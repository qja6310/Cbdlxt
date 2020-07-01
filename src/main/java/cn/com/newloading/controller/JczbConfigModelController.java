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

import cn.com.newloading.bean.Dict;
import cn.com.newloading.bean.Jcm;
import cn.com.newloading.bean.Jqf;
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.DictService;
import cn.com.newloading.service.JcmService;
import cn.com.newloading.service.JqfService;
import cn.com.newloading.service.JsfService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/jcm")
public class JczbConfigModelController extends BaseController{

	@Autowired
	private JcmService jcmService;
	@Autowired
	private DictService dictService;
	@Autowired
	private JsfService jsfService;
	
	@Autowired
	private JqfService jqfService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		String projectId = request.getParameter("projectId");
		if(StringUtil.isNotBlank(projectId)) {
			request.getSession().setAttribute("projectId", projectId);
		}else {
			projectId = (String)request.getSession().getAttribute("projectId");
		}
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		
		//获取六性
		List<Dict> functions = dictService.queryInfoByParams("FUN");
		//获取性能
		List<Dict> qfunctions = dictService.queryInfoByParams("QFUN","0");
		model.addAttribute("functions", functions);
		model.addAttribute("qfunctions", qfunctions);
		model.addAttribute("projectId", projectId);
		return "jcm/list";
	}
	
	@RequestMapping("/getJcm")
	@ResponseBody
	public JSONObject getJcm(HttpServletRequest request) {
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
		Jcm jcm = new Jcm();
		jcm.setPid(pid);
		jcm.setProjectId(projectId);
		List<Jcm> jcms = jcmService.queryInfoByParams(jcm);
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
	
	@RequestMapping("/doAdd")
	@ResponseBody
	public JSONObject doAdd(HttpServletRequest request) {
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
		String modelNum = request.getParameter("modelNum");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Jcm jcm = new Jcm();
		jcm.setModelNum(modelNum);
		jcm.setName(name);
		jcm.setType(type);
		jcm.setPid(pid);
		jcm.setProjectId(projectId);
		jcm.setUserId(user.getId());
		map = jcmService.add(jcm);
		JSONObject json = responseMsg(map);
		json.put("id", map.get("id"));
		return json;
	}
	
	@RequestMapping("/doEdit")
	@ResponseBody
	public JSONObject doEdit(HttpServletRequest request) {
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
		String name = request.getParameter("name");
		if(StringUtil.isBlank(name)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String modelNum = request.getParameter("modelNum");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			map.put("code", "0003");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Jcm jcm = new Jcm();
		jcm.setModelNum(modelNum);
		jcm.setName(name);
		jcm.setId(id);
		map = jcmService.edit(jcm);
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
		Jcm jcm = new Jcm();
		jcm.setId(id);
		map = jcmService.delete(jcm);
		return responseMsg(map);
	}
	
	@RequestMapping("/getSixFunction")
	@ResponseBody
	public JSONObject getSixFunction(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String status = "E";
		Jsf jsf = new Jsf();
		jsf.setJcmId(jcmId);
		jsf.setStatus(status);
		List<Jsf> sixFunctions = jsfService.getSixFunction(jsf);
		JSONObject json = new JSONObject();
		json.put("sixFunctions", sixFunctions);
		return json;
	}
	
	@RequestMapping("/getQFunction")
	@ResponseBody
	public JSONObject getQFunction(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String status = "E";
		Jqf jqf = new Jqf();
		jqf.setJcmId(jcmId);
		jqf.setStatus(status);
		List<Jqf> qFunctions = jqfService.getQFunction(jqf);
		JSONObject json = new JSONObject();
		json.put("qFunctions", qFunctions);
		return json;
	}
	
	@RequestMapping("/changeSixFunctionStatus")
	@ResponseBody
	public JSONObject changeSixFunctionStatus(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String funCode = request.getParameter("funCode");
		if(StringUtil.isBlank(funCode)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String status = request.getParameter("status");
		if(StringUtil.isBlank(status)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map = jsfService.changeSixFunctionStatus(jcmId, funCode, status);
		return responseMsg(map);
	}
	
	@RequestMapping("/changeQFunctionStatus")
	@ResponseBody
	public JSONObject changeQFunctionStatus(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String funCode = request.getParameter("funCode");
		if(StringUtil.isBlank(funCode)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String status = request.getParameter("status");
		if(StringUtil.isBlank(status)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map = jqfService.changeQFunctionStatus(jcmId, funCode, status);
		return responseMsg(map);
	}
}
