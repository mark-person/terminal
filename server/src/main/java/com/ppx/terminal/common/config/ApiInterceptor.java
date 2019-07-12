package com.ppx.terminal.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiInterceptor implements HandlerInterceptor {
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
    	// AccessKey&SecretKey （开放平台）
    	// 请求携带参数AccessKey和Sign，只有拥有合法的身份AccessKey和正确的签名Sign才能放行
    	// timestamp+nonce方案
    	
        
    	System.out.println("...................xxx:" + request.getServerName());
    	
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
