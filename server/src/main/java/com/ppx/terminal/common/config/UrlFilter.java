/**
 * 
 */
package com.ppx.terminal.common.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

// @Component
@WebFilter(urlPatterns = {"/testApi/test"}, filterName = "UrlFilter")
public class UrlFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
    	
    	if (request instanceof HttpServletRequest) {
    		  
        
           // System.out.println("99999999990:" + ((HttpServletRequest)request).getRequestURI());
            
            if (((HttpServletRequest)request).getRequestURI().startsWith("/api/")) {
                ServletRequest requestWrapper = new BufferedServletRequestWrapper((HttpServletRequest) request);
            	 chain.doFilter(requestWrapper, response);
            }
            else {
            	chain.doFilter(request, response);
            }
           
        } else {
            chain.doFilter(request, response);
        }
    }
 
    @Override
    public void destroy() {
 
    }
}
