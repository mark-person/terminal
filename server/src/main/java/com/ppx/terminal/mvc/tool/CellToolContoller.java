/**
 * 
 */
package com.ppx.terminal.mvc.tool;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.terminal.common.controller.ControllerReturn;



/**
 * 
 * @author mark
 * @date 2019年7月18日
 */
@Controller
@RequestMapping("/cellTool")
public class CellToolContoller {
	
	@Autowired
	private CellToolServiceImpl impl; 
	
	
	@RequestMapping("/cellTool")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("app/tool/cellTool");
		
		return mv;
	}
	
	
	@RequestMapping("/listCell") @ResponseBody
	public Map<String, Object> listCell(String clientId, String lockerNumber) {
		
		List<Map<String, Object>> list = impl.listCell(clientId, lockerNumber);
		List<String> rowList = impl.listRowNumber(clientId, lockerNumber);
		List<String> columnList = impl.listColumnNumber(clientId, lockerNumber);
		
		return ControllerReturn.of("list", list, "rowList", rowList, "columnList", columnList);
	}
	
	
	@RequestMapping("/addRow") @ResponseBody
	public Map<String, Object> addRow(String clientId, String lockerNumber) {
		
		impl.addRow(clientId, lockerNumber);
		
		return ControllerReturn.of();
	}
	
	@RequestMapping("/addColumn") @ResponseBody
	public Map<String, Object> addColumn(String clientId, String lockerNumber) {
		
		impl.addRow(clientId, lockerNumber);
		
		return ControllerReturn.of();
	}
	
	
	
}
