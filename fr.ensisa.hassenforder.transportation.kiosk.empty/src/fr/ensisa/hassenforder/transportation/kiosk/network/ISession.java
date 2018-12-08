package fr.ensisa.hassenforder.transportation.kiosk.network;

import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();

    boolean close ();

	long createPass();

	Pass getPassById(long passId);

	Transaction buyRoute(long passId, String from, String to, int count);

	Transaction buyUrban(long passId, int count);

	Transaction buySubscription(long passId, int month);

	long payTransaction(long id, long cardId);
    
	boolean cancelTransaction(long id);

}
