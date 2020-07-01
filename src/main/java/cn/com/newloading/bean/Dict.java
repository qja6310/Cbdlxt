package cn.com.newloading.bean;

public class Dict {

	private String id;
	private String name;
	private String code;
	private String type;
	private String pid;
	
	public Dict() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Dict [id=" + id + ", name=" + name + ", code=" + code + ", type=" + type + ", pid=" + pid + "]";
	}
	
	
}
