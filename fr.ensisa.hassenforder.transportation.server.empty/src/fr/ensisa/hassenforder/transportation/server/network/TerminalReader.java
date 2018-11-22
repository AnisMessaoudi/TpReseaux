package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class TerminalReader extends BasicAbstractReader
{
  public static class GetPassByIdRequest
  {
    public final long passId;

    private GetPassByIdRequest(long passId)
    {
      super();
      this.passId = passId;
    }
  }


  public static class UseTicketRequest
  {
    public final long passId;
    public final String ticketId;
    public final int count;

    private UseTicketRequest(long passId, String ticketId, int count)
    {
      super();
      this.passId = passId;
      this.ticketId = ticketId;
      this.count = count;
    }
  }



  private Object request = null;



  public TerminalReader(InputStream inputStream)
  {
    super (inputStream);
  }

  public void receive() {
    type = readInt ();
    switch (type) {
      default: break;

      case Protocol.REQ_GET_PASS_BY_ID:
        this.request = this.readGetPassByIdRequest();
        break;

      case Protocol.REQ_USE_TICKET:
        this.request = this.readUseTicketRequest();
        break;
    }
  }



  public Object extractRequest()
  {
    final Object request = this.request;

    this.request = null;

    return request;
  }



  private GetPassByIdRequest readGetPassByIdRequest()
  {
    return new GetPassByIdRequest(this.readLong());
  }


  private UseTicketRequest readUseTicketRequest()
  {
    final long passId = this.readLong();
    final String ticketId = this.readString();
    final int count = this.readInt();

    return new UseTicketRequest(passId, ticketId, count);
  }
}
