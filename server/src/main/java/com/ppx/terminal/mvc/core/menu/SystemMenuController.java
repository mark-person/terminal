package com.ppx.terminal.mvc.core.menu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/systemMenu")
public class SystemMenuController {
	


	@GetMapping("/systemMenu")
	public ModelAndView systemMenu(ModelAndView mv) {
		mv.setViewName("app/core/menu/systemMenu");
		
		
		return mv;
	}
	
	

}
