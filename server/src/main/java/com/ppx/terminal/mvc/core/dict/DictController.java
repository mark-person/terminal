package com.ppx.terminal.mvc.core.dict;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@RequestMapping("/dict")
public class DictController {
	
	@Autowired
	private DictImpl impl;
	
	@GetMapping("/list") @ResponseBody
	public void list(HttpServletResponse response, @RequestParam String[] code) throws Exception {
		
		
		response.setHeader("content-type", "application/javascript;charset=utf-8");
		response.setHeader("cache-control", "max-age=10");
		
		Map<String, Object> map = impl.list(code);
		
		try (PrintWriter pw = response.getWriter()){
			String json = new ObjectMapper().writeValueAsString(map);
			pw.write("var dict = " + json);
			response.flushBuffer();
		}
		
	}
	 
	

}
