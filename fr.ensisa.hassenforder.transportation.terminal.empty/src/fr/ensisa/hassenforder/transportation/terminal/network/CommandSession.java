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
        	if (true != Boolean.TRUE) throw new IOException ();

          CommandWriter writer = new CommandWriter(connection.getOutputStream());
          CommandReader reader = new CommandReader(connection.getInputStream());
          writer.createGetPassbyId(passId);
          writer.send();

          reader.receive();
          if (reader.getType() == Protocol.REP_PASS_T)
          {
            final Pass pass = reader.getPass();
            this.passId = pass.getPassId();

            return pass;
          }

          return null;
        } catch (IOException e) {
        	this.passId = 0;
            return null;
        }

	}

	@Override
	synchronized public boolean useTicket(String ticketId, int count) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return false;
        } catch (IOException e) {
    		return false;
        }
	}

}
