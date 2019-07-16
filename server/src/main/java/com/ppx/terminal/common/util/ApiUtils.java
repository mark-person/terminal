/**
 * 
 */
package com.ppx.terminal.common.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
 * @author mark
 * @date 2019年7月12日
 */
public class ApiUtils {
	public static  String API_SECRET_KEY = "";
	
	public static String getParaSign(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		List<String> paraNameList = new ArrayList<String>();
        Enumeration<String> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
        	String paraName = paraNames.nextElement();
        	if (!"sign".equals(paraName)) {
        		paraNameList.add(paraName);
        	}
		}
        Collections.sort(paraNameList);
        for (String name : paraNameList) {
			String[] vArray = request.getParameterValues(name);
			List<String> vList = Arrays.asList(vArray);
			String v = StringUtils.collectionToDelimitedString(vList, "");
			sb.append(name).append(v);
		}
        return HmacSHA1.genHMAC(sb.toString(), "SIGN_KEY");
	}
	
	public static boolean returnErrorJson(HttpServletResponse response, int code, String msg) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		try (PrintWriter pw = response.getWriter();) {
			pw.write("{\"code\":" + code + ", \"msg\":\"" + msg + "\"}");
		}
		return false;
	}
}
