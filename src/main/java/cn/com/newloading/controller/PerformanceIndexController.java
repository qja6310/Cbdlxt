package cn.com.newloading.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.PerformanceIndex;
import cn.com.newloading.service.PerformanceIndexService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/pi")
public class PerformanceIndexController extends BaseController {

	@Autowired
	private PerformanceIndexService piService;
	
	@RequestMapping("/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String taskModelId = request.getParameter("taskModelId");
		if (StringUtil.isBlank(taskModelId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String jcmId = request.getParameter("jcmId");
		if (StringUtil.isBlank(jcmId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String sixFunction = request.getParameter("sixFunction");
		if (StringUtil.isBlank(sixFunction)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pi1 = request.getParameter("pi1");
		String pi2 = request.getParameter("pi2");
		String pi3 = request.getParameter("pi3");
		String pi4 = request.getParameter("pi4");
		String pi5 = request.getParameter("pi5");
		String pi6 = request.getParameter("pi6");
		String pi7 = request.getParameter("pi7");
		String pi8 = request.getParameter("pi8");
		String pi9 = request.getParameter("pi9");
		String pi10 = request.getParameter("pi10");
		String pi11 = request.getParameter("pi11");
		String pi12 = request.getParameter("pi12");
		String pi13 = request.getParameter("pi13");
		String pi14 = request.getParameter("pi14");
		String pi15 = request.getParameter("pi15");
		String pi16 = request.getParameter("pi16");
		String pi17 = request.getParameter("pi17");
		String pi18 = request.getParameter("pi18");
		String pi19 = request.getParameter("pi19");
		String pi20 = request.getParameter("pi20");
		String pi21 = request.getParameter("pi21");
		String pi22 = request.getParameter("pi22");
		String pi23 = request.getParameter("pi23");
		String pi24 = request.getParameter("pi24");
		String pi25 = request.getParameter("pi25");
		PerformanceIndex pi = new PerformanceIndex();
		pi.setJcmId(jcmId);
		pi.setTaskModelId(taskModelId);
		pi.setSixFunction(sixFunction);
		pi.setPi1(pi1);
		pi.setPi2(pi2);
		pi.setPi3(pi3);
		pi.setPi4(pi4);
		pi.setPi5(pi5);
		pi.setPi6(pi6);
		pi.setPi7(pi7);
		pi.setPi8(pi8);
		pi.setPi9(pi9);
		pi.setPi10(pi10);
		pi.setPi11(pi11);
		pi.setPi12(pi12);
		pi.setPi13(pi13);
		pi.setPi14(pi14);
		pi.setPi15(pi15);
		pi.setPi16(pi16);
		pi.setPi17(pi17);
		pi.setPi18(pi18);
		pi.setPi19(pi19);
		pi.setPi20(pi20);
		pi.setPi21(pi21);
		pi.setPi22(pi22);
		pi.setPi23(pi23);
		pi.setPi24(pi24);
		pi.setPi25(pi25);
		map = piService.add(pi);
		return responseMsg(map);
	}
	
	@RequestMapping("/doDel")
	@ResponseBody
	public JSONObject doDel(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if (StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		map = piService.delete(id);
		return responseMsg(map);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if (StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String pi1 = request.getParameter("pi1");
		String pi2 = request.getParameter("pi2");
		String pi3 = request.getParameter("pi3");
		String pi4 = request.getParameter("pi4");
		String pi5 = request.getParameter("pi5");
		String pi6 = request.getParameter("pi6");
		String pi7 = request.getParameter("pi7");
		String pi8 = request.getParameter("pi8");
		String pi9 = request.getParameter("pi9");
		String pi10 = request.getParameter("pi10");
		String pi11 = request.getParameter("pi11");
		String pi12 = request.getParameter("pi12");
		String pi13 = request.getParameter("pi13");
		String pi14 = request.getParameter("pi14");
		String pi15 = request.getParameter("pi15");
		String pi16 = request.getParameter("pi16");
		String pi17 = request.getParameter("pi17");
		String pi18 = request.getParameter("pi18");
		String pi19 = request.getParameter("pi19");
		String pi20 = request.getParameter("pi20");
		String pi21 = request.getParameter("pi21");
		String pi22 = request.getParameter("pi22");
		String pi23 = request.getParameter("pi23");
		String pi24 = request.getParameter("pi24");
		String pi25 = request.getParameter("pi25");
		PerformanceIndex pi = new PerformanceIndex();
		pi.setId(id);
		pi.setPi1(pi1);
		pi.setPi2(pi2);
		pi.setPi3(pi3);
		pi.setPi4(pi4);
		pi.setPi5(pi5);
		pi.setPi6(pi6);
		pi.setPi7(pi7);
		pi.setPi8(pi8);
		pi.setPi9(pi9);
		pi.setPi10(pi10);
		pi.setPi11(pi11);
		pi.setPi12(pi12);
		pi.setPi13(pi13);
		pi.setPi14(pi14);
		pi.setPi15(pi15);
		pi.setPi16(pi16);
		pi.setPi17(pi17);
		pi.setPi18(pi18);
		pi.setPi19(pi19);
		pi.setPi20(pi20);
		pi.setPi21(pi21);
		pi.setPi22(pi22);
		pi.setPi23(pi23);
		pi.setPi24(pi24);
		pi.setPi25(pi25);
		map = piService.edit(pi);
		return responseMsg(map);
	}
}
