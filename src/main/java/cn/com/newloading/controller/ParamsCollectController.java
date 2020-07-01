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

import cn.com.newloading.bean.Dict;
import cn.com.newloading.bean.Jqf;
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.service.DictService;
import cn.com.newloading.service.JqfService;
import cn.com.newloading.service.ParamsService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/paramsCollect")
public class ParamsCollectController extends BaseController{

	@Autowired
	private ParamsService paramsService;
	@Autowired
	private JqfService jqfService;
	@Autowired
	private DictService dictService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "paramsCollect/list";
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
		String score = request.getParameter("score");
		map = paramsService.editScore(score, id);
		return responseMsg(map);
	}
	
	@RequestMapping("/editData")
	public String editData(HttpServletRequest request,Model model) {
		String taskModelId = request.getParameter("taskModelId");
		String jcmId = request.getParameter("jcmId");
		model.addAttribute("taskModelId", taskModelId);
		model.addAttribute("jcmId", jcmId);
		Jqf jqf = new Jqf();
		jqf.setJcmId(jcmId);
		jqf.setStatus("E");
		jqf.setPid("0");
		List<Jqf> jqfs = jqfService.getQFunction(jqf);
		String isJqfs = "0";
		if(jqfs == null && jqfs.size() == 0) {
			model.addAttribute("isJqfs", isJqfs);
		}else {
			isJqfs = "1";
			model.addAttribute("isJqfs", isJqfs);
			model.addAttribute("jqfs", jqfs);
//			Jqf jqf1 = jqfs.get(0);
//			List<Dict> dicts = dictService.queryInfoByParams("QFUN", "0", jqf1.getQfunCode());
//			String pid = dicts.get(0).getId();
//			jqf.setPid(pid);
//			List<Jqf> jqfs1 = jqfService.getQFunction(jqf);
//			model.addAttribute("jqfs1", jqfs1);
			model.addAttribute("calResult", jqfs.get(0).getCalResult());
		}
		return "paramsCollect/editData";
	}
	
	@RequestMapping("/getQFunction")
	@ResponseBody
	public String getQFunction(HttpServletRequest request) {
		String jcmId = request.getParameter("jcmId");
		String funCode = request.getParameter("funCode");
		List<Dict> dicts = dictService.queryInfoByParams("QFUN", "0", funCode);
		String pid = dicts.get(0).getId();
		Jqf jqf = new Jqf();
		jqf.setJcmId(jcmId);
		jqf.setStatus("E");
		jqf.setPid(pid);
		List<Jqf> jqfs = jqfService.getQFunction(jqf);
		JSONObject json = new JSONObject();
		json.put("jqfs", jqfs);
		return json.toString();             
	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public String getData(HttpServletRequest request) {
		String jcmId = request.getParameter("jcmId");
		String funCode = request.getParameter("funCode");
		Jqf jqf = new Jqf();
		jqf.setJcmId(jcmId);
		jqf.setQfunCode(funCode);
		List<Jqf> jqfs = jqfService.getQFunction(jqf);
		JSONObject json = new JSONObject();
		json.put("jqf", jqfs.get(0));
		return json.toString();             
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
		Jqf jqf = new Jqf();
		jqf.setCalResult(calResult);
		jqf.setQfunCode(funCode);
		jqf.setJcmId(jcmId);
		map = jqfService.edit(jqf);
		return responseMsg(map);
	}
}
