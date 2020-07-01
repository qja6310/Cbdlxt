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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Hint;
import cn.com.newloading.bean.Menu;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.HintService;
import cn.com.newloading.service.MenuService;
import cn.com.newloading.service.UserService;
import cn.com.newloading.utils.MyUtil;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("/doLogin")
	@ResponseBody
	public JSONObject doLogin(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String number = request.getParameter("number");
		if (StringUtil.isBlank(number)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String password = request.getParameter("password");
		if (StringUtil.isBlank(password)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		User user = userService.doLogin(number, password);
		if (user == null) {
			json.put("retCode", "0");
			json.put("retMsg", "登陆失败");
		} else {
			json.put("retCode", "1");
			json.put("retMsg", "登陆成功");
			request.getSession().setAttribute("user", user);
		}
		return json;
	}
	
	/**
	 * 管理页
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String menuManage(HttpServletRequest request,Model model) {
		return "user/list";
	}
	
	/**
	 * 获取信息总数
	 * @param request
	 * @return
	 */
	@RequestMapping("/dataCount")
	@ResponseBody
	public JSONObject dataCount(HttpServletRequest request) {
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		User user = new User();
		user.setName(name);
		user.setNumber(number);
		Integer count = userService.queryInfoCountByParams(user);
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
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String currPage = request.getParameter("currPage");
		String limit = request.getParameter("limit");
		User user = new User();
		user.setName(name);
		user.setNumber(number);
		user.setCurrent(Integer.valueOf(currPage));
		user.setLimit(Integer.valueOf(limit));
		user.setStart((Integer.valueOf(currPage) - 1) * Integer.valueOf(limit));
		List<User> users = userService.queryInfoByParams(user);
		model.addAttribute("users", users);
		return "user/page";
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		return "user/add";
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
		String name = request.getParameter("name");
		if(StringUtil.isBlank(name)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String age = request.getParameter("age");
		if(StringUtil.isBlank(age)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String sex = request.getParameter("sex");
		if(StringUtil.isBlank(sex)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String phone = request.getParameter("phone");
		if(StringUtil.isBlank(phone)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String password = request.getParameter("password");
		if(StringUtil.isBlank(password)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String number = request.getParameter("number");
		if(StringUtil.isBlank(number)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		User user = new User();
		user.setName(name);
		user.setAge(age);
		user.setSex(sex);
		user.setPhone(phone);
		user.setPassword(password);
		user.setNumber(number);
		map.clear();
		map= userService.add(user);
		return responseMsg(map);
	}
	
	/**
	 * 编辑页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			return "common/error";
		}
		User user = new User();
		user.setId(id);
		List<User> users = userService.queryInfoByParams(user);
		if(users == null || users.size() == 0) {
			return "common/error";
		}
		user = users.get(0);
		model.addAttribute("user", user);
		return "user/edit";
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
		String name = request.getParameter("name");
		if(StringUtil.isBlank(name)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String age = request.getParameter("age");
		if(StringUtil.isBlank(age)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String sex = request.getParameter("sex");
		if(StringUtil.isBlank(sex)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String phone = request.getParameter("phone");
		if(StringUtil.isBlank(phone)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String password = request.getParameter("password");
		if(StringUtil.isBlank(password)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String number = request.getParameter("number");
		if(StringUtil.isBlank(number)) {
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
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAge(age);
		user.setSex(sex);
		user.setPhone(phone);
		user.setPassword(password);
		user.setNumber(number);
		map.clear();
		map= userService.edit(user);
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
		User user = new User();
		user.setId(id);
		map.clear();
		map= userService.delete(user);
		return responseMsg(map);
	}
	
	//权限配置页面
	@RequestMapping("limits")
	public String limits(HttpServletRequest request,Model model) {
		String userId = request.getParameter("id");
		Menu menu = new Menu();
		menu.setPid("-99");
		List<Menu> menus = menuService.queryMenuByParams(menu);//根据pid，先获取一级菜单
		//由于一级菜单中有二级菜单，所以逐个获取
		for (int i = 0; i < menus.size(); i++) {
			menu.setPid(menus.get(i).getId());
			List<Menu> menuList = menuService.queryMenuByParams(menu);
			menus.get(i).setMenuList(menuList);
		}
		
		//根据用户id获取菜单
		menu = new Menu();
		menu.setUserId(userId);
		List<Menu> menusByUserId = menuService.queryMenuByUserId(userId);
		if(menusByUserId != null && menusByUserId.size() > 0) {
			//循环判断用户拥有的菜单权限
			for (int i = 0; i < menus.size(); i++) {
				for (int j = 0; j < menusByUserId.size(); j++) {
					if(menus.get(i).getId().equals(menusByUserId.get(j).getId())) {
						menus.get(i).setIsOwn("1");
						List<Menu> tmpList = menus.get(i).getMenuList();
						for (int j2 = 0; j2 < tmpList.size(); j2++) {
							for (int j3 = 0; j3 < menusByUserId.size(); j3++) {
								if(tmpList.get(j2).getId().equals(menusByUserId.get(j3).getId())) {
									tmpList.get(j2).setIsOwn("1");
								}
							}
						}
					}
				}
			}
		}
		model.addAttribute("menus", menus);
		model.addAttribute("userId", userId);
		return  "user/limits";
	}
	
	
	//确认配置权限
	@RequestMapping("configLimits")
	@ResponseBody
	public JSONObject configLimits(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String userId = request.getParameter("userId");
		if(StringUtil.isBlank(userId)) {
			map.put("code", "0002");
			map.put("type", MyUtil.COMMON);
			return responseMsg(map);
		}
		String ids = request.getParameter("ids");
		map.clear();
		map = userService.configLimits(userId, ids);
		return responseMsg(map);
	}
}
