package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class TerminalReader extends BasicAbstractReader {
  private long passId = -1;
  private String ticketId = "";
  private int used = 0;


	public TerminalReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
      case 0 : break;

      case Protocol.REQ_GET_PASS_BY_ID:
        this.passId = this.readLong();
        break;

      case Protocol.REQ_USE_TICKET:
        this.passId = this.readLong();
        this.ticketId = this.readString();
        this.used = this.readInt();
        break;
		}
	}

}
