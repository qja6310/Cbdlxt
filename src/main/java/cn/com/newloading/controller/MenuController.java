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

import cn.com.newloading.bean.Menu;
import cn.com.newloading.service.MenuService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController{
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 管理页
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String menuManage(HttpServletRequest request,Model model) {
		Menu menu = new Menu();
		menu.setPid("-99");
		List<Menu> menus = menuService.queryMenuByParams(menu);
		model.addAttribute("menus", menus);
		return "menu/list";
	}
	
	/**
	 * 获取信息总数
	 * @param request
	 * @return
	 */
	@RequestMapping("/dataCount")
	@ResponseBody
	public JSONObject dataCount(HttpServletRequest request) {
		String menuName = request.getParameter("menuName");
		String status = request.getParameter("status");
		String pid = request.getParameter("pid");
		Menu menu = new Menu();
		menu.setMenuName(menuName.trim());
		menu.setStatus(status.trim());
		menu.setPid(pid.trim());
		Integer count = menuService.queryMenuCountByParams(menu);
		JSONObject json = new JSONObject();
		json.put("count", count);
		return json;
	}
	
	/**
	 * 页面信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/page")
	public String page(HttpServletRequest request,Model model) {
		String menuName = request.getParameter("menuName");
		String status = request.getParameter("status");
		String pid = request.getParameter("pid");
		String currPage = request.getParameter("currPage");
		String limit = request.getParameter("limit");
		Menu menu = new Menu();
		menu.setMenuName(menuName);
		menu.setStatus(status.trim());
		menu.setPid(pid.trim());
		menu.setCurrent(Integer.valueOf(currPage));
		menu.setLimit(Integer.valueOf(limit));
		menu.setStart((Integer.valueOf(currPage) - 1) * Integer.valueOf(limit));
		List<Menu> menus = menuService.queryMenuByParams(menu);
		model.addAttribute("menus", menus);
		return "menu/page";
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request,Model model) {
		Menu menu = new Menu();
		menu.setPid("-99");
		List<Menu> menus = menuService.queryMenuByParams(menu);
		model.addAttribute("menus", menus);
		return "menu/add";
	}
	
	/**
	 * 确认新增
	 * @param request
	 * @return
	 */
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
		String menuName = request.getParameter("menuName");
		if(StringUtil.isBlank(menuName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String path = request.getParameter("path");
		if(StringUtil.isBlank(path)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Menu menu = new Menu();
		menu.setMenuName(menuName);
		menu.setPid(pid);
		menu.setPath(path);
		map.clear();
		map= menuService.add(menu);
		return responseMsg(map);
	}
	
	@RequestMapping("/updateStatus")
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String status = request.getParameter("status");
		if(StringUtil.isBlank(id)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Menu menu = new Menu();
		menu.setId(id);
		menu.setStatus(status);
		map.clear();
		map = menuService.updateStatus(menu);
		return responseMsg(map);
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request,Model model) {
		Menu menu = new Menu();
		menu.setPid("-99");
		List<Menu> menus = menuService.queryMenuByParams(menu);
		model.addAttribute("menus", menus);
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			return "common/error";
		}
		menu = new Menu();
		menu.setId(id);
		menus = menuService.queryMenuByParams(menu);
		if(menus != null && menus.size() > 0) {
			menu = menus.get(0);
			model.addAttribute("menu", menu);
		}
		return "menu/edit";
	}
	
	/**
	 * 确认新增
	 * @param request
	 * @return
	 */
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
		String pid = request.getParameter("pid");
		if(StringUtil.isBlank(pid)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String menuName = request.getParameter("menuName");
		if(StringUtil.isBlank(menuName)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String path = request.getParameter("path");
		if(StringUtil.isBlank(path)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String serialNumber = request.getParameter("serialNumber");
		if(StringUtil.isBlank(serialNumber)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		Menu menu = new Menu();
		menu.setId(id);
		menu.setMenuName(menuName);
		menu.setPid(pid);
		menu.setPath(path);
		menu.setSerialNumber(Integer.valueOf(serialNumber));
		map.clear();
		map= menuService.edit(menu);
		return responseMsg(map);
	}
	
	//获取子菜单
	@RequestMapping("getMenu")
	@ResponseBody
	public JSONObject getMenu(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		Menu menu = new Menu();
		menu.setPid(id);
		List<Menu> menus = menuService.queryMenuByParams(menu);
		json.put("menuList", menus);
		return json;
	}
}
