package fr.ensisa.hassenforder.transportation.server;

import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;

public interface NetworkListener {

	long kioskCreatePass();

	Pass kioskFetchPass(long passId);

	Transaction kioskCreateTransaction(Ticket ticket);

	boolean kioskCancelTransaction(long transactionId);

	long kioskPayTransaction(long transactionId, long cardId);

	boolean kioskAddTicket(Ticket ticket);

	Pass terminalFetchPass(long passId);

	boolean terminalUseTicket (long passId, String ticketId, int count);

}
