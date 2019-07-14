/**
 * 
 */
package com.ppx.terminal.mvc.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.config.plugins.convert.HexConverter;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.comm.CommUtils;
import com.ppx.terminal.comm.SerialReader;

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
	
	
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/test/test");
		return mv;
	}
	
	@RequestMapping("/listSerialPorts") @ResponseBody
	public List<String> listSerialPorts() {
		List<String> returnList = CommUtils.listSerialPorts();
		return returnList;
	}
	
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
	
	// 57 4B 4C 59 09 00 82 01 83 
	
	@RequestMapping("/sendCommandOneWay") @ResponseBody
	public List<String> sendCommandOneWay() {
		synchronized (this) {
			String PORT_NAME = "COM1";
			String commandStr = "57 4B 4C 59 09 00 82 01 83 ";
			
			commandStr = new String(HexConverter.parseHexBinary("57")) 
					+ new String(HexConverter.parseHexBinary("4B"));
			System.out.println(".....commandStr:");
			
			List<String> returnList = new ArrayList<String>();
			SerialPort sp = null;
			try {
				sp = CommUtils.connect(PORT_NAME);
				CommUtils.sendMessageOneWay(sp, commandStr);
			} catch (Exception e) {
				logger.error(e.getMessage());
				returnList.add("error:" + e.getMessage());
				return returnList;
			} finally {
				if (sp != null) {
					sp.close();
				}
			}
			
			returnList.add("PORT_NAME:" + PORT_NAME + "|" + commandStr + "|command success");
			return returnList;
		}
	}
	
	@RequestMapping("/sendCommandTwoWay") @ResponseBody
	public List<String> sendCommandTwoWay() {
		synchronized (this) {
			String PORT_NAME = "COM1";
			String commandStr = "001";
			
			List<String> returnList = new ArrayList<String>();
			SerialPort sp = null;
			
			long t1 = System.nanoTime();
			
			try {
				sp = CommUtils.connect(PORT_NAME);
				CommUtils.sendMessageTwoWay(sp, commandStr);
				
				for (int i = 0; i < 20; i++) {
					Thread.sleep(200);
					if (Strings.isNotEmpty(SerialReader.readerMsg)) {
						returnList.add("PORT_NAME:" + PORT_NAME + "|" + commandStr + "|success:" + SerialReader.readerMsg);
						return returnList;
					}
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				returnList.add("error:" + e.getMessage());
				return returnList;
			} finally {
				if (sp != null) {
					sp.close();
				}
			}
			
			long t = (System.nanoTime() - t1) / (1000000);
			returnList.add("PORT_NAME:" + PORT_NAME + "|" + commandStr + "|time out:" + t);
			return returnList;
		}
	}
	
}
