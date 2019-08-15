/**
 * 
 */
package com.ppx.terminal.mvc.core.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
		
		List<Map<String, Object>> list = impl.listColumnMsg(tableName);
		
		List<Map<String, Object>> columnlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> typeMap = new HashMap<String, Object>();
		Map<String, Object> dictMap = new HashMap<String, Object>();
		List<String> singleList = new ArrayList<String>();
		List<String> chainList = new ArrayList<String>();
		
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
			
			if (comment.split("select").length == 3) {
				chainList.add(value);
			}
			else if (comment.contains("select")) {
				singleList.add(value);
			}
			
			// 栏位信息
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("value", value);
			newMap.put("text", (String)map.get("text"));
			newMap.put("key", (String)map.get("COLUMN_KEY"));
			newMap.put("null", (String)map.get("IS_NULLABLE"));
			columnlist.add(newMap);
		}
		
		return ControllerReturn.of("list", columnlist, "dict", dictMap, "type", typeMap, "single", singleList, "chain", chainList);
	}
	
	@RequestMapping("/queryData") @ResponseBody
	public Map<String, Object> queryData(String tableName, String colVal, String qKey, String qOperator, String qValue) {
		
		List<Map<String, Object>> list = impl.queryData(tableName, colVal, qKey, qOperator, qValue);
		
		return ControllerReturn.of("list", list);
	}
	
	// >>>>>>>>>>>>>>>>>>
	@RequestMapping("/listSingleData") @ResponseBody
	public Map<String, Object> listSingleData(String tableName, String columnName, String queryVal) {
		Map<String, Object> map = impl.listSingleData(tableName, columnName, queryVal);
		return ControllerReturn.of(map);
	}
	
	// >>>>>>>>>>>>>>>>>>
	@PostMapping("/edit") @ResponseBody
	public Map<String, Object> edit(HttpServletRequest request) {
		Map<String, Object> r = impl.update(request.getParameterMap());
		return r;
	}
	
	
	// >>>>>>>>>...chain
	// >>>>>>>>>>>>>>>>>>
	@RequestMapping("/listChainData") @ResponseBody
	public Map<String, Object> listChainData(String tableName, String columnName) {
		Map<String, Object> map = impl.listChainData(tableName, columnName);
		return ControllerReturn.of(map);
	}
	
	@RequestMapping("/listChainSecondData") @ResponseBody
	public Map<String, Object> listChainSecondData(String tableName, String columnName, Integer parentId) {
		Map<String, Object> map = impl.listChainSecondData(tableName, columnName, parentId);
		return ControllerReturn.of(map);
	}
	
	

}
