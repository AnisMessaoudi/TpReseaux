package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;


public class TerminalSession extends Thread {

  private Socket connection;
  private NetworkListener listener;

  public TerminalSession(Socket connection, NetworkListener listener) {
    this.connection = connection;
    this.listener = listener;
    if( listener == null) throw new RuntimeException("listener cannot be null");
  }

  public void close () {
    this.interrupt();
    try {
      if (connection != null)
        connection.close();
    } catch (IOException e) {
    }
    connection = null;
  }

  public boolean operate() {
    try {
      TerminalWriter writer = new TerminalWriter (connection.getOutputStream());
      TerminalReader reader = new TerminalReader (connection.getInputStream());
      reader.receive ();
      switch (reader.getType ()) {
        case 0 : return false;

        case Protocol.REQ_GET_PASS_BY_ID:
          final long passId =
            ((TerminalReader.GetPassByIdResult) reader.getResult()).passId
          ;
          writer.writePassReply(this.listener.terminalFetchPass(passId));
          break;

        case Protocol.REQ_USE_TICKET:
          final TerminalReader.UseTicketResult result =
            (TerminalReader.UseTicketResult) reader.getResult()
          ;
          if (this.listener.terminalUseTicket(result.passId, result.ticketId, result.count)) {
            writer.writeOkReply();
          } else {
            writer.writeKoReply();
          }
          break;  // socket closed
      }
      writer.send ();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public void run() {
    while (true) {
      if (! operate())
        break;
    }
    try {
      if (connection != null) connection.close();
    } catch (IOException e) {
    }
  }

}
