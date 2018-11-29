package fr.ensisa.hassenforder.transportation.kiosk.ui;

import fr.ensisa.hassenforder.transportation.kiosk.model.Ticket;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

/**
 *
 * @author Hassenforder
 */
public interface GUIListener {
    
	public void onClickCreatePass();

	public void onClickFetch(long passId);

	public void onClickBuyRoute(long passId, String from, String to, int count);

	public void onClickBuyUrban(long passId, int count);

	public void onClickBuySubscription(long passId, int month);

	// do not implement
	public void onClickTicketUse(Ticket ticket, int count);

	// transaction validated locally
	public void onValidTransaction(Transaction transaction, String cardId);

	// transaction canceled locally
	public void onCancelTransaction(Transaction transaction);

}
