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

@Component
@WebFilter(urlPatterns = "/api/*")
public class UrlFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	System.out.println("000000000000000");
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
    	System.out.println("xxxxx001:" + request.getParameter("cellCode"));
    	if (request instanceof HttpServletRequest) {
            ServletRequest requestWrapper = new BufferedServletRequestWrapper((HttpServletRequest) request);
            System.out.println("xxxxx002:" + requestWrapper.getParameter("cellCode"));
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
 
    @Override
    public void destroy() {
 
    }
}
