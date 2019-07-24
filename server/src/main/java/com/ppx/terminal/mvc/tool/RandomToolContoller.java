/**
 * 
 */
package com.ppx.terminal.mvc.tool;

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
		return mv;
	}
	
	@RequestMapping("/getRadomMsg") @ResponseBody
	public Map<String, Object> getRadomMsg() {
		int randomCodeIndex = impl.getRandomCodeIndex();
		Map<String, Object> randomCodeMsg = impl.getRandomCodeMsg();
		return ControllerReturn.of("randomCodeIndex", randomCodeIndex, "randomCodeMsg", randomCodeMsg);
	} 
	
	@RequestMapping("/truncateCode") @ResponseBody
	public Map<String, Object> truncateCode() {
		impl.truncateCode();
		return ControllerReturn.of();
	}
	
	@RequestMapping("/createCode") @ResponseBody
	public Map<String, Object> createCode(Integer total) {
		int num = impl.createCode(total);
		return ControllerReturn.of("num", num);
	}
	
	@RequestMapping("/getRandomCode") @ResponseBody
	public Map<String, Object> getRandomCode() {
			
		String randomCode = impl.getRandomCode();
		
		return ControllerReturn.of("randomCode", randomCode);
	}
	
	
}
