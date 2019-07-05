/**
 * 
 */
package com.ppx.terminal.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * https://www.cnblogs.com/sowhat4999/p/4575696.html
 * 
 * @author mark
 * @date 2019年6月10日
 */
public class Test {
	public static void mainx(String[] args) {
		System.out.println("----------begin");
		
		//listAvailablePorts();
		// listPorts();
		
		try {
			
			// gnu.io.NoSuchPortException
			//connect("COM1");
			
            sendMessage(connect("COM1"), "P");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		System.out.println("----------end");
	}
	
	// ------------------003
	
	/** */
    public static class SerialWriter implements Runnable {
        OutputStream out;
        String commandString;

        public SerialWriter(OutputStream out, String commandString) {
            this.out = out;
            this.commandString = commandString;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);// an interval of 3 seconds to sending
                                        // data
                    out.write(commandString.getBytes());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
	
	public static void sendMessage(SerialPort serialPort, String string) {
        try {
            OutputStream outputStream = serialPort.getOutputStream();
            (new Thread(new SerialWriter(outputStream, string))).start();// send
                                                                            // command
                                                                            // in
                                                                            // the
                                                                            // new
                                                                            // thread
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	
	
	
	public static class SerialReader implements SerialPortEventListener {
        private InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void serialEvent(SerialPortEvent arg0) {
            byte[] buffer = new byte[1024];
            try {
                Thread.sleep(500);// the thread need to sleep for completed
                                    // receive the data
                if (in.available() > 0) {
                    in.read(buffer);
                }
                System.out.println(new String(buffer));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
	
	public static SerialPort connect(String portName) throws Exception {
        SerialPort serialPort = null;
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(portName);// initializes of port operation
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(portName, 2000);// the delay
                                                                    // time of
                                                                    // opening
                                                                    // port
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);// serial
                                                                        // communication
                                                                        // parameters
                                                                        // setting
                InputStream inputStream = serialPort.getInputStream();
                // OutputStream outputStream = serialPort.getOutputStream();
                // (new Thread(new SerialWriter(outputStream))).start();
                serialPort.addEventListener(new SerialReader(inputStream));
                serialPort.notifyOnDataAvailable(true);
            }
        }
        return serialPort;

    }
	
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ------------------002
	public static void listPorts()
    {
        @SuppressWarnings("unchecked")
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
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
	
	
	
	
	// -------------------001
	
	public static void listAvailablePorts() {
        HashSet<CommPortIdentifier> portSet = getAvailableSerialPorts();
        String[] serialPort = new String[portSet.size()];
        int i = 0;
        for (CommPortIdentifier comm : portSet) {
            serialPort[i] = comm.getName();
            System.out.println(serialPort[i]);
            i++;
        }
    }
	
	public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        @SuppressWarnings("rawtypes")
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();// 可以找到系统的所有的串口，每个串口对应一个CommPortldentifier
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts
                    .nextElement();
            switch (com.getPortType()) {
            case CommPortIdentifier.PORT_SERIAL:// type of the port is serial
                try {
                    CommPort thePort = com.open("CommUtil", 50);// open the serialPort
                    thePort.close();
                    h.add(com);
                } catch (PortInUseException e) {
                    System.out.println("Port, " + com.getName()
                            + ", is in use.");
                } catch (Exception e) {
                    System.err.println("Failed to open port " + com.getName());
                    e.printStackTrace();
                }
            }
        }
        return h;
    }
	
	

}
