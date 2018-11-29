package fr.ensisa.hassenforder.transportation.server.model;

public class Transaction {

	private long id;
	private Ticket ticket;
	private int amount;
	private boolean done;
	
	public Transaction(Ticket ticket, int amount) {
		super();
		this.id = -1;
		this.ticket = ticket;
		this.amount = amount;
		this.done = false;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone() {
		this.done = true;
	}

	public long getId() {
		return id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public int getAmount() {
		return amount;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
