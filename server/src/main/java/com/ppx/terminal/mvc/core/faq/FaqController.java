package com.ppx.terminal.mvc.core.faq;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.page.Page;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	private FaqImpl impl;
	
	@GetMapping("/faqIndex")
	public ModelAndView faqIndex(ModelAndView mv) {
		mv.setViewName("app/core/faq/faqIndex");
		mv.addObject("list", list(new Page(), new Faq()));
		
		// 的sql里转JSON_OBJECT
		String s = "{\"B\":1,\"A\":2}";
		mv.addObject("faqCategory", JSONObject.fromObject(s));
		
		System.out.println("99999999:" + JSONObject.fromObject(s));
		
		return mv;
	}
	
	@PostMapping("/list") @ResponseBody
	public Map<String, Object> list(Page page, Faq pojo) {
		
		
		try {
			String jsonStr = "{brain:'头脑风暴',tech:'技术',busi:'业务'}";
			
			
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			// JsonNode jn = new ObjectMapper().readTree(jsonStr);
			List<String> list = IteratorUtils.toList(jsonObject.keys());
			
			
			System.out.println("9999999:");
			System.out.println("..........jn:" + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
