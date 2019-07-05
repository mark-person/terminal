/**
 * 
 */
package com.ppx.terminal.netty.server.session;

import io.netty.channel.Channel;

/**
 * @author mark
 * @date 2019年6月12日
 */
public class DeviceSession {
	private String clientId;
	
	private Channel channel;
	
	private String returnValue;
	

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public DeviceSession(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	
}
