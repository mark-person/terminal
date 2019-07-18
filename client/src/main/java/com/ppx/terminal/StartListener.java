

package com.ppx.terminal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ppx.terminal.comm.CommUtils;

import gnu.io.SerialPort;





@Service
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger logger = LoggerFactory.getLogger(StartListener.class);
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	logger.info("StartListener........begin");
		
		SerialPort sp  = null;
		try {
			sp = CommUtils.connect(CommUtils.PORT_NAME_COM1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (sp != null) {
				sp.close();
			}
		}
		logger.info("SERIAL_PORT_NAME:" + CommUtils.PORT_NAME_COM1 + "|connect success");
    	logger.info("StartListener........end");
    }

    
}



