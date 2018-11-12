package fr.ensisa.hassenforder.transportation.kiosk.model;

public class Transaction {

	private long id;
	private int amount;
	
	public Transaction(long id, int amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
	
	public long getId() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}

}
