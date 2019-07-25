/**
 * 
 */
package com.ppx.terminal.mvc.tool.core;

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
@RequestMapping("/coreTool")
public class CoreToolContoller {
	
	@Autowired
	private CoreToolServiceImpl impl; 
	
	
	@RequestMapping("/coreTool")
	public ModelAndView coreTool() {
		ModelAndView mv = new ModelAndView("app/tool/core/coreTool");
		return mv;
	}
	
	@RequestMapping("/listStore") @ResponseBody
	public Map<String, Object> listStore() {
		List<Map<String, Object>> list = impl.listStore();
		return ControllerReturn.of("list", list);
	}
	
	

}
