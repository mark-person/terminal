

package com.ppx.terminal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ppx.terminal.netty.server.impl.LogServiceImpl;





@Service
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger logger = LoggerFactory.getLogger(StartListener.class);
	@Autowired
	private LogServiceImpl logServiceImpl;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)  {
    	logger.info("StartListener........begin");
    	logServiceImpl.insertLogStart();
    	logger.info("StartListener........end");
    }

    
}



