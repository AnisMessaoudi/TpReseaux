package fr.ensisa.hassenforder.transportation.server.model;

public class Subscription extends Ticket {

	public enum Month {
		JANUARY, FEBRUARY, MARCH, APRIL,
		MAY, JUNE, JULY, AUGUST,
		SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
		;
	};
	private Month month;
	private int used;
	
	public Subscription(long passId, Month month) {
		super(passId);
		this.month = month;
		this.used = 0;
	}

	public boolean use (int count) {
		this.used += count;
		return true;
	}

	public int cost(Model model) {
		int cost = 0;
		cost += Model.getCost(model.getPrice("month"));
		if (getPassId() == -1) {
			cost += Model.getCost(model.getPrice("pass"));
		}
		return cost;
	}

	public Month getMonth() {
		return month;
	}

	public int getUsed() {
		return used;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subscription ");
		builder.append(" ");
		builder.append(month.toString().toLowerCase());
		builder.append(" ");
		builder.append(" : ");
		builder.append(used);
		builder.append(" / ");
		builder.append("infinity");
		return builder.toString();
	}
	
}
