package cn.com.newloading.service;

import java.util.List;
import java.util.Map;

import cn.com.newloading.bean.TaskModel;

public interface TaskModelService {

	/**
	 * 新增
	 * @param TaskModel
	 * @return
	 */
	Map<String, String> add(TaskModel taskModel);
	
	/**
	 * 查询
	 * @param TaskModel
	 * @return
	 */
	List<TaskModel> queryInfoByParams(TaskModel taskModel);
	
	/**
	 * 修改
	 * @param TaskModel
	 * @return
	 */
	Map<String, String> edit(TaskModel taskModel);
	
	/**
	 * 删除
	 * @param TaskModel
	 * @return
	 */
	Map<String, String> delete(TaskModel taskModel);
	
}
