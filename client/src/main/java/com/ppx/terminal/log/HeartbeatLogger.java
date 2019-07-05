/**
 * 
 */
package com.ppx.terminal.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author mark
 * @date 2019年6月20日
 */
public class HeartbeatLogger {
	
	private static Logger heartbeatLogger = LogManager.getLogger("heartbeatLogger");
	
	public static void info(String message) {
		heartbeatLogger.info(message);
	}
	
	public static void error(String message) {
        heartbeatLogger.error(message);
    }
}
