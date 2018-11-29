package fr.ensisa.hassenforder.transportation.server.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

public class BankWriter extends BasicAbstractWriter
{
  public BankWriter(OutputStream outputStream)
  {
    super(outputStream);
  }



  public void writeBankWithdrawRequest(long cardId, int amount)
  {
    this.writeInt(Protocol.REQ_BANK_WITHDRAW);

    this.writeLong(cardId);
    this.writeInt(amount);
  }
}
