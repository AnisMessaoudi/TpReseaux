package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;

public class KioskWriter extends BasicAbstractWriter {

    public KioskWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void createNewPass()
    {
      writeInt(Protocol.REQ_NEW_PASS);
    }
    
    public void createGetPassbyId(long passId)
    {
      writeInt(Protocol.REQ_GET_PASS_BY_ID);
      writeLong(passId);
    }
    
    public void createBuyRoute(long passId, String from, String to, int count)
    {
      writeInt(Protocol.REQ_BUY_ROUTE);
      writeLong(passId);
      writeString(from);
      writeString(to);
      writeInt(count);
    }
    
    public void createBuyUrban(long passId, int count)
    {
      writeInt(Protocol.REQ_BUY_URBAN);
      writeLong(passId);
      writeInt(count);
    }
    
    public void createBuySubscription(long passId, int month)
    {
      writeInt(Protocol.REQ_BUY_SUBSCRIPTION);
      writeLong(passId);
      writeInt(month);
    }
    
    public void createPayTransaction(long id, long cardId)
    {
      writeInt(Protocol.REQ_PAY_TRANSACTION);
      writeLong(id);
      writeLong(cardId);
    }
    
    public void createCancelTransaction(long id)
    {
      writeInt(Protocol.REQ_CANCEL_TRANSACTION);
      writeLong(id);
    }
}
