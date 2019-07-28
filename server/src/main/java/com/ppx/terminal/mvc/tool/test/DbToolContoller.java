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
@RequestMapping("/dbTool")
public class DbToolContoller {
	
	@Autowired
	private DbToolServiceImpl impl; 
	
	@RequestMapping("/grid")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/tool/test/grid");
		return mv;
	}
	
	@RequestMapping("/dbTool")
	public ModelAndView dbTool() {
		ModelAndView mv = new ModelAndView("app/tool/test/dbTool");
		return mv;
	}
	
	@RequestMapping("/listColumn") @ResponseBody
	public Map<String, Object> listColumn(String tableName) {
		
		List<String> list = impl.listColumn(tableName);
		
		return ControllerReturn.of("list", list);
	}
	
	@RequestMapping("/listValue") @ResponseBody
	public Map<String, Object> listValue(String sql) {
		
		List<Map<String, Object>> list = impl.listValue(sql);
		
		return ControllerReturn.of("list", list);
	}
	
	
	

}
