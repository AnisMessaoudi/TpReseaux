package fr.ensisa.hassenforder.transportation.terminal.ui;

import fr.ensisa.hassenforder.transportation.terminal.model.Ticket;

/**
 *
 * @author Hassenforder
 */
public interface GUIListener {
    
    public void onClickFetch (int clientId);

    public void onClickTicketUse (Ticket ticket, int count);

}
