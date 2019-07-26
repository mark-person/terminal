/**
 * 
 */
package com.ppx.terminal.mvc.tool.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("a", "a1");
		m1.put("b", "b1");
		list.add(m1);
		
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("a", "a2");
		m2.put("b", "b2");
		list.add(m2);
		
		
		return ControllerReturn.of("list", list);
	}
	
	

}
