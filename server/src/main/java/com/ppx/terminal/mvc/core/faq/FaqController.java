package com.ppx.terminal.mvc.core.faq;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.page.Page;



@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	private FaqImpl impl;

	@GetMapping("/faqIndex")
	public ModelAndView faqIndex(ModelAndView mv) {
		mv.setViewName("app/core/faq/faqIndex");
		mv.addObject("data", list(new Page(), new Faq()));	
		return mv;
	}
	
	@PostMapping("/list") @ResponseBody
	public Map<String, Object> list(Page page, Faq pojo) {
		
		List<Faq> list = impl.list(page, pojo);
		return ControllerReturn.page(page, list);
	}
	
	@GetMapping("/faq")
	public ModelAndView faq(ModelAndView mv, Integer id) {
		
		mv.setViewName("app/core/faq/faq");
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
	public Map<String, Object> update(Faq pojo, HttpServletRequest request) {
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
