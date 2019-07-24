/**
 * 
 */
package com.ppx.terminal.mvc.tool;

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
 * @date 2019年6月27日
 */
@Controller
@RequestMapping("/randomTool")
public class RandomToolContoller {
	
	@Autowired
	private RandomToolServiceImpl impl; 
	
	@RequestMapping("/randomTool")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/tool/randomTool");
		
		mv.addObject("randomCodeIndex", impl.getRandomCodeIndex());
		mv.addObject("randomCodeMsg", impl.getRandomCodeMsg());
		
		return mv;
	}
	
	@RequestMapping("/truncateCode") @ResponseBody
	public Map<String, Object> truncateCode() {
		impl.truncateCode();
		return ControllerReturn.of();
	}
	
	@RequestMapping("/createCode") @ResponseBody
	public Map<String, Object> createCode() {
		impl.createCode();
		return ControllerReturn.of();
	}
	
	@RequestMapping("/getCode") @ResponseBody
	public List<String> getCode() {
			
		List<String> listCode = new ArrayList<String>();
		// String code = impl.getCode();
		
		for (int i = 0; i < 100; i++) {
			listCode.add(impl.getRandomCode());
		}
		
		return listCode;
	}
	
	
}
