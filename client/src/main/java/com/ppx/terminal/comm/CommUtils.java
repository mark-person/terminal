/**
 * 
 */
package com.ppx.terminal.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ppx.terminal.comm.Test.SerialReader;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

/**
 * @author mark
 * @date 2019年7月4日
 */
public class CommUtils {
	
	public static SerialPort connect(String portName) throws Exception {
        SerialPort serialPort = null;
        
        // initializes of port operation
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
        	throw new RuntimeException("Port is currently in use");
        } else {
        	// the delay time of opening port
            CommPort commPort = portIdentifier.open(portName, 2000);
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                // serial commnuication setting
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                InputStream inputStream = serialPort.getInputStream();
                // OutputStream outputStream = serialPort.getOutputStream();
                // (new Thread(new SerialWriter(outputStream))).start();
                serialPort.addEventListener(new SerialReader(inputStream));
                serialPort.notifyOnDataAvailable(true);
            }
        }
        return serialPort;
    }
	
	
	public static void sendMessage(SerialPort serialPort, String string) throws IOException {
		try (OutputStream outputStream = serialPort.getOutputStream()) {
			outputStream.write(string.getBytes());
			outputStream.flush();
		}
		
		// OutputStream outputStream = serialPort.getOutputStream();
		// (new Thread(new SerialWriter(outputStream, string))).start();
    }
	
	
	public static String getPortTypeName(int portType) {
        switch (portType) {
        case CommPortIdentifier.PORT_I2C:
            return "I2C";
        case CommPortIdentifier.PORT_PARALLEL:
            return "Parallel";
        case CommPortIdentifier.PORT_RAW:
            return "Raw";
        case CommPortIdentifier.PORT_RS485:
            return "RS485";
        case CommPortIdentifier.PORT_SERIAL:
            return "Serial";
        default:
            return "unknown type";
        }
    }
	
	
}
