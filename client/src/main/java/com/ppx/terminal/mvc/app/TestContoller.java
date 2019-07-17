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
	
	
	
	
	
	private String parseHex(String str) {
		StringBuilder sb = new StringBuilder();
		String[] s = str.split(" ");
		for (String c : s) {
			sb.append(new String(HexConverter.parseHexBinary(c)));
		}
		return sb.toString();
	}
	
	@RequestMapping("/close") @ResponseBody
	public String close() {
		String msg = "isNull";
		if (SerialPortUtils.SERIAL_PORT != null) {
			SerialPortUtils.SERIAL_PORT.close();
			SerialPortUtils.SERIAL_PORT = null;
			msg = "close";
		}
		
		return msg;
	}
	
	
	// 57 4B 4C 59 09 00 82 01 83 
	@RequestMapping("/sendCommandOneWay") @ResponseBody
	public List<String> sendCommandOneWay() {
		synchronized (this) {
			System.out.println("-----------------begin");
			
			String PORT_NAME = "COM1";
			// 57 4B 4C 59 09 00 82 01 83 
			// 57 4B 4C 59 09 00 82 01 83 
			String commandStr = "57 4B 4C 59 09 00 82 01 83 ";
			
			commandStr = "574B4C590900820183";
			
			byte[] b = new byte[9];
			b[0] = HexConverter.parseHexBinary("57")[0];
			b[1] = HexConverter.parseHexBinary("4B")[0];
			b[2] = HexConverter.parseHexBinary("4C")[0];
			b[3] = HexConverter.parseHexBinary("59")[0];
			
			b[4] = HexConverter.parseHexBinary("09")[0];
			b[5] = HexConverter.parseHexBinary("00")[0];
			b[6] = HexConverter.parseHexBinary("82")[0];
			b[7] = HexConverter.parseHexBinary("01")[0];
			
			b[8] = HexConverter.parseHexBinary("83")[0];
			
			byte[] newB = HexConverter.parseHexBinary(commandStr);
			
			
			
			// System.out.println("9999999:" + HexConverter.parseHexBinary("82").length);
			
			commandStr = parseHex(commandStr);
			
//			commandStr = new String(HexConverter.parseHexBinary("57")) 
//					+ new String(HexConverter.parseHexBinary("4B"));
			
			long t = System.currentTimeMillis();
			System.out.println(".....commandStr:" + commandStr);
			
			List<String> returnList = new ArrayList<String>();
			try {
				SerialPortUtils.SERIAL_PORT = CommUtils.connect(PORT_NAME);
				
				System.out.println("----------middle:" + SerialPortUtils.SERIAL_PORT);
				
				CommUtils.sendMessageOneWay(SerialPortUtils.SERIAL_PORT, newB);
			} catch (Exception e) {
				logger.error(e.getMessage());
				returnList.add("error:" + e.getMessage());
			} finally {
				if (SerialPortUtils.SERIAL_PORT != null) {
					SerialPortUtils.SERIAL_PORT.close();
					SerialPortUtils.SERIAL_PORT = null;
				}
			}
			
			if (returnList.isEmpty()) {
				returnList.add("PORT_NAME:" + PORT_NAME + "|" + commandStr + "|command success");
			}
			
			System.out.println("---------finish:" + (System.currentTimeMillis() - t));
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
