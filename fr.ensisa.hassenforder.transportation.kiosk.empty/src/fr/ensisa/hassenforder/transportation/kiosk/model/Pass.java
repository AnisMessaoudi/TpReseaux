package fr.ensisa.hassenforder.transportation.kiosk.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hassenforder
 */
public class Pass {
    
	private long passId;
	private String description;
	private List<Ticket> tickets;
    
	public Pass(long passId, String description) {
		super();
		this.passId = passId;
		this.description = description;
	}

	public long getPassId() {
		return passId;
	}

	public String getDescription() {
		return description;
	}

	public List<Ticket> getTickets() {
		if (tickets == null) tickets = new ArrayList<>();
		return tickets;
	}
	
	public void addTicket(Ticket ticket) {
		getTickets().add(ticket);
	}

}
