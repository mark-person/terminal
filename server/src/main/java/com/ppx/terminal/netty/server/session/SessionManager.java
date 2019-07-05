/**
 * 
 */
package com.ppx.terminal.netty.server.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mark
 * @date 2019年6月12日
 */
public class SessionManager {
	
	private Map<String, DeviceSession> sessionMap = new HashMap<String, DeviceSession>();
		
	
	private final static SessionManager INSTANCE = new SessionManager(); 
	
	public static SessionManager getSingleton() {
		return INSTANCE;
	}
	
	public DeviceSession getDeviceSession(String clientId) {
		return sessionMap.get(clientId);
	}
	
	public void addDeviceSession(String clientId, DeviceSession deviceSession) {
		sessionMap.put(clientId, deviceSession);
	}
	
	public void removeClient(String clientId) {
		sessionMap.remove(clientId);
	}
	
	public List<DeviceSession> listSession() {
		List<DeviceSession> returnList = new ArrayList<DeviceSession>();
		sessionMap.keySet().forEach(v -> {
			returnList.add(sessionMap.get(v));
		});
		return returnList;
	}
	
}	
