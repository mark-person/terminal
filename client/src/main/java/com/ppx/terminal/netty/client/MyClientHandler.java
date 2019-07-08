/**
 * 
 */
package com.ppx.terminal.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ppx.terminal.common.log.HeartbeatLogger;
import com.ppx.terminal.watchdog.Watchdog;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    
    private static Logger logger = LoggerFactory.getLogger(MyClientHandler.class);
    
    
	private String clientId;
	private String password;
	
	public MyClientHandler(String clientId, String password) {
		this.clientId = clientId;
		this.password = password;
	}
	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    	
        if (msg == null) {
            return;
        }
        if (msg.startsWith("OPEN_DOOR")) {
        	String[] item = msg.split("\\|");
        	if (item.length != 2) {
				logger.info("command error, msg:" + msg);
				return;
			}
        	
        	try {
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        	
        	logger.info("execute command:" + msg);
            ctx.writeAndFlush("R|0|OK");
        }
        else if (msg.startsWith("LOGIN-RETURN")) {
            if ("LOGIN-RETURN|SUC".equals(msg)) {
                DiscardClient.connectCount = 1;
                HeartbeatLogger.info("LOGIN_SUCCESS:" + clientId);
            }
            else {
                HeartbeatLogger.info("LOGIN_FAIL:" + clientId);
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught:{}", cause.getMessage());
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HeartbeatLogger.info("LOGIN_BEGIN:" + clientId);
        ctx.writeAndFlush("L|" + clientId + "|" + password);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive:{}", ctx.channel());
    }
    
    private int heartCount = 1;
    
    @Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {   
            IdleStateEvent event = (IdleStateEvent) obj;
            
            if (IdleState.READER_IDLE.equals(event.state())) {
            	ctx.channel().writeAndFlush("H|" + heartCount).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            Watchdog.setLastHeartNanoTime(System.nanoTime());
                            HeartbeatLogger.info("H|" + heartCount);
                        	heartCount++;
                        } else {
                            HeartbeatLogger.error("error H|" + heartCount);
                        }
                    }
            	});
            }
        }
    }
    
    
    
}


