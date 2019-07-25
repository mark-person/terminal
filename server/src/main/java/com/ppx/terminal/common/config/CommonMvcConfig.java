package com.ppx.terminal.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ppx.terminal.common.api.ApiInterceptor;




/**
 * MVC配置，由项目创建配置
 * @author mark
 * @date 2018年12月15日
 */

/**
 * 
 * @author mark
 * @date 2019年6月27日
 */
@Configuration
public class CommonMvcConfig extends WebMvcConfigurationSupport {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);	      
		MappingJackson2HttpMessageConverter m = new MappingJackson2HttpMessageConverter();
		m.setObjectMapper(new ObjectMapperCustomer());
        converters.add(m);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/apiV1/**");
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
    }
	
}  
