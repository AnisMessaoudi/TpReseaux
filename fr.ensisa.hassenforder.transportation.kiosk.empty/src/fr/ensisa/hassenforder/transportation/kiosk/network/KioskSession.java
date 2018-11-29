package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskSession implements ISession
{

  private Socket connection;

  public KioskSession()
  {
  }

  @Override
  synchronized public boolean close()
  {
    try
    {
      if (connection != null)
      {
        connection.close();
      }
      connection = null;
    } catch (IOException e)
    {
    }
    return true;
  }

  @Override
  synchronized public boolean open()
  {
    this.close();
    try
    {
      connection = new Socket("localhost", Protocol.KIOSK_PORT);
      return true;
    } catch (IOException e)
    {
      return false;
    }
  }

  @Override
  public long createPass()
  {
    try
    {
      KioskWriter writer= new KioskWriter(connection.getOutputStream());
      KioskReader reader= new KioskReader(connection.getInputStream());
      writer.createNewPass();
      writer.send();
      
      reader.receive();
      switch(reader.getType())
      {

        case Protocol.REP_NEW_PASS :
          return reader.getPassId();
          
        case Protocol.REP_KO : 
          return -1L;
          
        default:
          return -1L;
      }
    } catch (IOException e)
    {
      return -1L;
    }
  }

  @Override
  public Pass getPassById(long passId)
  {
    try
    {
      KioskWriter writer= new KioskWriter(connection.getOutputStream());
      KioskReader reader= new KioskReader(connection.getInputStream());
      writer.createGetPassbyId(passId);
      writer.send();
      
      reader.receive();
      switch(reader.getType())
      {
        case Protocol.REP_PASS :
          return reader.getPass();
          
        case Protocol.REP_KO : 
          return null;
          
        default:
          return null;
      }
    } catch (IOException e)
    {
      return null;
    }
  }

  @Override
  public Transaction buyRoute(long passId, String from, String to, int count)
  {
    try
    {
      KioskWriter writer= new KioskWriter(connection.getOutputStream());
      KioskReader reader= new KioskReader(connection.getInputStream());
      writer.createBuyRoute(passId, from, to, count);
      writer.send();
      
      reader.receive();
      switch (reader.getType())
      {
      case Protocol.REP_TRANSACTION:
        return reader.getTransaction();

      case Protocol.REP_KO:
        return null;

      default:
        return null;
      }
    } catch (IOException e)
    {
      return null;
    }
  }

  @Override
  public Transaction buyUrban(long passId, int count)
  {
    try
    {
      KioskWriter writer= new KioskWriter(connection.getOutputStream());
      KioskReader reader= new KioskReader(connection.getInputStream());
      writer.createBuyUrban(passId, count);
      writer.send();
      
      reader.receive();
      switch(reader.getType())
      {
      case Protocol.REP_TRANSACTION:
        return reader.getTransaction();

      case Protocol.REP_KO:
        return null;

      default:
        return null;
      }
    } catch (IOException e)
    {
      return null;
    }
  }

  @Override
  public Transaction buySubscription(long passId, int month)
  {
    try
    {
      KioskWriter writer= new KioskWriter(connection.getOutputStream());
      KioskReader reader= new KioskReader(connection.getInputStream());
      writer.createBuySubscription(passId, month);
      writer.send();
      
      reader.receive();
      switch(reader.getType())
      {
        case Protocol.REP_TRANSACTION :
          return reader.getTransaction();
          
        case Protocol.REP_KO : 
          return null;
          
        default:
          return null;
            
      }
    } catch (IOException e)
    {
      return null;
    }
  }

  @Override
  public boolean cancelTransaction(long id)
  {
    try
    {
      KioskWriter writer = new KioskWriter(connection.getOutputStream());
      KioskReader reader = new KioskReader(connection.getInputStream());
      writer.createCancelTransaction(id);
      writer.send();

      reader.receive();
      switch (reader.getType())
      {
      case Protocol.REP_KO:
        return true;
        
      case Protocol.REP_OK:
        return true;
        
      default:
        return false;
      }
    } catch (IOException e)
    {
      return false;
    }
  }

  @Override
  public long payTransaction(long id, long cardId)
  {
    try
    {
      KioskWriter writer = new KioskWriter(connection.getOutputStream());
      KioskReader reader = new KioskReader(connection.getInputStream());
      writer.createPayTransaction(id, cardId);
      writer.send();

      reader.receive();
      switch (reader.getType())
      {
      case Protocol.REP_PASS_ID:
        return reader.getPassId();

      default:
        return -1L;
      }
    } catch (IOException e)
    {
      return -1L;
    }
  }
}
