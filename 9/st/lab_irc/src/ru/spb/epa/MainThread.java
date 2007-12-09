package ru.spb.epa;

import ru.spb.epa.exceptions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Павел
 * Date: 08.12.2007
 * Time: 22:04:35
 */
public class MainThread extends Thread {

    private List<Client> clients = new ArrayList();
    private ServerSocket socket;
    private boolean run = true;

    public MainThread() throws IOException {
        this.socket = new ServerSocket(ServerConfig.port);
        System.out.println("Server listening on port " + ServerConfig.port + ".");
        this.start();
    }


    public void run() {
        try {
            this.socket.setSoTimeout(ServerConfig.SERVER_SOCKET_TIMEOUT);
        } catch (SocketException e) {
            ServerLogger.log("[MainThread] Can not set SocketTimeout to " + ServerConfig.SERVER_SOCKET_TIMEOUT);
        }
        try {
             ServerLogger.log("[MainThread] SocketTimeout is " + this.socket.getSoTimeout() + ".");
        } catch (IOException e1) { e1.printStackTrace(); }


        while (this.run) {
            try {
                System.out.println("[MainThread] Waiting for connections.");
                Socket client = null;

                while(client == null && this.run) try { client = this.socket.accept(); } catch (SocketTimeoutException e) { }
                if(!this.run)return;

                System.out.println("[MainThread] Accepted a connection from: " + client.getInetAddress());
                Client c = new Client(client, this);
                this.clients.add(c);

            } catch (Exception e) {
                ServerLogger.log("[MainThread] got exception listening port:" + e.getMessage() );
            }
        }
    }


    public void broadcastMessage(String mess){
        System.out.println("[MainThread] sending boadcast message:" + mess);

        for(Client c: this.clients){
            c.sendToClient(mess);
        }
    }

    public synchronized void changeNick(Client client, String newName) throws  CommandExecutionExcetion {
        if(newName == null) throw new IllegalArgumentException();
        for(Client c: this.clients){
            if (newName.equals(c.getNickname())) throw new UserExistException();
        }

        client.setNickname(newName);
    }

    public synchronized void disconnectUser(Client c){
        this.clients.remove(c);
    }

    protected void finalize() throws Throwable {
        System.out.println("[MainThread] finalize()");
        this.run = false;
        super.finalize();
    }

    public void interrupt() {
        for(Client c: this.clients){
            c.interrupt();
        }
         this.clients.clear();
        this.clients = null;
        System.out.println("[MainThread] interrupt()");
        this.run = false;
        super.interrupt();
    }

    public boolean isInterrupted() {
        return !this.run;
       // return super.isInterrupted();
    }


    public String toString() {
        String out = super.toString();
        StringBuffer sb = new StringBuffer();

        for(Client c: this.clients){
            sb.append("\n  * " + c);
        }

        if(sb.length() == 0){
            return out + " No clients connected.";
        }

        sb.append("\n=================");
        return out + "\n Connacted clients: " + sb.toString();
        //return super.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}