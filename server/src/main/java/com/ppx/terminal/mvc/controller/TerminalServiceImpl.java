package com.ppx.terminal.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ppx.terminal.common.jdbc.MyDaoSupport;
import com.ppx.terminal.common.util.NettyUtils;
import com.ppx.terminal.netty.server.session.DeviceSession;
import com.ppx.terminal.netty.server.session.SessionManager;

@Service
public class TerminalServiceImpl extends MyDaoSupport {
	
	private Logger logger = LoggerFactory.getLogger(TerminalContoller.class);
	
	public String getServerId(String clientId) {
	    String sql = "select ifnull((select service_Id from ter_client_login_service where client_id = ?), '') service_Id";
	    return (String)getJdbcTemplate().queryForObject(sql, String.class, new Object[] { clientId });
	}
	
	private Map<String, Boolean> requestingMap = new HashMap<String, Boolean>();
	
	public TerminalReturn callTerminal(String clientId, String commandContent) {
		
		synchronized (clientId) {
			Boolean inRequesting = requestingMap.get(clientId);
			if (inRequesting != null && inRequesting == true) {
				return new TerminalReturn(4000, "clientId:" + clientId + " in requesting.");
			}
			requestingMap.put(clientId, true);
		}
		
		long t = System.nanoTime();
		
		insertLogCommand(clientId, commandContent, null);
		int commandId = getLastInsertId();
		
		try {
			DeviceSession deviceSession = SessionManager.getSingleton().getDeviceSession(clientId);
			
			if (deviceSession == null) {
				logger.info("............deviceSession is null");
				updateLogCommandError(commandId, "deviceSession is null");
				return new TerminalReturn(4001, "deviceSession is null");
			}
			else {
				logger.info("send command");
				deviceSession.getChannel().writeAndFlush(commandContent);
				deviceSession.setReturnValue(null);
				for (int i = 0; i < 20; i++) {
					Thread.sleep(300);
					String returnValue = deviceSession.getReturnValue();
					if (returnValue != null) {
					    logger.info("returnValue:" + returnValue);
						deviceSession.setReturnValue(null);
						updateLogCommandReturn(commandId, returnValue);
						return new TerminalReturn(2000, "execute success:" + (System.nanoTime() - t) / 1000000);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			updateLogCommandError(commandId, e.getMessage());
			return new TerminalReturn(5000, "error:" + e.getMessage());
		} finally {
			requestingMap.put(clientId, false);
		}
		
		updateLogCommandError(commandId, "timeout:" + (System.nanoTime() - t) / 1000000);
		return new TerminalReturn(5001, "timeout:" + (System.nanoTime() - t) / 1000000);
	}
	
	
	public String getClientPassword(String clientId) {
		String sql = "select ifnull((select client_password from ter_client where client_id = ?), '') client_password";
		return getJdbcTemplate().queryForObject(sql, String.class, clientId);
	}

	private void insertLogCommand(String clientId, String commandContent, String commandError) {
		String insertSql = "insert into ter_log_command(service_id, client_id, command_content, command_error) values(?, ?, ?, ?)";
		getJdbcTemplate().update(insertSql, NettyUtils.getServiceId(), clientId, commandContent, commandError);
	}

	private int getLastInsertId() {
    	return getJdbcTemplate().queryForObject("select LAST_INSERT_ID();", Integer.class);
    }
	
	private void updateLogCommandReturn(int commandId, String commandReturn) {
		String updateSql = "update ter_log_command set command_return = ? where command_id = ?";
		getJdbcTemplate().update(updateSql, commandReturn, commandId);
	}
	
	public void updateLogCommandError(int commandId, String commandError) {
		String updateSql = "update ter_log_command set command_error = ? where command_id = ?";
		getJdbcTemplate().update(updateSql, commandError, commandId);
	}
}
