package fr.ensisa.hassenforder.transportation.server.model;

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
    
	public Pass(String description) {
		super();
		this.passId = -1;
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

	public boolean useTicket (Ticket ticket, int count) {
		if (getTickets().isEmpty()) return false;
		if (! getTickets().contains(ticket)) return false;
		return ticket.use (count);
	}

	public void setPassId(long passId) {
		this.passId = passId;
	}

}
