package ru.spb.epa;

import ru.spb.epa.commands.Command;
import ru.spb.epa.exceptions.IRCServerException;
import ru.spb.epa.exceptions.CommandExecutionException;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: �����
 * Date: 09.12.2007
 * Time: 0:12:40
 *
 * http://tools.ietf.org/html/rfc2813
 *
 * 2.2.1 Users
 *
 * Each user is distinguished from other users by a unique nickname
   having a maximum length of nine (9) characters.  See the protocol
   grammar rules (section 3.3.1) for what may and may not be used in a
   nickname.  In addition to the nickname, all servers MUST have the
   following information about all users: the name of the host that the
   user is running on, the username of the user on that host, and the
   server to which the client is connected.
 */
public class Client extends Thread{


    //================================================================================================================
    // Static params
    //================================================================================================================
    private static int ID = 0;


    //================================================================================================================
    // Private params
    //================================================================================================================
    private boolean run = true;
    private String myID;
    private MainThread myServer;
    private String nickname;
    String ipAdress;
    String fullname;
    /**
     * the name of the host that the user is running on
     */
    private String hostname;

    /**
     * the username of the user on that host
     */
    private String username;

    /**
     * the server to which the client is connected
     */
    private String server; 

    private Socket socket;

    private BufferedReader inputStream;  // Input from server socket

    private DataOutputStream outputStream; // Output to server socket

    //================================================================================================================
    // Setter & getters
    //================================================================================================================

    public String getMyID() {
        return myID;
    }

    public String getNickname() {
        return (nickname==null)?"":nickname;
    }

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getIpAdress() {
        return (ipAdress==null)?"":ipAdress;
    }

    public String getFullname() {
        return fullname;
    }

    public String getServer() {
        return server;
    }




    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    /**
     * :WiZ!jto@tolsun.oulu.fi
     * @return
     */
    public String getNameCombined(){
        return this.nickname + "!~" + this.fullname + "@" + this.hostname;
    }
    //================================================================================================================
    // Constructors
    //================================================================================================================
    public Client(Socket socket, MainThread m) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(ServerConfig.SERVER_SOCKET_TIMEOUT);
        this.myServer = m;
        this.inputStream = new BufferedReader(
            new InputStreamReader(
               new DataInputStream(this.socket.getInputStream())));

        this.outputStream = new DataOutputStream(this.socket.getOutputStream());
        ID++;
        this.myID = "Client_" + ID;
        this.start();
    }


    //================================================================================================================
    //  RUN                                                RUN                                                     RUN
    //================================================================================================================
    public void run() {
        ServerLogger.log(this.myID + " CONNECTED");

        String message;  // The full message

        try {
            while(this.run)
            try{
                
                while ((message = this.inputStream.readLine()) != null)  {
                ServerLogger.log(myID + " got message:" + message);

                try {
                    Command command = Command.getCommand(message);
                    command.execute(this);
                } catch (IRCServerException e) {
                    ServerLogger.log(this.myID + " error executing this command:" + e.getMessage());
                }
            }
            }catch(SocketTimeoutException e) {}
        } catch (IOException e) {
            ServerLogger.log(this.myID + " IOException on run()" + e.getMessage());
        }

        this.myServer.disconnectUser(this);
        ServerLogger.log(this.myID + " DISCONNECTED");
    }

    //================================================================================================================
    // common methods
    //================================================================================================================
    /**
    * Write directly to the IRC chat server, refer to RFC-1459
    * for valid commands.
    * @param message - what to write
     * @param srvPrefix - if need server name at start of command
    */
    public void sendToClient(String message, boolean srvPrefix) {
        String send = ((srvPrefix)?(":" + ServerConfig.SERVER_NAME  + " "):"")
                        + message+"\r\n";
        ServerLogger.log(this.myID + " sending:" + send);
        try {outputStream.writeBytes(send);}catch (Exception e){
            ServerLogger.log(this.myID + " error on sendToClient " + e.getMessage());}
    }

    public void sendBroadcastmessage(String m, boolean prefix){
        this.myServer.broadcastMessage(m, prefix);
    }

    public void changeNick(String newName) throws CommandExecutionException {
        this.myServer.changeNick(this,newName);
    }

    public void quit(){
        this.run = false;
        this.myServer.disconnectUser(this);
    }

    public void part(String channelname){
        myServer.part(this,channelname);
    }

    //================================================================================================================
    // Object and thread ooverriden functions
    //================================================================================================================
    public void interrupt() {
        ServerLogger.log(this.myID + " interrupt()");
        this.run = false;
        //super.interrupt();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean isInterrupted() {
        return !this.run;
       // return super.isInterrupted();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void finalize() throws Throwable {
        ServerLogger.log(this.myID + "finalize()");
        interrupt();
        super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String toString() {
        return super.toString() + this.myID + " " + this.nickname; 
    }

    public boolean equals(Object obj) {
        if(obj instanceof Client){
            Client c = (Client) obj;
            return c.nickname != null && this.nickname != null && this.nickname.equals(c.nickname);
        }
        if(obj instanceof String){
            String c = (String) obj;
            return this.nickname!=null && this.nickname.equals(c);
        }
        return false;
    }
}
