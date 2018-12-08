package fr.ensisa.hassenforder.transportation.server.network;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();

    boolean close ();

	boolean bankWithdraw(long cardId, int amount);
    
}
