package fr.ensisa.hassenforder.transportation.bank;

import fr.ensisa.hassenforder.transportation.bank.model.Account;
import fr.ensisa.hassenforder.transportation.bank.model.Model;

public class Bank implements BankNetworkListener {

	private BankServer commands = null;
	private Model model = null;
	
	public void start () {
		model = new Model ();
		commands = new BankServer(this);
		commands.start();
	}
	
	@Override
	public boolean withdrawByCardId(long cardId, int amount) {
		Account account = model.getAccountByCardId(cardId);
		if (account == null) return false;
		return account.withdraw(amount);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bank m = new Bank ();
		m.start();
	}

}
