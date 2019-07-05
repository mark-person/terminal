package com.ppx.terminal.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class NettyUtils {
    
    private static String serviceTag;
 
    @Autowired
    private Environment env;
    
    private static Environment staticEnv;
    
    @Bean
    public Object initApplicationUtils() {
    	NettyUtils.staticEnv = env;
    	return null;
    }
    
    public static Environment getEnv() {
    	return NettyUtils.staticEnv;
    }
    
    public static String getServiceId() {
    	return staticEnv.getProperty("netty.service.id");
    }
    
    /**
     * 取得服务ID, ip:port组成
     * 
     * @return
     */
    public static String getServiceTag() {
        if (!StringUtils.isEmpty(serviceTag)) {
            return serviceTag;
        }
        
        String ip = "";
        String port = staticEnv.getProperty("netty.port");

        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            // windows >>>>>>>>>>>>>>>>>>>>>
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
                ip = "127.0.0.1";
            }
        } else {
            // Linux >>>>>>>>>>>>>>>>>>>>>>>>>
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                        .hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    String name = intf.getName();
                    if (!name.contains("docker") && !name.contains("lo")) {
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                                .hasMoreElements();) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()) {
                                String ipaddress = inetAddress.getHostAddress().toString();
                                if (!ipaddress.contains("::") && !ipaddress.contains("0:0:")
                                        && !ipaddress.contains("fe80")) {
                                    ip = ipaddress;
                                }
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
                ip = "127.0.0.1";
            }
        }
        
        NettyUtils.serviceTag = ip + ":" + port;
        return NettyUtils.serviceTag;
    }
    
   
}
