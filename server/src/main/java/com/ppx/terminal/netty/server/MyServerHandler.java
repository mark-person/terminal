/**
 * 
 */
package com.ppx.terminal.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.ppx.terminal.mvc.controller.TerminalServiceImpl;
import com.ppx.terminal.netty.server.impl.LogServiceImpl;
import com.ppx.terminal.netty.server.session.DeviceSession;
import com.ppx.terminal.netty.server.session.SessionManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
	
	private WebApplicationContext context;
	
	public MyServerHandler(WebApplicationContext context) {
		this.context = context;
	}

	private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

	// AttributeKey 相对于 web session【重要】
	public static final AttributeKey<DeviceSession> KEY = AttributeKey.valueOf("IO");

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
		// 如果中间出现exception，刚断开连接
		DeviceSession session = ctx.channel().attr(KEY).get();
		if (msg == null || session == null) {
			closeConnection(ctx);
			return;
		}

		if (msg.startsWith("L")) {
			String[] item = msg.split("\\|");
			if (item.length != 3) {
				logger.error("login msg error, msg:" + msg);
				insertLogLogin("unknown", "E-M");
				ctx.writeAndFlush("LOGIN-RETURN|E-M");
				closeConnection(ctx);
				return;
			}
			String clientId = item[1];
			
			// 校验密码
			if (!getClientPassord(clientId).equals(item[2])) {
				logger.error("login password error, msg:" + msg);
				insertLogLogin(clientId, "E-P");
				ctx.writeAndFlush("LOGIN-RETURN|E-P");
				closeConnection(ctx);
				return;
			}
			
			session.setClientId(clientId);
			SessionManager.getSingleton().addDeviceSession(clientId, session);
			insertLogLogin(clientId, "SUC");
			ctx.writeAndFlush("LOGIN-RETURN|SUC");
		}
		
		
		String clientId = session.getClientId();
		if (msg.startsWith("H")) {
			String[] item = msg.split("\\|");
			if (item.length != 2) {
				logger.error("heartCount error, msg:" + msg + ", clientId:" + clientId);
				return;
			}
			insertLogHeart(clientId, Integer.parseInt(item[1]));
		}
		
		if (msg.startsWith("R")) {
			String[] item = msg.split("\\|");
			if (item.length != 3) {
				logger.info("return error, msg:" + msg + ", clientId:" + clientId);
			}
			session.setReturnValue(msg);
		}
	}
	
	private String getClientPassord(String clientId) {
		try {
			TerminalServiceImpl imp = context.getBean(TerminalServiceImpl.class);
			return imp.getClientPassword(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void insertLogHeart(String clientId, int heartCount) {
		try {
			LogServiceImpl imp = context.getBean(LogServiceImpl.class);
			imp.insertLogHeart(clientId, heartCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertLogLogin(String clientId, String loginStatus) {
		try {
			LogServiceImpl imp = context.getBean(LogServiceImpl.class);
			imp.insertLogLogin(clientId, loginStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertLogConnect(String connectStatus, String connectMsg) {
		try {
			LogServiceImpl imp = context.getBean(LogServiceImpl.class);
			imp.insertLogConnect(connectStatus, connectMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeConnection(ChannelHandlerContext ctx) {
		logger.info("closeConnection:" + ctx);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 2019-06-25 19:37:41.394  INFO 11212 --- [ntLoopGroup-3-2] c.p.t.netty.server.MyServerHandler       : exceptionCaught:[远程主机强迫关闭了一个现有的连接。]
		insertLogConnect("E-E", cause.getMessage());
		closeConnection(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		DeviceSession session = new DeviceSession(ctx.channel());
		ctx.channel().attr(KEY).set(session);
		insertLogConnect("REG", ctx.channel().toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		DeviceSession session = ctx.channel().attr(KEY).getAndSet(null);
		SessionManager.getSingleton().removeClient(session.getClientId());
		insertLogConnect("E-I", ctx.channel().toString());
	}

}
