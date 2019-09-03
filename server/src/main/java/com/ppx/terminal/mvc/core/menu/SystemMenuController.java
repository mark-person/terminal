package com.ppx.terminal.mvc.core.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/systemMenu")
public class SystemMenuController {
	


	@GetMapping("/systemMenu")
	public ModelAndView systemMenu(ModelAndView mv) {
		mv.setViewName("app/core/menu/systemMenu");
		
		
		return mv;
	}
	
	

}
