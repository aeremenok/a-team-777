/**
 * Command QUIT.
 * See FTP spec for details on the command.
 */
package com.coldcore.coloradoftp.command.impl.ftp;

import com.coldcore.coloradoftp.command.Reply;
import com.coldcore.coloradoftp.command.impl.AbstractCommand;
import com.coldcore.coloradoftp.connection.DataConnection;
import com.coldcore.coloradoftp.connection.DataPortListener;
import com.coldcore.coloradoftp.connection.DataPortListenerSet;
import com.coldcore.coloradoftp.factory.ObjectFactory;
import com.coldcore.coloradoftp.factory.ObjectName;

public class QuitCommand extends AbstractCommand {

  public Reply execute() {
    Reply reply = getReply();
    if (!testLogin()) return reply;

    //Abort data connection initiator
    controlConnection.getDataConnectionInitiator().abort();

    //Abort data connection listeners
    DataPortListenerSet listeners = (DataPortListenerSet) ObjectFactory.getObject(ObjectName.DATA_PORT_LISTENER_SET);
    for (DataPortListener listener : listeners.list())
      listener.removeConnection(controlConnection);

    /* We must not clear login state of the user, instead we will poison his/her connection
     * to make sure he/she cannot send any more commands in (except for special commands).
     */
    controlConnection.poison();

    //Logout the user
    logout();

    DataConnection dataConnection = controlConnection.getDataConnection();
    if (dataConnection != null) {
      reply.setCode("221");
      reply.setText("Logged out, closing control connection as soon as data transferred.");
    } else {
      reply.setCode("221");
      reply.setText("Logged out, closing control connection.");
    }
    return reply;
  }


  public boolean processInInterruptState() {
    return true;
  }


  /** Log out user.
   * This implementation does nothing.
   */
  protected void logout() {
  }
}
