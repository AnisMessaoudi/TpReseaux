package fr.ensisa.hassenforder.transportation.server.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

public class BankSession implements ISession {

    private Socket connection;

    public BankSession() {
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.BANK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

  @Override
  synchronized public boolean bankWithdraw (long cardId, int amount) {
        try {
          final BankWriter writer = new BankWriter(
            connection.getOutputStream()
          );
          final BankReader reader = new BankReader(connection.getInputStream());

          writer.writeBankWithdrawRequest(cardId, amount);
          writer.send();

          reader.receive();
          switch(reader.getType()) {
            case Protocol.REQ_BANK_WITHDRAW:
              final Object result = reader.getResult();

              if (result == null) {
                return false;
              }
              if (result instanceof BankReader.OkResult) {
                return true;
              }
              if (result instanceof BankReader.KoResult) {
                return false;
              }

            default:
              return false;
          }
        } catch (IOException e) {
          return false;
        }
  }

  @Override
  protected void finalize() throws Throwable {
    close();
    super.finalize();
  }

}
