/**
 * 
 */
package com.ppx.terminal.mvc.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.comm.CommUtils;

import gnu.io.SerialPort;

/**
 * 
 * @author mark
 * @date 2019年7月4日
 */
@Controller
@RequestMapping("/test")
public class TestContoller {
	private static Logger logger = LoggerFactory.getLogger(TestContoller.class);
	
	@Autowired
	private TestServiceImpl impl;
	// private Logger logger = LoggerFactory.getLogger(TestContoller.class);
	
	// >>>>>>>>>...test...
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/test/test");
		return mv;
	}
	
	// >>>>>>>>>...test...
	@RequestMapping("/listSerialPorts") @ResponseBody
	public List<String> listSerialPorts() {
		List<String> returnList = impl.listSerialPorts();
		return returnList;
	}
	
	// >>>>>>>>>...test...
	@RequestMapping("/testConnect") @ResponseBody
	public List<String> testConnect() {
		String PORT_NAME = "COM1";
		
		List<String> returnList = new ArrayList<String>();
		SerialPort sp  = null;
		try {
			sp = CommUtils.connect(PORT_NAME);
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnList.add("error:" + e.getMessage());
			return returnList;
		} finally {
			if (sp != null) {
				sp.close();
			}
		}
		returnList.add("PORT_NAME:" + PORT_NAME + "|connect success");
		return returnList;
	}
	
	@RequestMapping("/sendCommand") @ResponseBody
	public List<String> sendCommand() {
		String PORT_NAME = "COM1";
		String commandStr = "001";
		
		List<String> returnList = new ArrayList<String>();
		SerialPort sp = null;
		try {
			sp = CommUtils.connect(PORT_NAME);
			CommUtils.sendMessage(sp, commandStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnList.add("error:" + e.getMessage());
			return returnList;
		} finally {
			if (sp != null) {
				sp.close();
			}
		}
		
		returnList.add("PORT_NAME:" + PORT_NAME + "|" + commandStr + "|success");
		return returnList;
	}
	
	
	

}
