package fr.ensisa.hassenforder.transportation.server.network;

import java.io.OutputStream;
import java.util.List;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;
import fr.ensisa.hassenforder.transportation.server.model.Urban;



public class KioskWriter extends BasicAbstractWriter
{
  public KioskWriter(OutputStream outputStream)
  {
    super (outputStream);
  }


  public void writeOkReply()
  {
    this.writeInt(Protocol.REP_OK);
  }


  public void writeKoReply()
  {
    this.writeInt(Protocol.REP_KO);
  }


  public void writeNewPassReply(long passId)
  {
    this.writeInt(Protocol.REP_NEW_PASS);

    this.writeLong(passId);
  }


  public void writePassReply(Pass pass)
  {
    this.writeInt(Protocol.REP_PASS);

    this.writeLong(pass.getPassId());
    this.writeString(pass.getDescription());

    final List<Ticket> tickets = pass.getTickets();
    this.writeInt(tickets.size());

    for (Ticket t: tickets) {
      if (t instanceof Route) {
        this.writeInt(Ticket.Type.ROUTE.ordinal());

        final Route route = (Route) t;

        this.writeString(route.getTicketId());
        this.writeString(route.getFrom());
        this.writeString(route.getTo());
        this.writeInt(-1);
        this.writeInt(route.getCount());
        this.writeInt(route.getUsed());
      } else if (t instanceof Urban) {
        this.writeInt(Ticket.Type.URBAN.ordinal());

        final Urban urban = (Urban) t;

        this.writeString(urban.getTicketId());
        this.writeString("");
        this.writeString("");
        this.writeInt(-1);
        this.writeInt(urban.getCount());
        this.writeInt(urban.getUsed());
      } else {
        this.writeInt(Ticket.Type.SUBSCRIPTION.ordinal());

        final Subscription sub = (Subscription) t;

        this.writeString(sub.getTicketId());
        this.writeString("");
        this.writeString("");
        this.writeInt(sub.getMonth().ordinal());
        this.writeInt(-1);
        this.writeInt(sub.getUsed());
      }
    }
  }


  public void writeTransactionReply(Transaction trans)
  {
    this.writeInt(Protocol.REP_TRANSACTION);

    this.writeLong(trans.getId());
    this.writeInt(trans.getAmount());
  }


  public void writePassIdReply(long passId)
  {
    this.writeInt(Protocol.REP_PASS_ID);

    this.writeLong(passId);
  }
}
