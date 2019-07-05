/**
 * 
 */
package com.ppx.terminal.mvc.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author mark
 * @date 2019年6月27日
 */
@Controller
public class IndexContoller {
	
	@RequestMapping("/") @ResponseBody
	public String index() {
		return "OK";
	}
	
	
}
