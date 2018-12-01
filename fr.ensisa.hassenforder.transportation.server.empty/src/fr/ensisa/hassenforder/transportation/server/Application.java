package fr.ensisa.hassenforder.transportation.server;

import fr.ensisa.hassenforder.transportation.server.model.Model;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.server.model.Ticket;
import fr.ensisa.hassenforder.transportation.server.model.Transaction;
import fr.ensisa.hassenforder.transportation.server.network.BankSession;

public class Application implements NetworkListener {

	private KioskServer kiosk = null;
	private TerminalServer terminal = null;
	private Model model = null;
	
	public void start () {
		model = new Model ();
		model.populate();
		kiosk = new KioskServer(this);
		kiosk.start();
		terminal = new TerminalServer(this);
		terminal.start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application m = new Application ();
		m.start();
	}

	@Override
	public long kioskCreatePass() {
		Pass pass = new Pass ("created by nobody");
		model.addPass (pass);
		return pass.getPassId();
	}

	@Override
	public Pass kioskFetchPass(long passId) {
		return model.getPass (passId);
	}

	@Override
	public boolean kioskAddTicket(Ticket ticket) {
		return model.addTicket(ticket);
	}

	@Override
	public Transaction kioskCreateTransaction(Ticket ticket) {
		int amount = ticket.cost(model);
		Transaction transaction = new Transaction (ticket, amount);
		model.addTransaction (transaction);
		return transaction;
	}

	@Override
	public boolean kioskCancelTransaction(long transactionId) {
		return model.cancelTransaction (transactionId);
	}

	@Override
	public long kioskPayTransaction(long transactionId, long cardId) {
		BankSession bank = new BankSession ();
		Transaction transaction = model.getTransaction (transactionId);
		boolean ok = bank.bankWithdraw(cardId, transaction.getAmount());
		if (ok) {
			return model.validTransaction(transactionId);
		} else {
			model.cancelTransaction(transactionId);
			return -1;
		}
	}

	@Override
	public Pass terminalFetchPass(long passId) {
		return model.getPass (passId);
	}

	@Override
	public boolean terminalUseTicket(long passId, String ticketId, int count) {
		return model.useTicket(passId, ticketId, count);
	}

}
