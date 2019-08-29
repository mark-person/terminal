package com.ppx.terminal.mvc.core.faq;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	private FaqImpl impl;
	
	@GetMapping("/faq")
	public ModelAndView demo(ModelAndView mv, Integer id) {
		
		mv.setViewName("app/core/demo/faq");
		if (id != null) {
			Faq pojo = impl.get( id);
			mv.addObject("pojo", pojo);
		}
		
		return mv;
	}
	
	@PostMapping("/insert") @ResponseBody
	public Map<String, Object> insert(Faq pojo, HttpServletRequest request) {
		
		String[] sub = request.getParameterValues("sub");
		if (sub != null && sub.length == 1) {
			pojo.setSub(Arrays.asList(sub[0]));
		}
		String[] answerSub = request.getParameterValues("answerSub");
		if (answerSub != null && answerSub.length == 1) {
			pojo.setAnswerSub(Arrays.asList(answerSub[0]));
		}
		return impl.insert(pojo);
	}
	
	@PostMapping("/update") @ResponseBody
	public Map<String, Object> update(@RequestParam Faq pojo, HttpServletRequest request) {
		String[] sub = request.getParameterValues("sub");
		if (sub != null && sub.length == 1) {
			pojo.setSub(Arrays.asList(sub[0]));
		}
		String[] answerSub = request.getParameterValues("answerSub");
		if (answerSub != null && answerSub.length == 1) {
			pojo.setAnswerSub(Arrays.asList(answerSub[0]));
		}
		return impl.update(pojo);
	}

}
