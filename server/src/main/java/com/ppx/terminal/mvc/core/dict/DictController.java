package com.ppx.terminal.mvc.core.dict;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ppx.terminal.common.controller.ControllerReturn;



@Controller
@RequestMapping("/dict")
public class DictController {
	
	@Autowired
	private DictImpl impl;
	
	@GetMapping("/list") @ResponseBody
	public Map<String, Object> list(@RequestParam String[] columnName) {
		
		Map<String, String> map = impl.list(columnName);
		return ControllerReturn.of("dict", map);
		
	}
	 
	

}
