package cn.com.newloading.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.newloading.bean.Jqf;
import cn.com.newloading.bean.Jsf;
import cn.com.newloading.service.JqfService;
import cn.com.newloading.service.RadarService;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/lineChart")
public class LineChartController extends BaseController{

	@Autowired
	private RadarService radarService;
	@Autowired
	private JqfService jqfService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		String projectId = (String)request.getSession().getAttribute("projectId");
		if(StringUtil.isBlank(projectId)) {
			model.addAttribute("message", "请从质量评估中进入");
			return "common/error";
		}
		return "lineChart/list";
	}
	
	@RequestMapping("/showLineChart")
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
		
		Jqf jqf = new Jqf();
		jqf.setJcmId(jcmId);
		List<Jqf> jqfList = jqfService.getQFunction(jqf, "0");
		
		String names = "";//拼接x轴名字
		String values = "";//拼接y轴值
		for (int i = 0; i < jsfList.size(); i++) {
			names += "'" + jsfList.get(i).getFunName() + "',";
			values += jsfList.get(i).getCalResult() + ",";
		}
		for (int i = 0; i < jqfList.size(); i++) {
			if(i == jqfList.size() - 1) {
				names += "'" + jqfList.get(i).getFunName() + "'";
				values += jqfList.get(i).getCalResult() + "";
			}else {
				names += "'" + jqfList.get(i).getFunName() + "',";
				values += jqfList.get(i).getCalResult() + ",";
			}
		}
		model.addAttribute("names", names);
		model.addAttribute("values", values);
		return "lineChart/lineChart";
	}

}
