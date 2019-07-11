/**
 * 
 */
package com.ppx.terminal.mvc.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author mark
 * @date 2019年6月27日
 */
@Controller
@RequestMapping("/tool")
public class ToolContoller {
	
	@Autowired
	private ToolServiceImpl impl; 
	

	@RequestMapping("/createCode") @ResponseBody
	public String createCode() {
		
		impl.createCode();
		
		return "OK";
	}
	
	@RequestMapping("/getCode") @ResponseBody
	public List<String> getCode() {
			
		List<String> listCode = new ArrayList<String>();
		// String code = impl.getCode();
		
		for (int i = 0; i < 100; i++) {
			listCode.add(impl.getCode());
		}
		
		return listCode;
	}
	
	
}
