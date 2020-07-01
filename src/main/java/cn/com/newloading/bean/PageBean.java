package cn.com.newloading.bean;

public class PageBean{

	private Integer current;//当前页
	private Integer limit;//限制数
	private Integer start;//开始点,例如current = 1,limit=10,那么start = (current - 1) * limit,数据就是从第1~10
	
	public PageBean() {
		
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
}
