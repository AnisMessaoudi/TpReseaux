package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Urban;



public class KioskReader extends BasicAbstractReader
{
  public static class NewPassRequest
  {
    private NewPassRequest()
    {
      super();
    }
  }


  public static class GetPassByIdRequest
  {
    public final long passId;

    private GetPassByIdRequest(long passId)
    {
      super();
      this.passId = passId;
    }
  }


  public static class BuyRouteRequest
  {
    public final long passId;
    public final String from;
    public final String to;
    public final int count;

    private BuyRouteRequest(long passId, String from, String to, int count)
    {
      super();
      this.passId = passId;
      this.from = from;
      this.to = to;
      this.count = count;
    }
  }


  public static class BuyUrbanRequest
  {
    public final long passId;
    public final int count;

    private BuyUrbanRequest(long passId, int count)
    {
      super();
      this.passId = passId;
      this.count = count;
    }
  }


  public static class BuySubscriptionRequest
  {
    public final long passId;
    public final int month;

    private BuySubscriptionRequest(long passId, int month)
    {
      super();
      this.passId = passId;
      this.month = month;
    }
  }


  public static class PayTransactionRequest
  {
    public final long id;
    public final long passId;

    private PayTransactionRequest(long id, long passId)
    {
      super();
      this.id = id;
      this.passId = passId;
    }
  }


  public static class CancelTransactionRequest
  {
    public final long id;

    private CancelTransactionRequest(long id)
    {
      super();
      this.id = id;
    }
  }



  private Object request = null;



  public KioskReader(InputStream inputStream)
  {
    super (inputStream);
  }



  public void receive()
  {
    this.type = readInt();
    switch (type) {
      default:
        break;

      case Protocol.REQ_NEW_PASS:
        this.request = this.readNewPass();
        break;

      case Protocol.REQ_GET_PASS_BY_ID:
        this.request = this.readGetPassById();
        break;

      case Protocol.REQ_BUY_ROUTE:
        this.request = this.readBuyRoute();
        break;

      case Protocol.REQ_BUY_URBAN:
        this.request = this.readBuyUrban();
        break;

      case Protocol.REQ_BUY_SUBSCRIPTION:
        this.request = this.readBuySubscription();
        break;

      case Protocol.REQ_PAY_TRANSACTION:
        this.request = this.readPayTransaction();
        break;

      case Protocol.REQ_CANCEL_TRANSACTION:
        this.request = this.readCancelTransaction();
    }
  }


  public Object extractRequest()
  {
    final Object request = this.request;

    this.request = null;

    return request;
  }



  private NewPassRequest readNewPass()
  {
    return new NewPassRequest();
  }


  private GetPassByIdRequest readGetPassById()
  {
    final long passId = this.readLong();

    return new GetPassByIdRequest(passId);
  }


  private BuyRouteRequest readBuyRoute()
  {
    final long passId = this.readLong();
    final String from = this.readString();
    final String to = this.readString();
    final int count = this.readInt();

    return new BuyRouteRequest(passId, from, to, count);
  }


  private BuyUrbanRequest readBuyUrban()
  {
    final long passId = this.readLong();
    final int count = this.readInt();

    return new BuyUrbanRequest(passId, count);
  }


  private BuySubscriptionRequest readBuySubscription()
  {
    final long passId = this.readLong();
    final int month = this.readInt();

    return new BuySubscriptionRequest(passId, month);
  }


  private PayTransactionRequest readPayTransaction()
  {
    final long id = this.readLong();
    final long passId = this.readLong();

    return new PayTransactionRequest(id, passId);
  }


  private CancelTransactionRequest readCancelTransaction()
  {
    final long id = this.readLong();

    return new CancelTransactionRequest(id);
  }
}
