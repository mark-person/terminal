/**
 * 
 */
package com.ppx.terminal.mvc.core.tool;

/**
 * @author mark
 * @date 2019年8月15日
 */
public class DbPage {
	private Integer limit;
	private Integer count;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if (limit == null || limit >= 200) {
			this.limit = 50;
		}
		this.limit = limit;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
