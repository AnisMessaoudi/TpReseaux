package fr.ensisa.hassenforder.transportation.terminal.network;


import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;
import fr.ensisa.hassenforder.transportation.terminal.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.model.Ticket;



public class CommandReader extends BasicAbstractReader {

    private Pass pass;
    
    public CommandReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
      type = readInt();
      switch (type) 
      {
        case Protocol.REP_KO :
          break;
        case Protocol.REP_OK :
          break;
        case Protocol.REP_PASS_T :
          readPassReply();
          break;
      }
    }

    public void readPassReply()
    {
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
          Ticket.Month month = Ticket.Month.values()[readInt()];
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
    
    public void readOK()
    {
    }
    
    public void readKO()
    {
    }
    
    public Pass getPass()
    {
      return pass;
    }
}
