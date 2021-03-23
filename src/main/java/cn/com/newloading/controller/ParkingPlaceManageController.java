package cn.com.newloading.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Config;
import cn.com.newloading.bean.ParkingPlace;
import cn.com.newloading.service.impl.ConfigService;
import cn.com.newloading.service.impl.ParkingPlaceManageService;

@Controller
@RequestMapping("/cwgl")
public class ParkingPlaceManageController extends BaseController{
	
	@Autowired
	public ParkingPlaceManageService ppmService;
	@Autowired
	public ConfigService configService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model) {
		List<String> areas = ppmService.queryAllArea();
		model.addAttribute("areas", areas);
		Config config = new Config();
		config.setType("pps");
		List<Config> configList = configService.queryInfoByparam(config);
		model.addAttribute("configList", configList);
		return "parkingPlaceManage/list";
	}
	
	@RequestMapping("/dataCount")
	@ResponseBody
	public JSONObject dataCount(HttpServletRequest request) {
		String area = request.getParameter("area");
		String number = request.getParameter("number");
		String status = request.getParameter("status");
		ParkingPlace parkingPlace = new ParkingPlace();
		parkingPlace.setStatus(status);
		parkingPlace.setArea(area);
		parkingPlace.setNumber(number);
		Integer count = ppmService.queryInfoCountByParams(parkingPlace);
		JSONObject json = new JSONObject();
		json.put("count", count);
		return json;
	}
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request,Model model) {
		String area = request.getParameter("area");
		String number = request.getParameter("number");
		String status = request.getParameter("status");
		String currPage = request.getParameter("currPage");
		String limit = request.getParameter("limit");
		ParkingPlace parkingPlace = new ParkingPlace();
		parkingPlace.setStatus(status);
		parkingPlace.setArea(area);
		parkingPlace.setNumber(number);
		parkingPlace.setCurrent(Integer.valueOf(currPage));
		parkingPlace.setLimit(Integer.valueOf(limit));
		parkingPlace.setStart((Integer.valueOf(currPage) - 1) * Integer.valueOf(limit));
		List<ParkingPlace> infoList = ppmService.queryParkPlaceInfo(parkingPlace);
		model.addAttribute("list", infoList);
		Config config = new Config();
		config.setType("pps");
		List<Config> configList = configService.queryInfoByparam(config);
		model.addAttribute("configList", configList);
		return "parkingPlaceManage/page";
	}
	
	@RequestMapping("/count")
	public String count(HttpServletRequest request,Model model) {
		List<Map<String, String>> count = ppmService.count();
		String params = "";
		String values = "";
		int total = 0;
		for (int i = 0; i < count.size(); i++) {
			if(i == 0) {
				params += "'"+count.get(i).get("param")+"'";
				values += "{value: "+String.valueOf(count.get(i).get("count"))+", name: '"+count.get(i).get("param")+"'}";
			}else {
				params += ",'"+count.get(i).get("param")+"'";
				values += ",{value: "+String.valueOf(count.get(i).get("count"))+", name: '"+count.get(i).get("param")+"'}";
			}
			total += Long.valueOf(String.valueOf(count.get(i).get("count")));
		}
		model.addAttribute("params", params);
		model.addAttribute("values", values);
		model.addAttribute("counts", count);
		model.addAttribute("total", total);
		return "parkingPlaceManage/count";
	}
	
	@RequestMapping("/editStatus")
	@ResponseBody
	public JSONObject editStatus(HttpServletRequest request) {
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		ParkingPlace parkingPlace = new ParkingPlace();
		parkingPlace.setId(id);
		parkingPlace.setStatus(status);
		Map<String, String> editStatus = ppmService.editStatus(parkingPlace);
		return responseMsg(editStatus);
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request,Model model) {
		List<String> areas = ppmService.queryAllArea();
		model.addAttribute("areas", areas);
		String areastr = "";
		for (int i = 0; i < areas.size(); i++) {
			areastr += areas.get(i);
		}
		model.addAttribute("areastr", areastr);
		return "parkingPlaceManage/add";
	}
	
	@RequestMapping("/doAdd")
	@ResponseBody
	public JSONObject doAdd(HttpServletRequest request) {
		String area = request.getParameter("area");
		String number = request.getParameter("number");
		ParkingPlace parkingPlace = new ParkingPlace();
		parkingPlace.setArea(area);
		parkingPlace.setNumber(number);
		return responseMsg(ppmService.add(parkingPlace));
	}
}
