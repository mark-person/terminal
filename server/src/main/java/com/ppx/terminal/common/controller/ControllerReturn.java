/**
 * 
 */
package com.ppx.terminal.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppx.terminal.common.page.Page;


/**
 * 
 * @author mark
 * @date 2019年7月16日
 */
public class ControllerReturn {
	
	private static final String CODE_TITLE = "code";
	
	private static final String MSG_TITLE = "msg";
	
	private static Map<String, Object> SUCCESS = new LinkedHashMap<String, Object>(2);
	static {
		SUCCESS.put(CODE_TITLE, 0);
		SUCCESS.put(MSG_TITLE, "SUCCESS");
	}
	
	private static Map<String, Object> ERROR = new LinkedHashMap<String, Object>(2);
	static {
		ERROR.put(CODE_TITLE, 50000);
		ERROR.put(MSG_TITLE, "ERROR");
	}
	
	public static Map<String, Object> of() {
		return SUCCESS;
	}
	
	public static Map<String, Object> error() {
		return ERROR;
	}

	public static Map<String, Object> exists(int result, String msg) {
		if (result == 0) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>(2);
			returnMap.put(CODE_TITLE, "40000");
			returnMap.put(MSG_TITLE, msg);
			return returnMap;
		}
		else {
			return SUCCESS;
		}
	}
	
	public static Map<String, Object> page(Page page, List<?> list) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>(4);
		returnMap.putAll(SUCCESS);
		returnMap.put("page", page);
		returnMap.put("list", list);
		return returnMap;
	}
	
	public static Map<String, Object> page(Page page, List<Object> list, String key, Object value) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>(5);
		returnMap.putAll(SUCCESS);
		returnMap.put("page", page);
		returnMap.put("list", list);
		returnMap.put(key, value);
		return returnMap;
	}
	
	
	// 业务自定义:40000~40099
	public static Map<String, Object> of(int code, String msg) {
		if (code >= 40000 && code <= 40099) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>(2);
			returnMap.put(CODE_TITLE, code);
			returnMap.put(MSG_TITLE, msg);
			return returnMap;
		}
		else {
			throw new RuntimeException("code must be from 40000-40099, code:" + code);
		}
	}
	
	// 业务自定义:50050~50099
	public static Map<String, Object> error(int code, String msg) {
		if (code >= 50050 && code <= 50099) {
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>(2);
			returnMap.put(CODE_TITLE, code);
			returnMap.put(MSG_TITLE, msg);
			return returnMap;
		}
		else {
			throw new RuntimeException("code must be from 50050-50099, code:" + code);
		}
	}
	
	public static Map<String, Object> of(String key, Object value) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>(3);
		returnMap.putAll(SUCCESS);
		returnMap.put(key, value);
		return returnMap;
	}
	
	public static Map<String, Object> of(String k1, Object v1, String k2, Object v2) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>(4);
		returnMap.putAll(SUCCESS);
		returnMap.put(k1, v1);
		returnMap.put(k2, v2);
		return returnMap;
	}
	
	public static Map<String, Object> of(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>(5);
		returnMap.putAll(SUCCESS);
		returnMap.put(k1, v1);
		returnMap.put(k2, v2);
		returnMap.put(k3, v3);
		return returnMap;
	}
	
	
	
	
	
	
	/**
         * 返回错误(JSON格式)
     * 
     * @param response
     * @param errorCode
     * @param errorInfo
     */
    public static boolean errorJson(HttpServletResponse response, int code, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, Object> map = error(code, msg);
        try (PrintWriter printWriter = response.getWriter()) {
            String returnJson = new ObjectMapper().writeValueAsString(map);
            printWriter.write(returnJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
		
}
