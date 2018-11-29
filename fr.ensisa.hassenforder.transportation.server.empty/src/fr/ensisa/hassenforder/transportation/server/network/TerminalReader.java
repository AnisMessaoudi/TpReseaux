package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class TerminalReader extends BasicAbstractReader {
  public static class GetPassByIdResult
  {
    public final long passId;


    private GetPassByIdResult(long passId)
    {
      super();
      this.passId = passId;
    }
  }


  public static class UseTicketResult
  {
    public final long passId;
    public final String ticketId;
    public final int count;


    private UseTicketResult(long passId, String ticketId, int count)
    {
      super();
      this.passId = passId;
      this.ticketId = ticketId;
      this.count = count;
    }
  }



  private Object result = null;



  public TerminalReader(InputStream inputStream) {
    super (inputStream);
  }

  public void receive() {
    type = readInt ();
    switch (type) {
      case 0 : break;

      case Protocol.REQ_GET_PASS_BY_ID:
        this.result = this.readGetPassById();
        break;

      case Protocol.REQ_USE_TICKET:
        this.result = this.readUseTicket();
        break;
    }
  }



  public Object getResult()
  {
    final Object result = this.result;

    this.result = null;

    return result;
  }



  private GetPassByIdResult readGetPassById()
  {
    return new GetPassByIdResult(this.readLong());
  }


  private UseTicketResult readUseTicket()
  {
    final long passId = this.readLong();
    final String ticketId = this.readString();
    final int count = this.readInt();

    return new UseTicketResult(passId, ticketId, count);
  }
}
