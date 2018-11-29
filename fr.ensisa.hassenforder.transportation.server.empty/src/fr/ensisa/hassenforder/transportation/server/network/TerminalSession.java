package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;



public class TerminalSession extends Thread
{
  private Socket connection;
  private NetworkListener listener;



  public TerminalSession(Socket connection, NetworkListener listener)
  {
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


  public boolean operate()
  {
    try {
      TerminalWriter writer = new TerminalWriter(connection.getOutputStream());
      TerminalReader reader = new TerminalReader(connection.getInputStream());

      reader.receive();
      switch (reader.getType ()) {
        case 0:
          return false; // socket closed

        default:
          writer.writeKoReply();
          break;

        case Protocol.REQ_GET_PASS_BY_ID:
          this.handleGetPassByIdRequest(
            writer, (TerminalReader.GetPassByIdRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_USE_TICKET:
          this.handleUseTicketRequest(
            writer, (TerminalReader.UseTicketRequest) reader.extractRequest()
          );
          break;
      }

      writer.send();
      return true;
    } catch (IOException e) {
      return false;
    }
  }


  public void run()
  {
    while (true) {
      if (! operate())
        break;
    }
    try {
      if (connection != null) connection.close();
    } catch (IOException e) {
    }
  }
  
  private void handleGetPassByIdRequest(
    TerminalWriter writer, TerminalReader.GetPassByIdRequest request
  )
  {
    if (request != null) {
      final Pass pass = this.listener.terminalFetchPass(request.passId);

      if (pass != null) {
        writer.writePassReply(pass);
        return;
      }
    }

    writer.writeKoReply();
  }


  private void handleUseTicketRequest(
    TerminalWriter writer, TerminalReader.UseTicketRequest request
  )
  {
    if (request != null) {
      if (
        request.count > 0
        && this.listener.terminalUseTicket(
          request.passId, request.ticketId, request.count
        )
      ) {
        writer.writeOkReply();
        return;
      }
    }

    writer.writeKoReply();
  }
}


