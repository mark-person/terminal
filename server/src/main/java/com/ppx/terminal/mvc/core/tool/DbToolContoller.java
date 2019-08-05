/**
 * 
 */
package com.ppx.terminal.mvc.core.tool;

import java.util.ArrayList;
import java.util.HashMap;
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
		
		
		Map<String, Object> typeMap = new HashMap<String, Object>();
		Map<String, Object> dictMap = new HashMap<String, Object>();
		
		for (Map<String, Object> map : list) {
			String comment = (String)map.get("comment");
			String value = (String)map.get("value");
			if (comment.contains(";") && comment.contains(",") && comment.contains(":")) {
				String dict = comment.split(";")[1];
				String[] itemArray = dict.split(",");
				List<Map<String, String>> dictList = new ArrayList<Map<String, String>>();
				for (String item : itemArray) {
					Map<String, String> itemMap = new HashMap<String, String>();
					String v = item.split(":")[0];
					String t = item.split(":")[1];
					itemMap.put("v", v);
					itemMap.put("t", t);
					dictList.add(itemMap);
				}
				dictMap.put(value, dictList);
			}
			typeMap.put(value, (String)map.get("DATA_TYPE"));
		}
		
		
		
		return ControllerReturn.of("list", list, "dict", dictMap, "type", typeMap);
	}
	
	@RequestMapping("/listValue") @ResponseBody
	public Map<String, Object> listValue(String tableName, String colVal) {
		
		List<Map<String, Object>> list = impl.listValue(tableName, colVal);
		
		return ControllerReturn.of("list", list);
	}
	
	
	

}
