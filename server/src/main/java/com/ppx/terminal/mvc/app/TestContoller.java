/**
 * 
 */
package com.ppx.terminal.mvc.app;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.netty.server.session.DeviceSession;
import com.ppx.terminal.netty.server.session.SessionManager;


/**
 * 
 * @author mark
 * @date 2019年6月27日
 */
@Controller
@RequestMapping("/test")
public class TestContoller {
	
	@Autowired
	private TestServiceImpl impl; 
	
	@RequestMapping("/header") @ResponseBody
	public List<String> header(HttpServletRequest request) {
		List<String> returnList = new ArrayList<String>();
		Enumeration<String> nameEnum = request.getHeaderNames();
		while (nameEnum.hasMoreElements()) {
			String name = (String) nameEnum.nextElement();
			String value = request.getHeader(name);
			returnList.add(name + ":" + value);
		}
		return returnList;
	}
	
	// >>>>>>>>>...test...
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/test/test");
		mv.addObject("serviceIdList", impl.listServiceId());
		return mv;
	}
	
	// >>>>>>>>>...test...
	@RequestMapping("/tmp")
	public ModelAndView tmp() {
		ModelAndView mv = new ModelAndView("app/test/tmp");
		return mv;
	}

	@RequestMapping("/listSession") @ResponseBody
	public List<DeviceSession> listSession() {
		return SessionManager.getSingleton().listSession();
	}
	
	@RequestMapping("/listHeart") @ResponseBody
	public List<Map<String, Object>> listHeart(String clientId) {
		return impl.listHeart(clientId);
	}
	
	@RequestMapping("/listLogin") @ResponseBody
	public List<Map<String, Object>> listLogin(String clientId) {
		return impl.listLogin(clientId);
	}
	
	@RequestMapping("/listConnect") @ResponseBody
	public List<Map<String, Object>> listConnect(String serviceId) {
		return impl.listConnect(serviceId);
	}
	
	@RequestMapping("/listCommand") @ResponseBody
	public List<Map<String, Object>> listCommand(String serviceId) {
		return impl.listCommand(serviceId);
	}
}
