package fr.ensisa.hassenforder.transportation.server.model;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.ensisa.hassenforder.transportation.server.model.Subscription.Month;

/**
 *
 * @author hassenforder
 */
public class Model {

	private Map<Long, Pass> passes;
	private Map<String, Ticket> tickets;
	private Map<Long, Transaction> transactions;
	private Map<String, Integer> prices;
	
	public static final int LINE	= 0x0FFF0000;
	public static final int COST	= 0x0000FFFF;

	public void populate () {
		prices = new TreeMap<String, Integer>();
		prices.put ("Altkirch",		(1 << 16) |  4);
		prices.put ("Bâle",			(2 << 16) |  8);
		prices.put ("Belfort",		(1 << 16) | 10);
		prices.put ("Cernay",		(4 << 16) |  4);
		prices.put ("Colmar",		(3 << 16) |  9);
		prices.put ("Mulhouse",		(1 << 16) |  0);
		prices.put ("Saint Louis",	(2 << 16) |  6);
		prices.put ("Strasbourg",	(3 << 16) | 20);
		prices.put ("Thann",		(4 << 16) |  8);
		prices.put ("pass", 		             1);
		prices.put ("urban", 		             2);
		prices.put ("month", 		           200);
		{
			Pass pass1 = new Pass ("1 route ticket one way");
			addPass (pass1);
			Ticket ticket1_1 = new Route (pass1.getPassId(), "Mulhouse", "Strasbourg", 1);
			addTicket(ticket1_1);
			pass1.addTicket(ticket1_1);
		}
		{
			Pass pass2 = new Pass ("1 route ticket two ways");
			addPass (pass2);
			Ticket ticket2_1 = new Route (pass2.getPassId(), "Mulhouse", "Bâle", 2);
			addTicket(ticket2_1);
			pass2.addTicket(ticket2_1);
		}
		{
			Pass pass3 = new Pass ("1 urban ticket one way");
			addPass (pass3);
			Ticket ticket3_1 = new Urban (pass3.getPassId(), 1);
			addTicket(ticket3_1);
			pass3.addTicket(ticket3_1);
		}
		{
			Pass pass4 = new Pass ("1 urban ticket two ways");
			addPass (pass4);
			Ticket ticket4_1 = new Urban (pass4.getPassId(), 2);
			addTicket(ticket4_1);
			pass4.addTicket(ticket4_1);
		}
		{
			Pass pass5 = new Pass ("1 month ticket");
			addPass (pass5);
			Ticket ticket5_1 = new Subscription (pass5.getPassId(), Subscription.Month.JANUARY);
			addTicket(ticket5_1);
			pass5.addTicket(ticket5_1);
		}
		{
			Pass pass6 = new Pass ("2 route tickets one way");
			addPass (pass6);
			Ticket ticket6_1 = new Route (pass6.getPassId(), "Bâle", "Colmar", 1);
			addTicket(ticket6_1);
			pass6.addTicket(ticket6_1);
			Ticket ticket6_2 = new Route (pass6.getPassId(), "Thann", "Cernay", 1);
			addTicket(ticket6_2);
			pass6.addTicket(ticket6_2);
		}
		{
			Pass pass7 = new Pass ("2 urban tickets one way");
			addPass (pass7);
			Ticket ticket7_1 = new Route (pass7.getPassId(), "Thann", "Saint Louis", 10);
			addTicket(ticket7_1);
			pass7.addTicket(ticket7_1);
			Ticket ticket7_2 = new Route (pass7.getPassId(), "Colmar", "Strasbourg", 1);
			addTicket(ticket7_2);
			pass7.addTicket(ticket7_2);
		}
	}

	private Map<Long, Pass> getPasses () {
		if (passes == null) passes = new TreeMap<Long, Pass>();
		return passes;
	}

	static private long PASS_ID = 0L;
	public boolean addPass(Pass pass) {
		pass.setPassId(++PASS_ID);
		getPasses().put (pass.getPassId(), pass);
		return true;
	}

	public Pass getPass(long passId) {
		return getPasses().get(passId);
	}

	private Map<String, Ticket> getTickets () {
		if (tickets == null) tickets = new TreeMap<String, Ticket>();
		return tickets;
	}

	public boolean addTicket(Ticket ticket) {
		String id = UUID.randomUUID().toString();
		ticket.setTicketId(id);
		getTickets().put (ticket.getTicketId(), ticket);
		return true;
	}

	private Map<Long, Transaction> getTransactions () {
		if (transactions == null) transactions = new TreeMap<Long, Transaction>();
		return transactions;
	}

	public Transaction getTransaction(long transactionId) {
		return getTransactions().get(transactionId);
	}

	public boolean addTransaction (Transaction transaction) {
		long time = new Date().getTime();
		transaction.setId(time);
		getTransactions().put (transaction.getId(), transaction);
		return true;
	}

	public boolean cancelTransaction(long transactionId) {
		if (! getTransactions().containsKey(transactionId)) return false;
		getTransactions().remove(transactionId);
		return true;
	}

	public long validTransaction(long transactionId) {
		if (! getTransactions().containsKey(transactionId)) return -1;
		Transaction transaction = getTransactions().remove(transactionId);
		Ticket ticket = transaction.getTicket();
		if (! getPasses().containsKey(ticket.getPassId())) {
			Pass pass = new Pass ("autocreated pass");
			addPass (pass);
			pass.addTicket(ticket);
			return pass.getPassId();
		} else {
			Pass pass = getPasses().get(ticket.getPassId());
			pass.addTicket(ticket);
			return 0;
		}
	}

	public boolean useTicket(long passId, String ticketId, int count) {
		if (! getTickets().containsKey(ticketId)) return false;
		Ticket ticket = getTickets().get(ticketId);
		if (ticket.getPassId() != passId) return false;
		return ticket.use(count);
	}

	public int getPrice(String name) {
		return prices.get(name);
	}

	static int getLine (int price) {
		return price & LINE;
	}

	static int getCost (int price) {
		return price & COST;
	}

}
