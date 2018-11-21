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
          final TerminalReader.GetPassByIdResult getPassByIdResult =
            (TerminalReader.GetPassByIdResult) reader.getResult()
          ;
          if (getPassByIdResult == null) {
            writer.writeKoReply();
            break;
          }

          final Pass pass = this.listener.terminalFetchPass(
            getPassByIdResult.passId
          );
          if (pass == null) {
            writer.writeKoReply();
            break;
          }

          writer.writePassReply(pass);
          break;

        case Protocol.REQ_USE_TICKET:
          final TerminalReader.UseTicketResult useTicketResult =
            (TerminalReader.UseTicketResult) reader.getResult()
          ;
          if (useTicketResult == null) {
            writer.writeKoReply();
            break;
          }

          if (this.listener.terminalUseTicket(
            useTicketResult.passId,
            useTicketResult.ticketId,
            useTicketResult.count
          )) {
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
