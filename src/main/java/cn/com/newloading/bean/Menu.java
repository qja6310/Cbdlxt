package cn.com.newloading.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu extends PageBean{

	private String id;
	private String menuName;//菜单
	private String status;//状态
	private String pid;//父级id
	private Integer serialNumber;//序号
	private String path;//路径
	private String createTime;//创建时间
	
	private String fMenuName;//父级菜单名
	private String isOwn;//用户是否有这个权限
	private String userId;//用户id
	
	private List<Menu> menuList = new ArrayList<Menu>();
	
	public Menu() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getfMenuName() {
		return fMenuName;
	}

	public void setfMenuName(String fMenuName) {
		this.fMenuName = fMenuName;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}
	
	
}
