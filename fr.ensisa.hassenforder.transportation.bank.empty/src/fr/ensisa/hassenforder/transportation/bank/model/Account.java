package fr.ensisa.hassenforder.transportation.bank.model;

/**
 *
 * @author hassenforder
 */
public class Account {

	public enum Behaviour {
		ALLWAYS_VALID,
		ALLWAYS_CANCEL,
		REAL,
		;
	};
	private long id;
	private long cardId;
	private Behaviour behaviour;
	private String owner;
	private int amount;

	public Account(long id, long cardId, Behaviour behaviour, String owner, int amount) {
		super();
		this.id = id;
		this.cardId = cardId;
		this.behaviour = behaviour;
		this.owner = owner;
		this.amount = amount;
	}

	public boolean withdraw (int amount) {
		System.out.print("withdrawal : ");
		System.out.print(toString());
		System.out.println();
		switch (behaviour) {
		case ALLWAYS_CANCEL : return false;
		case ALLWAYS_VALID : return true;
		case REAL :
			if (this.amount >= amount) {
				this.amount -= amount;
				return true;
			} else {
				return false;
			}
		default: return false;
		}
	}

	public long getCardId() {
		return cardId;
	}

	public String getOwner() {
		return owner;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account ");
		builder.append(id);
		builder.append(" / ");
		builder.append(behaviour.toString());
		builder.append(" - ");
		builder.append(cardId);
		builder.append(" - ");
		builder.append(owner);
		builder.append(" = ");
		builder.append(amount);
		builder.append(" €");
		return builder.toString();
	}
	
}
