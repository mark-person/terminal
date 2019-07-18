/**
 * 
 */
package com.ppx.terminal.mvc.api.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ppx.terminal.common.api.ApiUtils;
import com.ppx.terminal.common.controller.ControllerReturn;

/**
 * @author mark
 * @date 2019年7月12日
 */

@Controller
@RequestMapping(ApiUtils.API_V1_URL + "/cell")
public class ApiCellController {
	
	@Autowired
	private ApiCellServiceImpl impl;
	
	@RequestMapping("/getCellBit") @ResponseBody
	public Map<String, Object> getCellBit(@RequestParam(required=true) String cellCode) {
		
		// int i = 1 / 0;
		
		return ControllerReturn.of("cellCode", impl.getCellBit(cellCode));
	}
	
	
}
