package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Ticket;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskReader extends BasicAbstractReader {

    private Pass pass;
    private long passId;
    private Transaction transaction;

    public KioskReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type)
        {
        case Protocol.REP_OK:
          break;
        case Protocol.REP_KO:
          break;
        case Protocol.REP_PASS :
          readPassReply();
          break;
        case Protocol.REP_NEW_PASS :
          readNewPassReply();
          break;
        case Protocol.REP_TRANSACTION :
          readTransactionReply();
          break;
        case Protocol.REP_PASS_ID :
          readPassIdReply();
          break;
        }
    }

    private void readPassReply()
    {
      Ticket.Month month=null;
      
      long passId = readLong();
      String description = readString();
      pass = new Pass(passId, description);
      int size = readInt();
      if (size != 0 )
      {
        for (int i = 0; i < size; i++) 
        {
          Ticket.Type type = Ticket.Type.values() [readInt()];
          String id = readString();
          String from = readString();
          String to = readString();
          int j=readInt();
          if( j != -1) {
             month = Ticket.Month.values()[j];
          }
          int count = readInt();
          int used = readInt();
          switch (type)
          {
          case ROUTE :
            pass.addTicket(new Ticket(id,from,to,count,used));
            break;
          case URBAN : 
            pass.addTicket(new Ticket(id,count,used));
            break;
          case SUBSCRIPTION : 
            pass.addTicket(new Ticket(id,month,used));
            break;
          default:
            break;
          }
        }
      }
      
    }


    private void readNewPassReply()
    {
      passId = readLong();
      
    }

    private void readTransactionReply()
    {
      long id= readLong();
      int amount = readInt();
      transaction = new Transaction(id, amount);
    }

    private void readPassIdReply()
    {
      passId= readLong();
    }
    

    public Pass getPass() {
      return pass;
    }
    
    public long getPassId() {
      return passId;
    }
    
    public Transaction getTransaction() {
      return transaction;
    }

}
