/**
 * 
 */
package com.ppx.terminal.mvc.core.tool;

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
	private DbToolImpl impl; 
	
	
	@RequestMapping("/dbTool")
	public ModelAndView dbTool() {
		ModelAndView mv = new ModelAndView("app/core/tool/dbTool");
		return mv;
	}
	
	@RequestMapping("/listColumn") @ResponseBody
	public Map<String, Object> listColumn(String tableName) {
		
		List<String> list = impl.listColumn(tableName);
		
		return ControllerReturn.of("list", list);
	}
	
	@RequestMapping("/listValue") @ResponseBody
	public Map<String, Object> listValue(String tableName, String colVal) {
		
		List<Map<String, Object>> list = impl.listValue(tableName, colVal);
		
		return ControllerReturn.of("list", list);
	}
	
	
	

}