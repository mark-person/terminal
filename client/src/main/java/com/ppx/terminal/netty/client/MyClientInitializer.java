/**
 * 
 */
package com.ppx.terminal.netty.client;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    
    private String heartIntervalSecond;
	private String clientId;
	private String password;
	
	public MyClientInitializer(String clientId, String password, String heartIntervalSecond) {
		this.clientId = clientId;
		this.password = password;
		this.heartIntervalSecond = heartIntervalSecond;
	}
	
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        
        int readerIdleTime = Integer.parseInt(heartIntervalSecond);
        
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(readerIdleTime, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyClientHandler(clientId, password));
    }
}
