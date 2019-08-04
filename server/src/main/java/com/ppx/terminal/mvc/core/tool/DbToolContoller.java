/**
 * 
 */
package com.ppx.terminal.mvc.core.tool;

import java.util.ArrayList;
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
		
		List<Map<String, Object>> tableList = impl.listTable();
		mv.addObject("tableList", tableList);
		
		return mv;
	}
	
	@RequestMapping("/listColumn") @ResponseBody
	public Map<String, Object> listColumn(String tableName) {
		
		List<Map<String, Object>> list = impl.listColumn(tableName);
		
		// 类型;NEW:新的,OLD:旧的
		for (Map<String, Object> map : list) {
			String comment = (String)map.get("comment");
			String value = (String)map.get("value");
			if (comment.contains(";") && comment.contains(",") && comment.contains(":")) {
				String dict = comment.split(";")[1];
				String[] itemArray = dict.split(",");
				List<Map<String, String>> dictList = new ArrayList<Map<String, String>>();
				for (String item : itemArray) {
					String n = item.split(":")[0];
					String v = item.split(":")[1];
					
				}
			}
			
		}
		
		
		
		return ControllerReturn.of("list", list);
	}
	
	@RequestMapping("/listValue") @ResponseBody
	public Map<String, Object> listValue(String tableName, String colVal) {
		
		List<Map<String, Object>> list = impl.listValue(tableName, colVal);
		
		return ControllerReturn.of("list", list);
	}
	
	
	

}
