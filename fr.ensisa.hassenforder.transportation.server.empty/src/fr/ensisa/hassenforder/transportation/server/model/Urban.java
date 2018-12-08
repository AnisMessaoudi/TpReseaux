package fr.ensisa.hassenforder.transportation.server.model;

public class Urban extends Ticket {

	private int count;
	private int used;
	
	public Urban(long passId, int count) {
		super(passId);
		this.count = count;
		this.used = 0;
	}

	public boolean use (int count) {
		if (this.count < this.used+count) return false;
		this.used += count;
		return true;
	}

	public int cost(Model model) {
		int cost = 0;
		cost += Model.getCost(model.getPrice("urban"));
		cost *= count;
		if (getPassId() == -1) {
			cost += Model.getCost(model.getPrice("pass"));
		}
		return cost;
	}

	public int getCount() {
		return count;
	}

	public int getUsed() {
		return used;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Urban ");
		builder.append(" : ");
		builder.append(used);
		builder.append(" / ");
		builder.append(count);
		return builder.toString();
	}
	
}
