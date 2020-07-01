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
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.bean.PerformanceIndex;
import cn.com.newloading.service.JsfService;
import cn.com.newloading.service.PerformanceIndexService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/sixFunction")
public class SixFunctionCollectController extends BaseController{
	
	@Autowired
	private PerformanceIndexService piService;
	@Autowired
	private JsfService jsfService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "sixFunction/list";
	}
	
	@RequestMapping("/details")
	public String details(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		String taskModelId = request.getParameter("taskModelId");
		if(StringUtil.isBlank(taskModelId)) {
			model.addAttribute("message", "缺失参数");
			return "common/error";
		}
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			model.addAttribute("message", "缺失参数");
			return "common/error";
		}
		String funCode = request.getParameter("funCode");
		if(StringUtil.isBlank(funCode)) {
			model.addAttribute("message", "缺失参数");
			return "common/error";
		}
		List<PerformanceIndex> pis = piService.queryInfoByParams(taskModelId, jcmId, funCode);
		model.addAttribute("pis", pis);
		if("0001".equals(funCode)) {
			return "sixFunction/cs";
		}else if("0002".equals(funCode)){
			return "sixFunction/kk";
		}else if("0003".equals(funCode)){
			return "sixFunction/bz";
		}else if("0004".equals(funCode)){
			return "sixFunction/wx";
		}else if("0005".equals(funCode)){
			return "sixFunction/sy";
		}else if("0006".equals(funCode)){
			return "sixFunction/aq";
		}else {
			model.addAttribute("message", "参数有误");
			return "common/error";
		}
	}
	
	@RequestMapping("/editData")
	public String editData(HttpServletRequest request,Model model) {
		String funCode = request.getParameter("funCode");
		String taskModelId = request.getParameter("taskModelId");
		String jcmId = request.getParameter("jcmId");
		model.addAttribute("funCode", funCode);
		model.addAttribute("taskModelId", taskModelId);
		model.addAttribute("jcmId", jcmId);
		Jsf jsf = new Jsf();
		jsf.setFunCode(funCode);
		jsf.setJcmId(jcmId);
		List<Jsf> jsfList = jsfService.getSixFunction(jsf);
		jsf = jsfList.get(0);
		model.addAttribute("jsf", jsf);
		return "sixFunction/editData";
	}
	
	@RequestMapping("/saveResult")
	@ResponseBody
	public JSONObject saveResult(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String funCode = request.getParameter("funCode");
		if(StringUtil.isBlank(funCode)) {
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
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String calResult = request.getParameter("calResult");
		if(StringUtil.isBlank(calResult)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Jsf jsf = new Jsf();
		jsf.setCalResult(calResult);
		jsf.setFunCode(funCode);
		jsf.setJcmId(jcmId);
		map = jsfService.edit(jsf);
		return responseMsg(map);
	}
}
