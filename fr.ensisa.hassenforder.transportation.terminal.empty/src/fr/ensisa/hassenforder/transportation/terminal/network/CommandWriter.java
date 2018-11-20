package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class CommandWriter extends BasicAbstractWriter {

    public CommandWriter(OutputStream outputStream) {
        super(outputStream);
    }
    
    public void createGetPassbyId(long passId)
    {
      writeInt(Protocol.REQ_GET_PASS_BY_ID);
      writeLong(passId);
    }
    
    public void createUseTicket(String ticketId, int count)
    {
      writeInt(Protocol.REQ_USE_TICKET);
      writeString(ticketId);
      writeInt(count);
    }

}
