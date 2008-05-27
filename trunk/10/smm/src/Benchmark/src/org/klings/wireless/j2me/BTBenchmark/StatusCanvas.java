/*
 * StatusCanvas.java
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

import javax.microedition.lcdui.*;
import org.klings.wireless.j2me.*;


public class StatusCanvas extends Canvas{

	private final int WAITING_FOR_CLIENT = 1;
	private final int CONNECTED_TO_CLIENT = 2;
	private final int CONNECTED_TO_SERVER = 3;
	
	/* Anchor for text */
	private final int ANCHOR = Graphics.LEFT|Graphics.TOP;
	
	/* indent */
	private final int X = 2;
	
	private int mode = 0;
	private boolean authentication,encryption,authorization,isServer;
	/* Different fonts for different types of text */
	private Font plain,bold;
    
    /*Size of canvas */
	private int canvasHeight, canvasWidth;
    
    /* Height of fonts */
	private int plainHeight,boldHeight;
	
    /* Keep track of where we are in the canvas */
	private int y = 0;
    
    /* Custom String */
	private String remoteName,remoteAddress,connectionURL;
    
	public StatusCanvas() {
		
		super();
		
		/*Get the canvas size */
        canvasHeight = this.getHeight();
        canvasWidth = this.getWidth();
		
		/* Get height of fonts */
		plain = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        bold = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
                
        /* heights to compute where to draw. */
        plainHeight = plain.getHeight();
        boldHeight = bold.getHeight();
		
        /*Set Custom to null */
        remoteName = null;
        remoteAddress = null;
        connectionURL = null;
        
        
	}
	
	protected void paint(Graphics g) {
		
        /* Initialize the canvas */
        g.setColor(0xffffff);
        g.fillRect(0,0, getWidth(),getHeight());
        
        /* We want black text */
        g.setColor(0x000000);
        g.setFont(plain);
        
        if (isServer) serverStatus(g);
        else clientStatus(g);
		
	}
	
	
	
	public void waitForClient(String connectionURL){
		/* this must be a server */
		isServer = true;
		mode = WAITING_FOR_CLIENT;
		this.connectionURL = connectionURL;
		this.repaint();
	}
	
	public void connectedToClient(String deviceName, String deviceAddress,
			boolean authentication,boolean encryption,boolean authorization){
		
		/* this must be a server */
		isServer = true;
		remoteName = deviceName;
		remoteAddress = deviceAddress;
		this.authentication = authentication;
		this.encryption = encryption;
		this.authorization = authorization;
		mode = CONNECTED_TO_CLIENT;
		this.repaint();
	}
	public void connectedToServer(String deviceName, String deviceAddress,
			boolean authentication,boolean encryption){
		/* this must be a client */
		isServer = false;
		remoteName = deviceName;
		remoteAddress = deviceAddress;
		this.authentication = authentication;
		this.encryption = encryption;
		
		
		mode = CONNECTED_TO_SERVER;
		this.repaint();
	}
	
	private void serverStatus(Graphics g){
		
		switch (mode){
			
			case WAITING_FOR_CLIENT:
				this.setTitle("Service started!");
				y = 2;
				
				y+= CanvasHelper.printString("Connection URL:",X,y,ANCHOR,bold,canvasWidth-X,g);
						 
				y+= CanvasHelper.printString(connectionURL,X,y,ANCHOR,plain,canvasWidth-X,g);
				
				y+=plainHeight;
				
				y+= CanvasHelper.printString("Waiting for client connection.",X,y,ANCHOR,plain,canvasWidth-X,g);
				
				break;
				
			case CONNECTED_TO_CLIENT:
				this.setTitle("Client connected!");
				y = 2;
				
				y+= CanvasHelper.printString("Client name:",X,y,ANCHOR,bold,canvasWidth-X,g);
				y+= CanvasHelper.printString(remoteName,X,y,ANCHOR,plain,canvasWidth-X,g);
				
				y+= CanvasHelper.printString("Client address:",X,y,ANCHOR,bold,canvasWidth-X,g);
				y+= CanvasHelper.printString(remoteAddress,X,y,ANCHOR,plain,canvasWidth-X,g);
				
				y+= CanvasHelper.printString("Security settings:",X,y,ANCHOR,bold,canvasWidth-X,g);
				y+= CanvasHelper.printString("Authenticated: " + (authentication ? "Yes" : "No"),X,y,ANCHOR,plain,canvasWidth-X,g);
				y+= CanvasHelper.printString("Encrypted: "+ (encryption ? "Yes" : "No"),X,y,ANCHOR,plain,canvasWidth-X,g);
				y+= CanvasHelper.printString("Authorized: " + (authorization ? "Yes" : "No"),X,y,ANCHOR,plain,canvasWidth-X,g);
				
				break;
				
		}
		
	}
	
	private void clientStatus(Graphics g){
		
		if (mode == CONNECTED_TO_SERVER){
			
			this.setTitle("Connected to server!");
			y = 2;
			
			y+= CanvasHelper.printString("Server name:",X,y,ANCHOR,bold,canvasWidth-X,g);
			y+= CanvasHelper.printString(remoteName,X,y,ANCHOR,plain,canvasWidth-X,g);
			
			y+= CanvasHelper.printString("Server address:",X,y,ANCHOR,bold,canvasWidth-X,g);
			y+= CanvasHelper.printString(remoteAddress,X,y,ANCHOR,plain,canvasWidth-X,g);
			
			y+= CanvasHelper.printString("Security settings:",X,y,ANCHOR,bold,canvasWidth-X,g);
			y+= CanvasHelper.printString("Authenticated: " + (authentication ? "Yes" : "No"),X,y,ANCHOR,plain,canvasWidth-X,g);
			y+= CanvasHelper.printString("Encrypted: "+ (encryption ? "Yes" : "No"),X,y,ANCHOR,plain,canvasWidth-X,g);
			
		}
		
	}
	
}
