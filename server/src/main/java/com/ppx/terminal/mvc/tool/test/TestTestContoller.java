/**
 * 
 */
package com.ppx.terminal.mvc.tool.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;



/**
 * 
 * @author mark
 * @date 2019年7月18日
 */
@Controller
@RequestMapping("/testTest")
public class TestTestContoller {
	
	
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/tool/test/test");
		return mv;
	}
	
	@RequestMapping("/json") @ResponseBody
	public Map<String, Object> json() {
		int i = 1 / 0;
		return ControllerReturn.of();
	}
	
	

}
