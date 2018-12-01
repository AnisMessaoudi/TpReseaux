package fr.ensisa.hassenforder.transportation.server.model;

public class Route extends Ticket {

	private String from;
	private String to;
	private int count;
	private int used;
	
	public Route(long passId, String from, String to, int count) {
		super(passId);
		this.from = from;
		this.to = to;
		this.count = count;
		this.used = 0;
	}
	
	public boolean use (int count) {
		if (this.count < this.used+count) return false;
		this.used += count;
		return true;
	}

	@Override
	public int cost(Model model) {
		int cost = 0;
		int fromLine = model.getPrice (getFrom());
		int toLine = model.getPrice (getTo());
		if (Model.getLine(fromLine) == Model.getLine(toLine)) {
			// same line it is : absolute of the difference
			cost += Math.abs(Model.getCost(fromLine) - Model.getCost(toLine));
		} else {
			// different lines so it is : from -> Mulhouse -> to
			// just add each costs
			cost += Model.getCost(fromLine);
			cost += Model.getCost(toLine);
		}
		cost *= count;
		if (getPassId() == -1) {
			cost += Model.getCost(model.getPrice("pass"));
		}
		return cost;
	}
	
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
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
		builder.append("Route ");
		builder.append(" ");
		builder.append(from);
		builder.append("->");
		builder.append(to);
		builder.append(" ");
		builder.append(" : ");
		builder.append(used);
		builder.append(" / ");
		builder.append(count);
		return builder.toString();
	}

}
