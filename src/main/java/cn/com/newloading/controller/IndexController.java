package cn.com.newloading.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.newloading.bean.Menu;
import cn.com.newloading.bean.User;
import cn.com.newloading.service.MenuService;

@Controller
public class IndexController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index/index";
	}
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return "index/index";
		}
		List<Menu> menus = menuService.queryMenuByUserId(user.getId());
		List<Menu> myMenus = new ArrayList<Menu>();
		//获取层级关系
		for (int i = 0; i < menus.size(); i++) {
			if("-99".equals(menus.get(i).getPid())) {
				myMenus.add(menus.get(i));
			}
		}
		
		for (int i = 0; i < myMenus.size(); i++) {
			for (int j = 0; j < menus.size(); j++) {
				if(myMenus.get(i).getId().equals(menus.get(j).getPid())) {
					myMenus.get(i).getMenuList().add(menus.get(j));
				}
			}
		}
		
		model.addAttribute("user", user);
		model.addAttribute("myMenus", myMenus);
		return "index/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		return "index/index";
	}
}
