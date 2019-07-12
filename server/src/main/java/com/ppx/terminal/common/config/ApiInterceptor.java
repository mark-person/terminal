package com.ppx.terminal.common.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//System.out.println(".....ddd:" + request.getParameter("cellCode"));
		
		
		BufferedServletRequestWrapper requestWrapper = new BufferedServletRequestWrapper(request);
		
		String body = readRequestBody(request);
		
		//request.getreq
		
		
		System.out.println("body1111......................:" + "||");
		
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

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	private static String readRequestBody(HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = request.getReader();) {
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		}
		return sb.toString();
	}
}
