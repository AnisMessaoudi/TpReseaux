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
        default:
          return false;

        case Protocol.REQ_NEW_PASS:
          this.handleNewPassRequest(writer);
          break;

        case Protocol.REQ_GET_PASS_BY_ID:
          this.handleGetPassByIdRequest(
            writer, (KioskReader.GetPassByIdResult) reader.extractResult()
          );
          break;

        case Protocol.REQ_BUY_ROUTE:
          this.handleBuyRouteRequest(
            writer, (KioskReader.BuyRouteResult) reader.extractResult()
          );
          break;

        case Protocol.REQ_BUY_URBAN:
          this.handleBuyUrbanRequest(
            writer, (KioskReader.BuyUrbanResult) reader.extractResult()
          );
          break;

        case Protocol.REQ_BUY_SUBSCRIPTION:
          this.handleBuySubscriptionRequest(
            writer, (KioskReader.BuySubscriptionResult) reader.extractResult()
          );
          break;

        case Protocol.REQ_PAY_TRANSACTION:
          this.handlePayTransactionRequest(
            writer, (KioskReader.PayTransactionResult) reader.extractResult()
          );

        case Protocol.REQ_CANCEL_TRANSACTION:
          this.handleCancelTransactionRequest(
            writer, (KioskReader.CancelTransactionResult) reader.extractResult()
          );
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
    KioskWriter writer, KioskReader.GetPassByIdResult result
  )
  {
    if (result != null) {
      final Pass pass = this.listener.kioskFetchPass(result.passId);

      if (pass != null) {
        writer.writePassReply(pass);
        return;
      }
    }

    writer.writeKoReply();
  }


  private void handleBuyRouteRequest(
    KioskWriter writer, KioskReader.BuyRouteResult result
  )
  {
    if (result != null) {
      final Route route = new Route(
        result.passId, result.from, result.to, result.count
      );

      if (result.count > 0) {
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
    KioskWriter writer, KioskReader.BuyUrbanResult result
  )
  {
    if (result != null) {
      final Urban urban = new Urban(result.passId, result.count);

      if (result.count > 0) {
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
    KioskWriter writer, KioskReader.BuySubscriptionResult result
  )
  {
    if (result != null) {
      final Subscription.Month[] months = Subscription.Month.values();

      if (result.month >= 0 && result.month < months.length) {
        final Subscription urban = new Subscription(
          result.passId, months[result.month]
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
    KioskWriter writer, KioskReader.PayTransactionResult result
  )
  {
    if (result != null) {
      final long transResult = this.listener.kioskPayTransaction(
        result.id, result.passId
      );

      if (transResult >= 0) {
        writer.writePassIdReply(transResult);
        return;
      }
    }

    writer.writeKoReply();
  }


  private void handleCancelTransactionRequest(
    KioskWriter writer, KioskReader.CancelTransactionResult result
  )
  {
    if (result != null) {
      if (this.listener.kioskCancelTransaction(result.id)) {
        writer.writeOkReply();
        return;
      }
    }

    writer.writeKoReply();
  }
}
