package com.ppx.terminal.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.util.ApiReturn;



@ControllerAdvice
public class CustomExceptionHandler implements HandlerExceptionResolver {

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception exception) {
        
        System.err.println("CustomExceptionHandler error:" + exception.getMessage());
    	
        ApiReturn.errorJson(response, 50050, exception.getMessage());
        
        
        return null;
    }

}
