/**
 * 
 */
package com.ppx.terminal.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppx.terminal.common.util.NettyUtils;

/**
 * @author mark
 * @date 2019年6月12日
 */
@RestController
public class TerminalContoller {

	private Logger logger = LoggerFactory.getLogger(TerminalContoller.class);
	@Autowired
	private TerminalServiceImpl impl;

	@RequestMapping({"/terminal/getServerId"})
	public String getServerId(String clientId) {
		return this.impl.getServerId(clientId);
	}
	
	@RequestMapping({"/app/terminal"})
	public TerminalReturn terminalApp1(String clientId) {
		this.logger.info("/app1/terminal");
		String commandContent = "OPEN_DOOR|00000001";
		TerminalReturn r = this.impl.callTerminal(clientId, commandContent);
		r.setMsg(r.getMsg() + "|" + NettyUtils.getServiceId());
		return r;
	}

}
