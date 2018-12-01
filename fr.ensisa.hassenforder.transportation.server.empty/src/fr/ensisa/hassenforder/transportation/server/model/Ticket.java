package fr.ensisa.hassenforder.transportation.server.model;

abstract public class Ticket {

	public enum Type {
		VOID,
		ROUTE,
		URBAN,
		SUBSCRIPTION,
		;
	};

	private String ticketId;
	private long passId;
	
	public Ticket(long passId) {
		this.ticketId = null;
		this.passId = passId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public long getPassId() {
		return passId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	abstract public boolean use(int count);
	abstract public int cost (Model model);
}
