package fr.ensisa.hassenforder.transportation.terminal.model;

public class Ticket {

	public enum Type {
		VOID,
		ROUTE,
		URBAN,
		SUBSCRIPTION,
		;
	};
	public enum Month {
		JANUARY, FEBRUARY, MARCH, APRIL,
		MAY, JUNE, JULY, AUGUST,
		SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
		;
	};
	private Type type;
	private String id;
	private String from;
	private String to;
	private Month month;
	private int count;
	private int used;
	
	public Ticket(String id, String from, String to, int count, int used) {
		super();
		this.type = Type.ROUTE;
		this.id = id;
		this.from = from;
		this.to = to;
		this.month = null;
		this.count = count;
		this.used = used;
	}
	
	public Ticket(String id, int count, int used) {
		super();
		this.type = Type.URBAN;
		this.id = id;
		this.from = "";
		this.to = "";
		this.month = null;
		this.count = count;
		this.used = used;
	}

	public Ticket(String id, Month month, int used) {
		super();
		this.type = Type.SUBSCRIPTION;
		this.id = id;
		this.from = "";
		this.to = "";
		this.month = month;
		this.used = used;
	}

	public Type getType() {
		return type;
	}

	public String getId() {
		return id;
	}
	
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public Month getMonth() {
		return month;
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
		builder.append("Travel ");
		builder.append(id);
		builder.append(" ");
		builder.append(type.toString());
		switch (type) {
		case ROUTE : 
			builder.append(" ");
			builder.append(from);
			builder.append("->");
			builder.append(to);
			builder.append(" ");
			break;
		case URBAN :
			builder.append(" urban ");
			break;
		case SUBSCRIPTION:
			builder.append(" ");
			builder.append(month.toString().toLowerCase());
			builder.append(" ");
			break;
		}
		builder.append(" : ");
		builder.append(used);
		builder.append(" / ");
		builder.append(count);
		return builder.toString();
	}

}
