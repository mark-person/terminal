package com.ppx.terminal.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.util.ApiUtils;

public class ApiInterceptor implements HandlerInterceptor {
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	//  accessKey=&timestamp=&sign=
    	
    	// accessKey&secretKey
    	// 请求携带参数accessKey和sign，只有拥有合法的身份accessKey和正确的签名sign才能放行
    	// timestamp+nonce方案
    	
    	// ApiUtils.API_SECRET_KEY;
    	
        
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
