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
      
    }
    
    public void createGetPassbyId(long passId)
    {
      writeInt(Protocol.REQ_GET_PASS_BY_ID_K);
      writeLong(passId);
    }
}
