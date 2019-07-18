/**
 * 
 */
package com.ppx.terminal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ppx.terminal.common.api.ApiClientUtils;
import com.ppx.terminal.netty.client.DiscardClient;

/**
 * 
 * @author mark
 * @date 2019年6月25日
 */
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan
public class TerminalClient extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
	    
		SpringApplication.run(TerminalClient.class, args);
	}
	
	@Value("${netty.heart.interval.second}")
    private String heartIntervalSecond;
	
	
	
	
	@Autowired
    private DiscardClient discardClient;
	@Override
    public void run(String... strings) throws Exception {
		
		
	    
	    ApplicationHome home = new ApplicationHome(getClass());
	    String jarPath = home.getSource().getParent().replace("\\target", "");
	    String confPath = jarPath + "/conf.properties";
	    
	    Properties properties = new Properties();
	    try (FileReader confFileReader = new FileReader(confPath);
	            BufferedReader bufferedReader = new BufferedReader(confFileReader);) {
	        properties.load(bufferedReader);
	        
	        // 初始化API参数
		    ApiClientUtils.API_SECRET_KEY = properties.getProperty("api.secretKey");
			ApiClientUtils.API_URL = properties.getProperty("api.url");
	        
	        
	        // 获取key对应的value值
	        String serverIp = properties.getProperty("server.ip");
	        String serverPort = properties.getProperty("server.port");
            String clientId = properties.getProperty("client.id");
            String clientPassword = properties.getProperty("client.password");
            
            discardClient.setValue(serverIp, serverPort, clientId, clientPassword, heartIntervalSecond);
            discardClient.run();
        }
	    
	    
	    
    }
	

}