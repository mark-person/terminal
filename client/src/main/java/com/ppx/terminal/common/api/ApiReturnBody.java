/**
 * 
 */
package com.ppx.terminal.common.api;

import java.util.Map;

/**
 * @author mark
 * @date 2019年7月16日
 */
public class ApiReturnBody {
	
	private Map<String, Object> returnBody;
	
	public ApiReturnBody(Map<String, Object> returnBody) {
		this.returnBody = returnBody;
	}
	
	public int getCode() {
		return (int)returnBody.get("code");
	}
	
	public String getMsg() {
		return (String)returnBody.get("msg");
	}
	
	public Object get(String key) {
		return returnBody.get(key);
	}
	
	public String toString() {
		if (returnBody == null) return "";
		else return returnBody.toString();
	}
	
	
	
	
}
