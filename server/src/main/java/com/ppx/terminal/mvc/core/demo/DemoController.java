package com.ppx.terminal.mvc.core.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.page.Page;



@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@Autowired
	private DemoImpl impl;
	
	@GetMapping("/demo")
	public ModelAndView demo(ModelAndView mv) {
		mv.setViewName("app/core/demo/demo");
		mv.addObject("list", list(new Page(), new Demo()));
		return mv;
	}
	
	@PostMapping("/list") @ResponseBody
	public Map<String, Object> list(Page page, Demo pojo) {
		try {
			// Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		List<Demo> list = impl.list(page, pojo);
		return ControllerReturn.page(page, list);
	}
	 
	@PostMapping("/insert") @ResponseBody
    public Map<?, ?> insert(Demo pojo) {
        return impl.insert(pojo);
    }
    
	@PostMapping("/get") @ResponseBody
    public Map<?, ?> get(@RequestParam Integer id) {
        return ControllerReturn.of("pojo", impl.get(id));
    }
    
    @PostMapping("/update") @ResponseBody
    public Map<?, ?> update(Demo pojo) {
        return impl.update(pojo);
    }
    
    @PostMapping("/delete") @ResponseBody
    public Map<?, ?> delete(@RequestParam Integer id) {
        return impl.delete(id);
    }

}
