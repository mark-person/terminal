/**
 * 
 */
package com.ppx.terminal.mvc.core.demo;

import java.util.Date;

import com.ppx.terminal.common.jdbc.annotation.Table;

/**
 * @author mark
 * @date 2019年7月29日
 */
@Table("core_demo")
public class Demo {
	private Integer demoId;
	private String demoName;
	private String demoDate;
	private Double demoValue;
	private Integer demo_type;
	private Date created;
	
	public Integer getDemoId() {
		return demoId;
	}
	public void setDemoId(Integer demoId) {
		this.demoId = demoId;
	}
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
	public String getDemoDate() {
		return demoDate;
	}
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	public Double getDemoValue() {
		return demoValue;
	}
	public void setDemoValue(Double demoValue) {
		this.demoValue = demoValue;
	}
	public Integer getDemo_type() {
		return demo_type;
	}
	public void setDemo_type(Integer demo_type) {
		this.demo_type = demo_type;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	

}
