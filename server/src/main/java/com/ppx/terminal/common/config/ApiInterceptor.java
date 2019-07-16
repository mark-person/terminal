package com.ppx.terminal.common.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
		request.getParameterMap().forEach((name, v) -> {
			
			System.out.println("name:" + name + "|" + Arrays.asList(v));
		});
		
		
		
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

	
}
