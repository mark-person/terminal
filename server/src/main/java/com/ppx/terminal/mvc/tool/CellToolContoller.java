/**
 * 
 */
package com.ppx.terminal.mvc.tool;

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
@RequestMapping("/cellTool")
public class CellToolContoller {
	
	@Autowired
	private CellToolServiceImpl impl; 
	
	
	@RequestMapping("/cellTool")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/tool/cellTool");
		
		return mv;
	}
	
	
	@RequestMapping("/listCell") @ResponseBody
	public Map<String, Object> listCell() {
		
		List<Map<String, Object>> list = impl.listCell();
		
		return ControllerReturn.of("list", list);
	}
	
	
	
	
	
	
}
