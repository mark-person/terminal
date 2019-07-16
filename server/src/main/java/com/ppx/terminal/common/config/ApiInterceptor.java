package com.ppx.terminal.common.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.util.ApiUtils;

public class ApiInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String accessKey = request.getParameter("accessKey");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		
		if (Strings.isEmpty(accessKey)) {
			return ApiUtils.returnErrorJson(response, 40001, "accessKey is empty");
		}
		if (Strings.isEmpty(timestamp)) {
			return ApiUtils.returnErrorJson(response, 40002, "timestamp is empty");
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date timestampDate = sdf.parse(timestamp);
				long differTime = System.currentTimeMillis() - timestampDate.getTime();
				// 15 minute
				if (differTime > 15 * 60 * 1000) {
					return ApiUtils.returnErrorJson(response, 40003, "timestamp more than 15 minutes");
				}
			} catch (Exception e) {
				return ApiUtils.returnErrorJson(response, 40004, "timestamp format exception");
			}
		}
		if (Strings.isEmpty(sign)) {
			return ApiUtils.returnErrorJson(response, 40005, "sign is empty");
		}
		
		if (!sign.equals(ApiUtils.getParaSign(request))) {
			return ApiUtils.returnErrorJson(response, 40006, "sign error");
		}
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	
}
