/**
 * 
 */
package com.ppx.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;

import com.ppx.terminal.common.util.ApiUtils;
import com.ppx.terminal.netty.server.boot.DiscardServer;

/**
 * @author mark
 * @date 2019年6月10日
 */

@EnableAutoConfiguration
@ComponentScan
public class TerminalServer implements CommandLineRunner {
	

	public static void main(String[] args) {
		SpringApplication.run(TerminalServer.class, args);
	}
	
	@Autowired
    private DiscardServer discardServer;
	
	@Value("${netty.port}")
	private Integer nettyPort;
	
	@Value("${api.secretKey}")
	private String apiSecretKey;
	
	@Autowired
    private WebApplicationContext context;
	
	@Override
    public void run(String... strings) throws Exception {
		// API init
		ApiUtils.API_SECRET_KEY = this.apiSecretKey;
		
		
        discardServer.run(context, nettyPort);
    }

}