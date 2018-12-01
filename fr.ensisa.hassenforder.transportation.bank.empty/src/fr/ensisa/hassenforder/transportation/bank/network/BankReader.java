package fr.ensisa.hassenforder.transportation.bank.network;



import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.bank.network.Protocol;



public class BankReader extends BasicAbstractReader
{
  public static class BankWithdrawRequest
  {
    public final long cardId;
    public final int amount;

    private BankWithdrawRequest(long cardId, int amount)
    {
      super();
      this.cardId = cardId;
      this.amount = amount;
    }
  }



  private Object request = null;



  public BankReader(InputStream inputStream)
  {
    super (inputStream);
  }



  public void receive()
  {
    type = readInt();
    switch (type) {
      default:
        break;

      case Protocol.REQ_BANK_WITHDRAW:
        this.request = this.readBankWithdrawRequest();
        break;
    }
  }


  public Object extractRequest()
  {
    final Object request = this.request;

    this.request = null;

    return request;
  }


  private BankWithdrawRequest readBankWithdrawRequest()
  {
    final long cardId = this.readLong();
    final int amount = this.readInt();

    return new BankWithdrawRequest(cardId, amount);
  }
}
