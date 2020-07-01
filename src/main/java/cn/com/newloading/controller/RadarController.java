package cn.com.newloading.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.newloading.bean.Jsf;
import cn.com.newloading.service.RadarService;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/radar")
public class RadarController extends BaseController{

	@Autowired
	private RadarService radarService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "radar/list";
	}
	
	@RequestMapping("/showRadar")
	public String showRadar(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		String jcmId = request.getParameter("jcmId");
		if(StringUtil.isBlank(jcmId)) {
			model.addAttribute("message", "缺失关键字");
			return "common/error";
		}
		Jsf jsf = new Jsf();
		jsf.setJcmId(jcmId);
		List<Jsf> jsfList = radarService.getJsf(jsf);
		String value="";
		//计算结果值拼接
		for (int i = 0; i < jsfList.size(); i++) {
			if(i == 0) {
				value += jsfList.get(i).getCalResult();
			}else {
				value += "," + jsfList.get(i).getCalResult();
			}
		}
		model.addAttribute("jsfList", jsfList);
		model.addAttribute("value", value);
		return "radar/radar";
	}

}
