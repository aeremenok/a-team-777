/*
 * Server.java
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
import javax.microedition.midlet.MIDletStateChangeException;
import javax.bluetooth.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import org.klings.wireless.j2me.*;

public class Server extends MIDlet implements CommandListener, Runnable{
	
	/* Display and Bluetooth LocalDevice */
	private Display display = null;
	private LocalDevice local = null;
	
	private List list = null;
	private Ticker tic = null;
	
	/* Commands */
	private Command exitCommand = new Command("Exit",Command.EXIT,2);
	private Command startCommand = new Command("Start",Command.SCREEN,2);
	private Command settingsCommand = new Command("Settings",Command.SCREEN,2);
	private Command backCommand = new Command("Back", Command.BACK, 1);
	private Command btInfoCommand = new Command("BTInfo",Command.SCREEN,2);
	
	/* Notifier for client connections */
	private StreamConnectionNotifier server = null;
	
	/* Connection for communication */
	private StreamConnection conn = null;
	private InputStream in = null;
	private OutputStream out = null;
	
	/* Connection parameters */
	private boolean authenticate = false;
	private boolean encrypt = false;
	private boolean authorize = false;
	private boolean master = false;
	
	/* Canvas to show status to user */
	private StatusCanvas s=null;
	
	/* Initialized in createService(), defined globally so it is accessible
	 * by the communicating thread.
	 */
	private String connectionURL;
	
	/* Thread for blocking functionality */
	
	private Thread t = null;
	
	/* Default constructor */
	public Server() {
		super();
		
	}
	
	protected void startApp() throws MIDletStateChangeException {
		
		display = Display.getDisplay(this);
		
		tic = new Ticker("By Klings, www.klings.org/nowires");
		
		mainMenu(null);
	}
	
	protected void pauseApp() {
		tic = null;
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		
		/* 
		 * Ensure that cleanUp() is called before MIDlet exits. cleanUp()
		 * will close the notifier, which will remove the service record from
		 * device Bluetooth SDDB. Very important!
		 */
		
		cleanup();
		
		/*
		 * If the communication Thread is waiting for clients, an exception
		 * occurs when we close the notifier. The thread will terminate,
		 * wait for it to terminate.
		 */
		if(t!=null){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notifyDestroyed();
	}
	public void commandAction(Command c, Displayable arg1) {
		
		if (c == exitCommand) {
			
			try {
				destroyApp(true);
			} catch (MIDletStateChangeException e) {
				
			}
		}
		else if(c == startCommand){
			
			/*
			 *  Start a server thread. createService() returns true
			 * if the service was created successfully.
			 */
			
			if (createService()){
				
				/* Show status screen to user */
				s = new StatusCanvas();
				s.waitForClient(connectionURL);
				s.addCommand(exitCommand);
				s.setCommandListener(this);
				s.setTicker(tic);
				display.setCurrent(s);
				
				t = new Thread(this);
				t.start();
				
			}else{
				mainMenu(new Alert("Error","Error creating service",null,AlertType.ERROR));
			}
			
			
			
			
		}else if(c == settingsCommand){
		
			settings();
		}else if (c == backCommand){
			
			mainMenu(null);
	}else if (c == btInfoCommand){
			
			BluetoothInfoCanvas btic = new BluetoothInfoCanvas();
			btic.addCommand(backCommand);
			btic.setCommandListener(this);
			display.setCurrent(btic);
			
			
	}else if (c == List.SELECT_COMMAND) {
            
			/* There is only one list. Check what the user selected */
			List list = (List) display.getCurrent();
			
			int index = list.getSelectedIndex();
			
			switch (index){
			
				case 0:
					
					authenticate = !authenticate;
					if(!authenticate) {
						encrypt = false;
						authorize = false;
					}
					break;
					
				case 1:
					encrypt = !encrypt;
					if(encrypt) authenticate = true;
					break;
					
				case 2:
					
					authorize = !authorize;
					if (authorize) authenticate = true;
					break;
					
				case 3:
					
					master = !master;
					break;
			}
            
			/* Update settings screen so it reflects the user's choice */
            settings();
		}
		
	}
	
	/* Display list of connection settings */
	private void settings() {
		
		
		List sett = new List("Settings:",List.IMPLICIT);
		
		sett.append("Authenticate: " + (authenticate?"On":"Off"),null);
		sett.append("Encrypt: " + (encrypt?"On":"Off"),null);
		sett.append("Authorize: " + (authorize?"On":"Off"),null);
		
		if ("true".equals(LocalDevice.getProperty("bluetooth.master.switch")))
		sett.append("Master: " + (master?"Yes":"No"),null);
		
		sett.addCommand(startCommand);
		sett.setCommandListener(this);
		
		display.setCurrent(sett);
	}
	
	/* Display main menu */
	private void mainMenu(Alert a){
		
		list = new List("Benchmark Server",List.IMPLICIT);
		list.setTicker(tic);
		list.addCommand(exitCommand);
		list.addCommand(startCommand);
		list.addCommand(settingsCommand);
		list.addCommand(btInfoCommand);
		list.setCommandListener(this);
		
		if(a == null){
		display.setCurrent(list);
		}else display.setCurrent(a,list);
	}
			
	/* Create ServiceRecord, set desired attributes */
	private boolean createService(){
		
		ServiceRecord record = null;
		
		Alert a = new Alert("Error","",null,AlertType.ERROR);
		a.setTimeout(Alert.FOREVER);
		
		/* Get the Bluetooth LocalDevice */
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
		} catch (BluetoothStateException e) {
			a.setString("Error getting localdevice or setting discovery mode");
			display.setCurrent(a,list);
			e.printStackTrace();
			return false;
		}
		
		String param ="";
		
		/* Add parameters to connection URL */
		if(encrypt) param = "encrypt=true;";
		else if(authorize) param="authorize=true;";
		else if(authenticate) param="authenticate=true;";
		
		param += "name=BTBench";
		
		if(master) param +=";master=true";
		
		connectionURL = "btspp://localhost:66ca80886d1f11d88526000bdb544cb1;"
			+ param;
			
		
		/* Get the notifier, will also generate a ServiceRecord */
		try {
			server = (StreamConnectionNotifier) Connector.open(connectionURL);
		} catch (IOException e1) {
			
			return false;
		}
		
		/* Get the ServiceRecord associated with the notifier */
		try {
			record = local.getRecord(server);
		}
		catch (IllegalArgumentException iae){
			return false;
			
		}
		
		/* 
		 * Manipulate the ServiceRecord to meet our needs. Some code is
		 * disabled, but is included to show how attributes are set.
		 */
		DataElement elm = null;
		
		/* 
		 * Set public browse root in browsegrouplist, making service
		 * public browseable
		 */
		elm = new DataElement(DataElement.DATSEQ);
		elm.addElement(new DataElement(DataElement.UUID,new UUID(0x1002)));
		record.setAttributeValue(0x0005,elm);
		
		
		/* Set service description */ 
		elm = new DataElement(DataElement.STRING,"BT Benchmark service");
		record.setAttributeValue(0x101,elm);
		
		/* Set service provider name */
		elm = new DataElement(DataElement.STRING,"Klings, NoWires Research Group");
		record.setAttributeValue(0x102,elm);
		
		/* Set serviceInfoTimeToLive */
		/*elm = new DataElement(DataElement.U_INT_4,10000);
		record.setAttributeValue(0x0007,elm);*/
		
		/* Set serviceAvailability */
		/*elm = new DataElement(DataElement.U_INT_1,255);
		record.setAttributeValue(0x0008,elm);*/
		
		/* Set documentationURL */
		elm = new DataElement(DataElement.URL,"http://wap.klings.org/btbenchmark.wml");
		record.setAttributeValue(0x000A,elm);
		
		/* Set clientExecutableURL */
		elm = new DataElement(DataElement.URL,"http://wap.klings.org/java/btbenchmark.jad");
		record.setAttributeValue(0x000B,elm);
		
		/* Set iconURL */
		/*elm = new DataElement(DataElement.URL,"http://klings.org/java/BTBenchmark.ico");
		record.setAttributeValue(0x000C,elm);*/
		
		/* Update the record, else changes are lost */
		try {
			local.updateRecord(record);
		} catch (ServiceRegistrationException e3) {
						
			return false;
			
		}
		return true;
	}
	
	/* run() method executed by communication thread */
	public void run() {
		
		/* 
		 * If the notifier is not available, a service has not been
		 * created.
		 */
		if(server == null) return;
		
		Alert a = new Alert("Error","",null,AlertType.ERROR);
		a.setTimeout(Alert.FOREVER);
		
		
		/* 
		 * Open the notifier. This is a blocking operation. Now the
		 * ServiceRecord will be entered in the Bluetooth SDDB and
		 * the server is ready for client connections. 
		 */
		try {
				conn = server.acceptAndOpen();
			}
			catch (ServiceRegistrationException sre){
				a.setString("Error creating service record");
				display.setCurrent(a,list);
				
				cleanup();
				return;
			}
			catch (IOException e2) {
				if (t != null){
				a.setString("Error starting server. " + e2.getMessage());
				display.setCurrent(a,list);
				}
				
				cleanup();
				return;
			}
			
			/* A client has connected! Retrieve information about the
			 * remote device and display it to the user.
			 */
			RemoteDevice dev = null;
			String name,address;
			boolean authorized = false;
			
			try {
				dev = RemoteDevice.getRemoteDevice(conn);
				name = dev.getFriendlyName(false);
				address = dev.getBluetoothAddress();
				authorized = dev.isAuthorized(conn);
			} catch (IOException e) {
				name = "Unknown";
				address = "Unknown";
			}
			
			
			s.connectedToClient(name,address,dev.isAuthenticated(),
					dev.isEncrypted(),authorized);
			display.setCurrent(s);
			
			/* 
			 * The user now knows who connected. Get streams and start
			 * communication.
			 */
			try {
				in = conn.openInputStream();
				out = conn.openOutputStream();
			} catch (IOException e4) {
				a.setString("Error opening input/output streams:" + e4.getMessage());
				display.setCurrent(a,list);
				
				cleanup();
				t = null;
				return;
			}
			
			DataInputStream dais = new DataInputStream(in);
			
			byte[] data = new byte[512];
			
			//int data;
			long timer = 0;
			boolean keepOn = true;
			int bytesRead = 0;
			int iterations = 0;
			
			/* read the number of iterations */
			try {
				iterations = dais.readInt();
			}catch (EOFException eof ){
				
				a.setString("EOF on first read.");
				display.setCurrent(a,list);
				
				cleanup();
				list.addCommand(startCommand);
				return;
			} catch (IOException e5) {
				a.setString("Error on first read.");
				display.setCurrent(a,list);
				
				
				cleanup();
				list.addCommand(startCommand);
				return;
			}
			
			/* Get the time and do actual communication */
			try{
				timer = System.currentTimeMillis();
				
				for (int i = iterations; i>0;i--){
					
					bytesRead = dais.read(data,0,data.length);
					out.write(0);
					out.flush();
				}
				timer = System.currentTimeMillis() - timer;
			}catch (IOException ioe) {
				
				a.setString(bytesRead + " bytes read before communication error occured. " + ioe.getMessage());
				display.setCurrent(a,list);
				
				cleanup();
				list.addCommand(startCommand);
				return;
			}
			
			/* Communication complete. Cleanup connections */
			cleanup();
			long sec = timer / 1000;
			long transferred = iterations/2;
			
			/* Display statistics to user */
			a.setType(AlertType.INFO);
			a.setTitle("Success!");
			a.setString("Read " + data +" KB in " + sec + " seconds.\n"+transferred/sec + " KBps");
			
			
			display.setCurrent(a,list);
			list.addCommand(startCommand);
			list.addCommand(settingsCommand);
			list.setTitle("Run complete.");
			
		
		
	}
	
	/* Close streams and notifier */
	private void cleanup() {

		try {
			
			if(in != null){
				in.close();
			}
			
			if(out != null){
				out.close();
			}
			
			if (conn != null) {
				conn.close();
			}
			
			if (server != null){
				
				server.close();
			}
		}catch (IOException ioe){
			
			//Shit happens. All the time.
		}
	}
	
	
	
}
