package com.ppx.terminal.mvc.core.demo;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.page.Page;



@Controller
public class DemoController {
	
	@Autowired
	private DemoImpl impl;
	
	public ModelAndView test(ModelAndView mv) {
		mv.addObject("list", list(new Page(), new Demo()));
		return mv;
	}
	
	public Map<String, Object> list(Page page, Demo pojo) {
		List<Demo> list = impl.list(page, pojo);
		return ControllerReturn.page(page, list);
	}
	 
    public Map<?, ?> insert(Test pojo) {
        return impl.insert(pojo);
    }
    
    public Map<?, ?> get(@RequestParam Integer id) {
        return ControllerReturn.of("pojo", impl.get(id));
    }
    
    public Map<?, ?> update(Demo pojo) {
        return impl.update(pojo);
    }
    
    public Map<?, ?> delete(@RequestParam Integer id) {
        return impl.delete(id);
    }

	
}
