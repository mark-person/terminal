/**
 * 
 */
package com.ppx.terminal.mvc.tool.core;

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
@RequestMapping("/coreTool")
public class CoreToolContoller {
	
	@Autowired
	private CoreToolServiceImpl impl; 
	
	@RequestMapping("/store")
	public ModelAndView store() {
		ModelAndView mv = new ModelAndView("app/tool/core/store");
		return mv;
	}
	
	@RequestMapping("/listStore") @ResponseBody
	public Map<String, Object> listStore() {
		List<Store> list = impl.listStore();
		return ControllerReturn.of("list", list);
	}
	
	@RequestMapping("/insertStore") @ResponseBody
	public Map<String, Object> insertStore(Store store) {
		impl.insertStore(store);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/updateStore") @ResponseBody
	public Map<String, Object> updateStore(Store store) {
		impl.updateStore(store);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/deleteStore") @ResponseBody
	public Map<String, Object> deleteStore(String storeNo) {
		impl.deleteStore(storeNo);
		return ControllerReturn.of();
	}

	
	
	
	@RequestMapping("/locker")
	public ModelAndView locker() {
		ModelAndView mv = new ModelAndView("app/tool/core/locker");
		return mv;
	}
	
	@RequestMapping("/listLocker") @ResponseBody
	public Map<String, Object> listLocker() {
		List<Locker> list = impl.listLocker();
		return ControllerReturn.of("list", list);
	}
	
	@RequestMapping("/insertLocker") @ResponseBody
	public Map<String, Object> insertLocker(Locker locker) {
		impl.insertStore(locker);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/updateLocker") @ResponseBody
	public Map<String, Object> updateLocker(Locker locker) {
		impl.updateStore(locker);
		return ControllerReturn.of();
	}
	
	@RequestMapping("/deleteLocker") @ResponseBody
	public Map<String, Object> deleteLocker(String lockerNo) {
		impl.deleteLocker(lockerNo);
		return ControllerReturn.of();
	}
}
