package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Route;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Urban;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;

public class KioskSession extends Thread
{
  private Socket connection;
  private NetworkListener listener;


  public KioskSession(Socket connection, NetworkListener listener)
  {
    this.connection = connection;
    this.listener = listener;
    if( listener == null) throw new RuntimeException("listener cannot be null");
  }


  public void close ()
  {
    this.interrupt();
    try {
      if (connection != null)
        connection.close();
    } catch (IOException e) {
    }
    connection = null;
  }


  public boolean operate()
  {
    try {
      KioskWriter writer = new KioskWriter(connection.getOutputStream());
      KioskReader reader = new KioskReader(connection.getInputStream());

      reader.receive();
      switch (reader.getType()) {
        case 0:
          return false; // socket closed

        default:
          writer.writeKoReply();
          break;

        case Protocol.REQ_NEW_PASS:
          this.handleNewPassRequest(writer);
          break;

        case Protocol.REQ_GET_PASS_BY_ID:
          this.handleGetPassByIdRequest(
            writer, (KioskReader.GetPassByIdRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_BUY_ROUTE:
          this.handleBuyRouteRequest(
            writer, (KioskReader.BuyRouteRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_BUY_URBAN:
          this.handleBuyUrbanRequest(
            writer, (KioskReader.BuyUrbanRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_BUY_SUBSCRIPTION:
          this.handleBuySubscriptionRequest(
            writer, (KioskReader.BuySubscriptionRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_PAY_TRANSACTION:
          this.handlePayTransactionRequest(
            writer, (KioskReader.PayTransactionRequest) reader.extractRequest()
          );
          break;

        case Protocol.REQ_CANCEL_TRANSACTION:
          this.handleCancelTransactionRequest(
            writer,
            (KioskReader.CancelTransactionRequest) reader.extractRequest()
          );
          break;
      }

      writer.send();
      return true;
    } catch (IOException e) {
      return false;
    }
  }


  public void run() {
    while (true) {
      if (! operate())
        break;
    }
    try {
      if (connection != null) connection.close();
    } catch (IOException e) {
    }
  }



  private void handleNewPassRequest(KioskWriter writer)
  {
    final long passId = this.listener.kioskCreatePass();

    if (passId > 0) {
      writer.writeNewPassReply(passId);
    } else {
      writer.writeKoReply();
    }
  }


  private void handleGetPassByIdRequest(
    KioskWriter writer, KioskReader.GetPassByIdRequest request
  )
  {
    if (request != null) {
      final Pass pass = this.listener.kioskFetchPass(request.passId);

      if (pass != null) {
        writer.writePassReply(pass);
        return;
      }
    }

    writer.writeKoReply();
  }


  private void handleBuyRouteRequest(
    KioskWriter writer, KioskReader.BuyRouteRequest request
  )
  {
    if (request != null) {
      final Route route = new Route(
        request.passId, request.from, request.to, request.count
      );

      if (request.count > 0) {
        final Transaction trans = this.listener.kioskCreateTransaction(route);

        if (trans != null) {
          writer.writeTransactionReply(trans);
          return;
        }
      }
    }

    writer.writeKoReply();
  }


  private void handleBuyUrbanRequest(
    KioskWriter writer, KioskReader.BuyUrbanRequest request
  )
  {
    if (request != null) {
      final Urban urban = new Urban(request.passId, request.count);

      if (request.count > 0) {
        final Transaction trans = this.listener.kioskCreateTransaction(urban);

        if (trans != null) {
          writer.writeTransactionReply(trans);
          return;
        }
      }
    }

    writer.writeKoReply();
  }


  private void handleBuySubscriptionRequest(
    KioskWriter writer, KioskReader.BuySubscriptionRequest request
  )
  {
    if (request != null) {
      final Subscription.Month[] months = Subscription.Month.values();

      if (request.month >= 0 && request.month < months.length) {
        final Subscription urban = new Subscription(
          request.passId, months[request.month]
        );

        if (urban != null) {
          final Transaction trans = this.listener.kioskCreateTransaction(urban);

          if (trans != null) {
            writer.writeTransactionReply(trans);
            return;
          }
        }
      }
    }

    writer.writeKoReply();
  }


  private void handlePayTransactionRequest(
    KioskWriter writer, KioskReader.PayTransactionRequest request
  )
  {
    if (request != null) {
      final long passId = this.listener.kioskPayTransaction(
        request.id, request.passId
      );

      if (passId >= 0) {
        writer.writePassIdReply(passId);
        return;
      }
    }

    writer.writeKoReply();
  }


  private void handleCancelTransactionRequest(
    KioskWriter writer, KioskReader.CancelTransactionRequest request
  )
  {
    if (request != null) {
      if (this.listener.kioskCancelTransaction(request.id)) {
        writer.writeOkReply();
        return;
      }
    }

    writer.writeKoReply();
  }
}
