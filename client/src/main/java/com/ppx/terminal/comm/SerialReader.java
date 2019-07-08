/**
 * 
 */
package com.ppx.terminal.comm;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.util.Strings;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;


/**
 * 
 * @author mark
 * @date 2019年7月8日
 */
public class SerialReader implements SerialPortEventListener {
	
	public static String readerMsg = null;
	
    private InputStream in;

    public SerialReader(InputStream in) {
        this.in = in;
    }

    public void serialEvent(SerialPortEvent arg0) {
        byte[] buffer = new byte[2];
        try {
        	// the thread need to sleep for completed
        	// receive the data
        	
        	while (Strings.isEmpty(SerialReader.readerMsg)) {
        		Thread.sleep(100);
                if (in.available() > 0) {
                    in.read(buffer);
                }
                SerialReader.readerMsg = new String(buffer);
        	}
            
            // System.out.println(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}