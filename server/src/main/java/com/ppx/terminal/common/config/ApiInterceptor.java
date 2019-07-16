package com.ppx.terminal.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.util.HmacSHA1;

public class ApiInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		String sign = request.getParameter("sign");
		System.out.println(".........requsign:" + sign);
		
		String paraSign = getParaSign(request);
		System.out.println(".........paraSign:" + paraSign);
        
        
        
		
		
		// accessKey=&timestamp=&sign=
//		String accessKey = request.getParameter("accessKey");
//		String timestamp = request.getParameter("timestamp");
//		String sign = request.getParameter("sign");
		// System.out.println("queryString:" + request.getp);

		// accessKey&secretKey
		// 请求携带参数accessKey和sign，只有拥有合法的身份accessKey和正确的签名sign才能放行
		// timestamp+nonce方案

		// ApiUtils.API_SECRET_KEY;

		

		return true;
	}
	
	
	private String getParaSign(HttpServletRequest request) {
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

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	
}
