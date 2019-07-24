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
	
	@RequestMapping("/cellSet")
	public ModelAndView cellSet() {
		ModelAndView mv = new ModelAndView("app/tool/cellSet");
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
		impl.addColumn(clientId, lockerNumber);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/delRow") @ResponseBody
	public Map<String, Object> delRow(String clientId, String lockerNumber) {
		int r = impl.delRow(clientId, lockerNumber);
		if (r == -1) {
			return ControllerReturn.of(40000, "删除失败，最后一行必须全部为初始状态");
		}
		return ControllerReturn.of();
	}
	
	@RequestMapping("/delColumn") @ResponseBody
	public Map<String, Object> delColumn(String clientId, String lockerNumber) {
		int r = impl.delColumn(clientId, lockerNumber);
		if (r == -1) {
			return ControllerReturn.of(40000, "删除失败，最后一列必须全部为初始状态");
		}
		return ControllerReturn.of();
	}
	
	@RequestMapping("/delCell") @ResponseBody
	public Map<String, Object> delCell(String clientId, String cellId) {
		impl.delCell(clientId, cellId);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/addCell") @ResponseBody
	public Map<String, Object> addCell(String clientId, String cellId) {
		impl.addCell(clientId, cellId);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/editCell") @ResponseBody
	public Map<String, Object> editCell(String clientId, String cellId, String openCode) {
		int r = impl.editCell(clientId, cellId, openCode);
		if (r == -1) {
			return ControllerReturn.of(40000, "开锁码[" + openCode + "]已经存在");
		}
		return ControllerReturn.of();
	}
	
	@RequestMapping("/initCell") @ResponseBody
	public Map<String, Object> initCell(String clientId, String cellId) {
		impl.initCell(clientId, cellId);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/lockCell") @ResponseBody
	public Map<String, Object> lockCell(String clientId, String cellId) {
		impl.lockCell(clientId, cellId);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/unLockCell") @ResponseBody
	public Map<String, Object> unLockCell(String clientId, String cellId) {
		impl.unLockCell(clientId, cellId);
		return ControllerReturn.of();
	}
	
	
	@RequestMapping("/openCell") @ResponseBody
	public Map<String, Object> openCell(String clientId, String cellId) {
		return ControllerReturn.of();
	}

}
