package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.IOException;
import java.net.Socket;


import fr.ensisa.hassenforder.transportation.terminal.model.Pass;

public class CommandSession implements ISession {

    private Socket connection;
    private long passId;

    public CommandSession() {
    	this.passId = 0;
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.TERMINAL_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	synchronized public Pass getPassById(long passId) {
        try
        {

          CommandWriter writer = new CommandWriter(connection.getOutputStream());
          CommandReader reader = new CommandReader(connection.getInputStream());
          writer.createGetPassbyId(passId);
          writer.send();

          reader.receive();
          switch(reader.getType())
          {
            default :
              return null;

            case 0 :
              return null;

            case Protocol.REP_PASS :
              final Pass pass = reader.getPass();
              this.passId = pass.getPassId();
              return pass;
          }

        } catch (IOException e) {
        	this.passId = 0;
            return null;
        }

	}

	@Override
	synchronized public boolean useTicket(String ticketId, int count) {
        try {

          CommandWriter writer = new CommandWriter(connection.getOutputStream());
          CommandReader reader = new CommandReader(connection.getInputStream());
          writer.createUseTicket(passId,ticketId, count);
          writer.send();

          reader.receive();
          switch(reader.getType())
          {
          case 0 :
            return false; // socket closed

          default:
            return false;

          case Protocol.REP_OK:
            break;
          }

        return true;

        } catch (IOException e) {
    		return false;
        }
	}

}