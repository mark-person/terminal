/**
 * 
 */
package com.ppx.terminal.mvc.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mark
 * @date 2019年7月12日
 */

@Controller
@RequestMapping("/api/cell")
public class ApiCellController {
	
	@Autowired
	private ApiCellServiceImpl impl;
	
	
	
	@RequestMapping("/getCellBit") @ResponseBody
	public Map<String, String> getCellBit(@RequestParam(required=true) String cellCode) {
		Map<String, String> returnMap = new HashMap<String, String>(); 
		returnMap.put("code", impl.getCellBit(cellCode));
		return returnMap;
	}
	
	
}
