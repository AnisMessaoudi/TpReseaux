package fr.ensisa.hassenforder.transportation.terminal.network;

import fr.ensisa.hassenforder.transportation.terminal.model.Pass;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();

    boolean close ();

    Pass getPassById (long passId);
    
	boolean useTicket(String ticketId, int count);
    
}
