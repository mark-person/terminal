/**
 * 
 */
package com.ppx.terminal.netty.client;

import org.springframework.stereotype.Service;

import com.ppx.terminal.common.log.HeartbeatLogger;
import com.ppx.terminal.watchdog.Watchdog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author mark
 * @date 2019年6月25日
 */
@Service
public class DiscardClient {
    
    private String ip;
    
    private String usedIp;
    
    private String port;
    
    private String usedPort;
    
    private String clientId;
    
    private String password;
    
    private String heartIntervalSecond;
    
    public void setValue(String ip, String port, String clientId, String password, String heartIntervalSecond) {
        this.ip = ip;
        this.port = port;
        this.clientId = clientId;
        this.password = password;
        this.heartIntervalSecond = heartIntervalSecond;
    }

    private EventLoopGroup eventLoopGroup;
    
    private Bootstrap bootstrap;
    
    public static int connectCount = 1;
    
    public void run() throws Exception {
        if (eventLoopGroup != null) {
            try {
                eventLoopGroup.shutdownGracefully().sync();
            } catch(Exception e) {}
        }
        
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer(clientId, password, heartIntervalSecond));
            connect();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
    
    
    
    private void setNextUsedIp() {
        String[] ipArray = ip.split(";");
        if (usedIp == null || ipArray.length == 1) {
            usedIp = ipArray[0];
        }
        else {
            for (int i = 0; i < ipArray.length; i++) {
                String tmpIp = ipArray[i];
                if (tmpIp.equals(usedIp)) {
                    int usedIndex = i + 1;
                    usedIndex = usedIndex == ipArray.length ? 0 : usedIndex;
                    usedIp = ipArray[usedIndex];
                    break;
                }
            } 
        }
    }
    
    private void setNextUsedPort() {
        String[] portArray = port.split(";");
        if (usedPort == null || portArray.length == 1) {
            usedPort = portArray[0];
        }
        else {
            for (int i = 0; i < portArray.length; i++) {
                String tmpPort = portArray[i];
                if (tmpPort.equals(usedPort)) {
                    int usedIndex = i + 1;
                    usedIndex = usedIndex == portArray.length ? 0 : usedIndex;
                    usedPort = portArray[usedIndex];
                    break;
                }
            }
        }
    }
    
    // 第一次隔1秒，第二次5秒，第二次10秒， 第四次及之后20秒，连通(channelActive)后connectCount = 1;
    private void connect() throws Exception {
        setNextUsedIp();
        setNextUsedPort();
        
        Watchdog.setLastConnectNanoTime(System.nanoTime());
        HeartbeatLogger.info("connect to " + usedIp + ":" + usedPort + ", C|" + connectCount);
        
        int sleepTimeSecond = 20;
        if (connectCount == 1) {
            sleepTimeSecond = 1;
        }
        else if (connectCount == 2) {
            sleepTimeSecond = 5;
        }
        else if (connectCount == 2) {
            sleepTimeSecond = 10;
        }
        
        connectCount++;
        try {
            ChannelFuture channelFuture = bootstrap.connect(usedIp, Integer.parseInt(usedPort)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            Thread.sleep(sleepTimeSecond * 1000);
            run();
        }
    }
}

