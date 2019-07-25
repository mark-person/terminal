/**
 * 
 */
package com.ppx.terminal.mvc.tool.core;

/**
 * @author mark
 * @date 2019年7月25日
 */
public class Locker {
	private String lockerNo;
	private String lockerDesc;
	
	private String initLockerNo;
	

	public String getInitLockerNo() {
		return initLockerNo;
	}

	public void setInitLockerNo(String initLockerNo) {
		this.initLockerNo = initLockerNo;
	}

	public String getLockerNo() {
		return lockerNo;
	}

	public void setLockerNo(String lockerNo) {
		this.lockerNo = lockerNo;
	}

	public String getLockerDesc() {
		return lockerDesc;
	}

	public void setLockerDesc(String lockerDesc) {
		this.lockerDesc = lockerDesc;
	}

}
