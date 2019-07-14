package com.ppx.terminal.common.config;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		 System.out.println(".....1111:" + request.getInputStream());
		
		 RequestFacade rf = (RequestFacade)request;
		 // rf.setBuffer(getByte(request));
		 
		 System.out.println(".....222:" + rf.getBuffer());
		//BufferedServletRequestWrapper requestWrapper = new BufferedServletRequestWrapper(request);
		
		String body = new String(rf.getBuffer());
		
		//request.getreq
		
		
		System.out.println("body1:......................:" + body + "||");
		
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
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1) {
		    buf.write((byte) result);
		    result = bis.read();
		}
		return buf.toString();
	}
	
	private static byte[] getByte(HttpServletRequest request) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1) {
		    buf.write((byte) result);
		    result = bis.read();
		}
		return buf.toByteArray();
	}
}
