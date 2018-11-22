package fr.ensisa.hassenforder.transportation.server.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

public class BankReader extends BasicAbstractReader
{
  public static class OkResult
  {
  }


  public static class KoResult
  {
  }



  private Object result = null;



  public BankReader(InputStream inputStream)
  {
    super(inputStream);
  }



  public void receive()
  {
    type = readInt();
    switch (type) {
      default:
        break;

      case Protocol.REP_OK:
        this.result = this.readOk();

      case Protocol.REP_KO:
        this.result = this.readKo();
    }
  }



  public Object retrieveResult()
  {
    final Object result = this.result;

    this.result = null;

    return result;
  }



  private OkResult readOk()
  {
    return new OkResult();
  }


  private KoResult readKo()
  {
    return new KoResult();
  }
}
