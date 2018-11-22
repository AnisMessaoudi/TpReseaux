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
  public static class NewPassResult
  {
    private NewPassResult()
    {
      super();
    }
  }


  public static class GetPassByIdResult
  {
    public final long passId;

    private GetPassByIdResult(long passId)
    {
      super();
      this.passId = passId;
    }
  }


  public static class BuyRouteResult
  {
    public final long passId;
    public final String from;
    public final String to;
    public final int count;

    private BuyRouteResult(long passId, String from, String to, int count)
    {
      super();
      this.passId = passId;
      this.from = from;
      this.to = to;
      this.count = count;
    }
  }


  public static class BuyUrbanResult
  {
    public final long passId;
    public final int count;

    private BuyUrbanResult(long passId, int count)
    {
      super();
      this.passId = passId;
      this.count = count;
    }
  }


  public static class BuySubscriptionResult
  {
    public final long passId;
    public final int month;

    private BuySubscriptionResult(long passId, int month)
    {
      super();
      this.passId = passId;
      this.month = month;
    }
  }


  public static class PayTransactionResult
  {
    public final long id;
    public final long passId;

    private PayTransactionResult(long id, long passId)
    {
      super();
      this.id = id;
      this.passId = passId;
    }
  }


  public static class CancelTransactionResult
  {
    public final long id;

    private CancelTransactionResult(long id)
    {
      super();
      this.id = id;
    }
  }



  private Object result = null;



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
        this.result = this.readNewPass();
        break;

      case Protocol.REQ_GET_PASS_BY_ID:
        this.result = this.readGetPassById();
        break;

      case Protocol.REQ_BUY_ROUTE:
        this.result = this.readBuyRoute();
        break;

      case Protocol.REQ_BUY_URBAN:
        this.result = this.readBuyUrban();
        break;

      case Protocol.REQ_BUY_SUBSCRIPTION:
        this.result = this.readBuySubscription();
        break;

      case Protocol.REQ_PAY_TRANSACTION:
        this.result = this.readPayTransaction();
        break;

      case Protocol.REQ_CANCEL_TRANSACTION:
        this.result = this.readCancelTransaction();
    }
  }


  public Object extractResult()
  {
    final Object result = this.result;

    this.result = null;

    return result;
  }



  private NewPassResult readNewPass()
  {
    return new NewPassResult();
  }


  private GetPassByIdResult readGetPassById()
  {
    final long passId = this.readLong();

    return new GetPassByIdResult(passId);
  }


  private BuyRouteResult readBuyRoute()
  {
    final long passId = this.readLong();
    final String from = this.readString();
    final String to = this.readString();
    final int count = this.readInt();

    return new BuyRouteResult(passId, from, to, count);
  }


  private BuyUrbanResult readBuyUrban()
  {
    final long passId = this.readLong();
    final int count = this.readInt();

    return new BuyUrbanResult(passId, count);
  }


  private BuySubscriptionResult readBuySubscription()
  {
    final long passId = this.readLong();
    final int month = this.readInt();

    return new BuySubscriptionResult(passId, month);
  }


  private PayTransactionResult readPayTransaction()
  {
    final long id = this.readLong();
    final long passId = this.readLong();

    return new PayTransactionResult(id, passId);
  }


  private CancelTransactionResult readCancelTransaction()
  {
    final long id = this.readLong();

    return new CancelTransactionResult(id);
  }
}
