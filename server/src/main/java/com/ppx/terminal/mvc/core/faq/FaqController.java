package com.ppx.terminal.mvc.core.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	private FatImpl impl;
	
	@GetMapping("/faq")
	public ModelAndView demo(ModelAndView mv) {
		mv.setViewName("app/core/demo/faq");
		
		impl.test();
		return mv;
	}
	


}
