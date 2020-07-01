package cn.com.newloading.bean;

/**
 * 船舶装备模型
 * @author 35030
 *
 */
public class TaskModel {

	private String id;
	private String pid;
	private String weight;//权重
	private String type;
	private String name;
	private String projectId;
	private String startTime;//计划任务开始时间
	private String endTime;//计划任务结束时间
	private String longestTime;//作战最长使用时间
	private String scope;//任务期间海况范围
	private String createTime;
	private String userId;
	
	public TaskModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLongestTime() {
		return longestTime;
	}

	public void setLongestTime(String longestTime) {
		this.longestTime = longestTime;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
