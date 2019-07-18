/**
 * 
 */
package com.ppx.terminal.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * @author mark
 * @date 2019年7月4日
 */
public class CommUtils {
	
	public static final String PORT_NAME_COM1 = "COM1";
	
	public static List<String> listSerialPorts() {
		List<String> returnList = new ArrayList<String>();
        @SuppressWarnings("rawtypes")
        // 可以找到系统的所有的串口，每个串口对应一个CommPortldentifier
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts
                    .nextElement();
            switch (com.getPortType()) {
            case CommPortIdentifier.PORT_SERIAL:// type of the port is serial
                try {
                    CommPort thePort = com.open("CommUtil", 50);// open the serialPort
                    thePort.close();
                    returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|available");
                } catch (PortInUseException e) {
                	returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|is in use");
                } catch (Exception e) {
                    returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|Failed to open port:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }
	
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
                
                SerialReader.readerMsg = null;
                serialPort.addEventListener(new SerialReader(inputStream));
                serialPort.notifyOnDataAvailable(true);
            }
        }
        return serialPort;
    }
	
	
	public static void sendMessageOneWay(SerialPort serialPort, String string) throws IOException {
		try (OutputStream outputStream = serialPort.getOutputStream()) {
			outputStream.write(string.getBytes());
			outputStream.flush();
		}
    }
	
	public static void sendMessageOneWay(SerialPort serialPort, byte[] b) throws IOException {
		try (OutputStream outputStream = serialPort.getOutputStream()) {
			// 这里会在重启时，有时会停止
			outputStream.write(b);
			outputStream.flush();
		}
    }
	
	public static void sendMessageTwoWay(SerialPort serialPort, String string) throws IOException {
		try (OutputStream outputStream = serialPort.getOutputStream()) {
			outputStream.write(string.getBytes());
			outputStream.flush();
		}
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
