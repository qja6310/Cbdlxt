package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.Project;

public interface ProjectService {

	/**
	 * 新增项目
	 * @param project
	 * @return
	 */
	Map<String, String> add(Project project);
	
	/**
	 * 查询工程信息
	 * @return
	 */
	List<Project> queryInfoByParams();
	
	/**
	 * 删除工程
	 * @param projectId
	 * @return
	 */
	Map<String, String> delete(String projectId);
	
	/**
	 * 修改工程
	 * @param projectId
	 * @return
	 */
	Map<String, String> edit(Project project);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Project queryInfoById(String id);
	
}
