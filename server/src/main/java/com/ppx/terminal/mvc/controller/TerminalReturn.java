/**
 * 
 */
package com.ppx.terminal.mvc.controller;

/**
 * @author mark
 * @date 2019年6月27日
 */
public class TerminalReturn {
	private int code;
	
	private String msg;
	
	public TerminalReturn(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
