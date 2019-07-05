package com.ppx.terminal.mvc.app;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ppx.terminal.comm.CommUtils;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;


@Service
public class TestServiceImpl {
	
	public List<String> listSerialPorts() {
		List<String> returnList = new ArrayList<String>();
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
                    // h.add(com);
                    returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|available");
                } catch (PortInUseException e) {
                	returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|is in use");
                } catch (Exception e) {
                    returnList.add(com.getName() + "|" + CommUtils.getPortTypeName(com.getPortType()) + "|Failed to open port");
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }
	
	
	
	
}
