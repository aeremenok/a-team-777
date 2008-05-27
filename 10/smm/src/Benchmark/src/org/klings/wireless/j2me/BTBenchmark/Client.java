/*
 * Client.java
 * 
 * Version 1.0
 * 
 * 22. June 2004
 * 
 * Copyright (c) 2004, Andre N. Klingsheim
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the NoWires research group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.klings.wireless.j2me.BTBenchmark;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import java.util.*;
import java.io.*;

import javax.microedition.io.*;


import javax.bluetooth.*;
import org.klings.wireless.j2me.*;

public class Client extends MIDlet implements CommandListener,
											DiscoveryListener,
											Runnable{
	
    private Display display = null;
    private LocalDevice local=null;
    private DiscoveryAgent agent=null;
    
    /* Vectors to store devices an services in */
    private Vector deviceVector = null;
    private Vector serviceVector = null;
    
    private Ticker tic = null;
    
    /* Global list accessible to deviceDiscovered(...) */
    private List deviceList = null;
    private List serviceList = null;
    
    /* lobal serviceRecord accessible to several threads */
    private ServiceRecord globalRecord = null;
    
    private StreamConnection conn = null;
    private DataOutputStream out = null;
    private InputStream in = null;
    
    /* Connection parameters */
    boolean authenticate,encrypt,master=false;
    
    private int iterations = 0;
    
    
    private Command exitCommand = new Command("Exit",Command.EXIT,2);
    private Command searchCommand = new Command("New Search",Command.SCREEN, 1);
    private Command backCommand = new Command("Back", Command.BACK, 1);
    private Command cancelCommand = new Command("Cancel",Command.CANCEL,1);
    private Command settingsCommand = new Command("Settings",Command.SCREEN,1);
    private Command btInfoCommand = new Command("BTInfo",Command.SCREEN,2);
    
    int currentMenu = 0;
    
    /* Keep track of ongoing device discoveries */
    boolean inquiring = false;
    
    /* Keep track of ongoing service discoveries */
    int serviceSearch = 0;
    
    public void startApp() {
        
        display = Display.getDisplay(this);
        
        try {
            local = LocalDevice.getLocalDevice();
            
        }catch(BluetoothStateException bse) {
            System.err.println("LocalDevice error");
        }
        
        tic = new Ticker("By Klings, www.klings.org/nowires");
        mainMenu(null);
    }
    
    public void pauseApp() {
        display = null;
        local = null;
        tic = null;
    }
    
    public void destroyApp(boolean unconditional) {
        
        cleanUp();
        
        notifyDestroyed();
    }
    
    /* Main menu, list of cached/known Bluetooth devices */
    private void mainMenu(Alert a) {
        
        List knownDevices = new List("Cached/known devices",List.IMPLICIT);
        
        if (deviceVector == null) deviceVector = new Vector();
        if (agent == null) agent = local.getDiscoveryAgent();
        
        RemoteDevice[] devices = agent.retrieveDevices(DiscoveryAgent.PREKNOWN);
        
        String name = null;
        
        if (devices != null) {
            
        	/* 
        	 * Synchronize on vector before running through loop, since
        	 * Vector methods are thread safe. We obtain the object lock
        	 * on the Vector once, instead of every iteration. Add devices
        	 * to the deviceVector.
        	 */
            synchronized(deviceVector) {
                
                for (int i = devices.length-1;i >=0;i--) {
                    deviceVector.addElement(devices[i]);
                    
                    try {
                        name = devices[i].getFriendlyName(false);
                        
                    }catch (IOException ioe) {
                        name = devices[i].getBluetoothAddress();
                    }
                    if (name.equals("")) name = devices[i].getBluetoothAddress();
                    knownDevices.append(name,null);
                }
            } //End synchronized
        }
        devices = null; devices = agent.retrieveDevices(DiscoveryAgent.CACHED);
        
        if (devices !=null) {
            
            synchronized(deviceVector) {
                for (int i = devices.length-1;i >=0;i--) {
                    deviceVector.addElement(devices[i]);
                    
                    try {
                        name = devices[i].getFriendlyName(false);
                    }catch (IOException ioe) {
                        name = devices[i].getBluetoothAddress();
                    }
                    if (name.equals("")) name = devices[i].getBluetoothAddress();
                    knownDevices.append(name,null);
                }
            }
        }
        
        /* No cached/known devices, notify the user */
        if (deviceVector.isEmpty()) {
            knownDevices.append("Empty",null);
        }
        
        knownDevices.setTicker(tic);
        knownDevices.addCommand(exitCommand);
        knownDevices.addCommand(searchCommand);
        knownDevices.addCommand(btInfoCommand);
        knownDevices.setCommandListener(this);
        
        /* Show Alert, if available */
        if (a ==null ) display.setCurrent(knownDevices);
        else display.setCurrent(a,knownDevices);
        
        currentMenu = 1;
        
    }
    
    /* Show list of discovered devices */
    private void deviceScreen(Alert a) {
        
        
        //if currentmenu < 3 we are in screen with known/cached devices or
        //screen with discovered devices and have issued a device search
        //deviceList is then reInitialized by startInquiry(), hence we must add
        //these commands again
        if (currentMenu < 3) {
            
            deviceList.setTicker(tic);
            if(inquiring) {
                deviceList.addCommand(cancelCommand);
            }else{
                deviceList.removeCommand(cancelCommand);
                deviceList.addCommand(exitCommand);
                deviceList.addCommand(searchCommand);
                deviceList.addCommand(backCommand);
                
            }
            
            deviceList.setCommandListener(this);
        }
        
        if (a == null) display.setCurrent(deviceList);
        else display.setCurrent(a,deviceList);
        currentMenu = 2;
    }
    
    /* Start device discovery */
    private void startInquiry() {
        
        Alert a = new Alert("Inquiry status",null,null,AlertType.INFO);
        
        
        if (agent ==null) agent = local.getDiscoveryAgent();
        
        /* Remove old search results in vector and deviceList */
        deviceVector.removeAllElements();
        deviceList = new List("Nearby devices",List.IMPLICIT);
        
        /* Start the actual device discovery */
        try {
            inquiring = agent.startInquiry(DiscoveryAgent.GIAC, this);
        }catch(BluetoothStateException bse) {
            a.setType(AlertType.ERROR);
            a.setString("Bluetooth error while starting inquiry");
            mainMenu(a);
            return;
        }
        
        /* Notify the user if inquiry was started or not */
        if (inquiring) {
            a.setString("Inquiry started");
            deviceScreen(a);
        }
        else {
            a.setType(AlertType.ERROR);
            a.setString("Error starting inquiry");
            
            /* With no Inquiry we have no need for this any more. */
            deviceList = null;
            mainMenu(a);
        }
    }
    
    /* Retrieve friendly names for all devices in deviceVector */
    private void getFriendlyNames() {
    	String name = null;
    	
    	synchronized(deviceVector){
    		for (int i = deviceVector.size() -1; i>= 0;i--) {
    			
    			try {
    				name = ((RemoteDevice)deviceVector.elementAt(i)).getFriendlyName(false);
    			}catch (IOException ioe) {
    				continue;
    			}
    			if (!name.equals("")) {
    				deviceList.set(i, name,null);
    			}
    		}// End for
    	}// End synchronized 
    	
    }
    
    /* Start service discovery on remote device */
    private void startServiceDiscovery(RemoteDevice rDevice) {
        
        Alert a = null;
        
        /* Prepare serviceVector and service list*/
        if (serviceVector == null) serviceVector = new Vector();
        else serviceVector.removeAllElements();
        
        serviceList = new List("",List.IMPLICIT);
        try {
            serviceList.setTitle( rDevice.getFriendlyName(false));
        }catch (IOException ioe) {
            serviceList.setTitle(rDevice.getBluetoothAddress());
        }
        
        UUID[] uuids = new UUID[1];
        
        /* Add the BTBenchmark UUID to an array */
        uuids[0] = new UUID("66ca80886d1f11d88526000bdb544cb1",false);
        
        /* 
         * Retrieve default attributes, services with BTBenchmark UUID on
         * the remote device.
         */
        try {
            int transid = agent.searchServices(null, uuids, rDevice, this);
        }catch (BluetoothStateException bse) {
            a = new Alert("Bluetooth error", "Error starting service search",null, AlertType.ERROR);
            deviceScreen(a);
            return;
        }
        
    }
    
    /* Select amount of data to transfer */
    private void selectDataScreen(Alert a) {
        
    	List tList = new List("Select amount of data ",List.IMPLICIT);
    	tList.append("10KB",null);
        tList.append("100KB",null);
        tList.append("500KB",null);
        tList.append("1000KB",null);
    	
        tList.addCommand(settingsCommand);
        tList.addCommand(backCommand);
        tList.setCommandListener(this);
        
        if (a == null) display.setCurrent(tList);
        else display.setCurrent(a,tList);
        currentMenu = 3;
    }
    
    /* 
     * Set the number of iterations to use by the thread when communicating,
     * then start the communication Thread.
     */
    private void doTransfer(int index) {
        
    	switch(index){
    		
    		case 0: iterations = 20; break;
    		case 1: iterations = 200; break;
    		case 2: iterations = 1000; break;
    		case 3: iterations = 2000; break;
    	}
    	
    	Thread t = new Thread(this);
    	t.start();
    	
    }
    
    /* Display settings menu */
    private void settingsScreen(){
    	
    	List settings = new List("Settings",List.IMPLICIT);
    	settings.append("Authenticate: " + (authenticate? "Yes":"No"),null);
    	settings.append("Encrypt: " + (encrypt? "Yes":"No"),null);
    	
    	if ("true".equals(LocalDevice.getProperty("bluetooth.master.switch")))
    	settings.append("Master: " + (master? "Yes":"No"),null);
    	
    	settings.addCommand(backCommand);
    	settings.setCommandListener(this);
    	display.setCurrent(settings);
    	currentMenu = 31;
    	
    }
    
    /* A device is discovered. Add it to the deviceVector */
    public void deviceDiscovered(javax.bluetooth.RemoteDevice remoteDevice, javax.bluetooth.DeviceClass deviceClass) {
        
        /* Add device to vector in case of further use */
        deviceVector.addElement(remoteDevice);
        
        /* 
         * Add device to active list, making devices show up as they are
         * discoverd. Add only BT address, getting the name requires the 
         * device to go on air and the device is probably quite busy now. 
         */
        
        deviceList.append(remoteDevice.getBluetoothAddress(),null);
        
    }
    
    /* Device discovery completed. Get friendly names. */
    public void inquiryCompleted(int status) {
        inquiring = false;
        Alert a = new Alert("Inquiry status",null,null,AlertType.INFO);
        
        /* Check status */
        switch(status) {
            
            case DiscoveryListener.INQUIRY_COMPLETED:
                
                if (deviceVector.size() == 0) {
                    a.setString("No devices found!");
                    deviceList.append("Empty",null);
                }else {
                    getFriendlyNames();
                    a.setString(deviceVector.size() + " devices found!");
                }
                deviceScreen(a);
                break;
                
            case DiscoveryListener.INQUIRY_ERROR:
                a.setType(AlertType.ERROR);
                a.setString("Error occured.");
                mainMenu(a);
                break;
                
            case DiscoveryListener.INQUIRY_TERMINATED:
                
                a.setString("Search terminated");
                
                if(deviceVector.size() > 0) {
                getFriendlyNames();
                deviceScreen(a);
                }else{
                mainMenu(a);
                }
                break;
        }
        
    }
    
    /* ServiceSearch completed. */
    public void serviceSearchCompleted(int transID, int respCode) {
        serviceSearch = 0;
        Alert a = new Alert("Search status",null,null,AlertType.INFO);
        
        /* Check resonse Code */
        switch(respCode) {
            
            case DiscoveryListener.SERVICE_SEARCH_COMPLETED:
                a.setString("Service found!");
                selectDataScreen(a);
                
                break;
                
            case DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
                
                a.setString("Device not reachable");
                deviceScreen(a);
                break;
                
            case DiscoveryListener.SERVICE_SEARCH_ERROR:
                a.setType(AlertType.ERROR);
                a.setString("Error during service search");
                deviceScreen(a);
                break;
                
            case DiscoveryListener.SERVICE_SEARCH_NO_RECORDS:
                a.setString("No services found");
                deviceScreen(a);
                break;
                
            case DiscoveryListener.SERVICE_SEARCH_TERMINATED:
                a.setString("Search terminated");
                deviceScreen(a);
                break;
        }
        
        
    }
    
    /* Services were discovered. */
    public void servicesDiscovered(int transID, ServiceRecord[] serviceRecord) {
        
        DataElement nameElement = null;
        String name = null;
        RemoteDevice dev = serviceRecord[0].getHostDevice();
        
        /* 
         * Keep the discovered service record in globalRecord so it is
         * available to the communication thread.
         */
        globalRecord=serviceRecord[0];
        
    }
    
    /* Command handler */
    public void commandAction(Command c,Displayable d) {
        
        if (c == exitCommand) destroyApp(true);
        
        else if(c == searchCommand) {
            startInquiry();
        }
        else if(c == backCommand) {
            switch(currentMenu) {
                
            	case 11:
                case 2:
                    mainMenu(null);
                    break;
                    
                case 3:
                    deviceScreen(null);
                    break;
                
                case 31:    
                case 4:
                    selectDataScreen(null);
                    break;
      
            }
        }else if(c == cancelCommand){
        	
        	switch(currentMenu) {
        		
        		case 2: // Device discovery in progress
        			if (agent.cancelInquiry(this)) {
        				
        				inquiring = false;
        				deviceScreen(null);
        				
        			}else{
        				deviceScreen(new Alert("Error","Could not stop inquiry or inquiry not started", null, AlertType.ERROR));
        				inquiring = false;
        			}
        			
        			break;
        			
        		case 3: //Service discovery in progress
        			
        			if(!agent.cancelServiceSearch(serviceSearch)) {
        				selectDataScreen(new Alert("Error","No active service search",null, AlertType.ERROR));
        			}
        			
        			break;
        			
        			
        	}
        }else if(c == settingsCommand){
        	
        	settingsScreen();
        	
        }else if (c == btInfoCommand){
        	BluetoothInfoCanvas canv = new BluetoothInfoCanvas();
        	canv.addCommand(backCommand);
        	canv.setCommandListener(this);
        	display.setCurrent(canv);
        	currentMenu = 11;
        }
        else if (c == List.SELECT_COMMAND) {
            
            List list = (List) display.getCurrent();
            int index = list.getSelectedIndex();
            
            switch (currentMenu) {
                
                case 1: // Main list of known devices
                    
                    
                case 2: //List of newly found devices
                    if (! deviceVector.isEmpty()) startServiceDiscovery((RemoteDevice)deviceVector.elementAt(index));
                    break;
                    
                case 3: //Browse service
                	
                    doTransfer(index);
                    
                    break;
                    
                    case 31: //Settings changed
                    	
                    	switch(index){
                    		
                    		case 0:
                    			
                    			authenticate = !authenticate;
                    			if(!authenticate) encrypt = false;
                    			break;
                    			
                    		case 1:
                    			encrypt = !encrypt;
                    			if(encrypt) authenticate=true;
                    			break;
                    			
                    		case 2:
                    			master = !master;
                    			break;
                    	}
                    	
                    	settingsScreen();
                    break;
            }//End switch for list-index
        }// End if for List-command
        
    }//End commandaction

    /* Code executed by the communication thread */
	public void run() {
		
		/* Generate connection URL based on user selections */
		String conURL = null; 
		
		if(encrypt){
			conURL = globalRecord.getConnectionURL(ServiceRecord.AUTHENTICATE_ENCRYPT,master);
		}else if (authenticate){
			
			conURL = globalRecord.getConnectionURL(ServiceRecord.AUTHENTICATE_NOENCRYPT,master);
		}else{
			conURL = globalRecord.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,master);
		}
		
		StreamConnection conn = null;
		
		Alert a = new Alert("Error",conURL,null,AlertType.ERROR);
		a.setTimeout(Alert.FOREVER);
		
		/* Connect to the Benchmark server */
		try {
			 conn = (StreamConnection) Connector.open(conURL);
		} catch (IOException e) {
			a.setString("Error creating connection\n URL used:\n"+conURL);
			deviceScreen(a);
			cleanUp();
			return;
		}
		
		/* Display information about the server */
		StatusCanvas s = new StatusCanvas();
		RemoteDevice dev = globalRecord.getHostDevice();
		String name = "Unknown";
		try {
			name = dev.getFriendlyName(false);
		} catch (IOException e4) {
			name = "Unretrievable";
		}
		
		s.connectedToServer(name,dev.getBluetoothAddress(),
				dev.isAuthenticated(),dev.isEncrypted());
		display.setCurrent(s);
		
		/* Open streams */
		try {
			out = conn.openDataOutputStream();
			in = conn.openInputStream();
		} catch (IOException e1) {
			a.setString("Error opening streams");
			display.setCurrent(a,serviceList);
			cleanUp();
			return;
		}
		
		
		/* Write the number of iterations */
		byte[] bytes = new byte[512];
		try {
			out.writeInt(iterations);
			out.flush();
		} catch (IOException e2) {
			a.setString("Error doing first write");
			display.setCurrent(a,serviceList);
			cleanUp();
			return;
		}
		
		/* Send the actual data */
		try {
			
			for (int i = iterations; i > 0; i--){
				
				out.write(bytes);
				out.flush();
				in.read();
			}
		} catch (IOException e3) {
			a.setString("Error writing main load");
			display.setCurrent(a,serviceList);
			cleanUp();
			return;
		}
		
		/* Clean up streams and connection */
		cleanUp();
		
		/* Display the number of KB sent */
		a.setString("Sent " + iterations/2 + " KB. All well!");
		a.setType(AlertType.INFO);
		a.setTitle("Done!");
		deviceScreen(a);
	}
	
	/* Close streams and streamconnection */
	private void cleanUp(){
		
		try {
			if (out != null){
				out.close();
			}
			
			if (in != null){
				in.close();
			}
			
			if(conn != null){
				conn.close();
			}
		} catch (IOException e) {
			// Shit happens. All the time.
		}
	}
}
