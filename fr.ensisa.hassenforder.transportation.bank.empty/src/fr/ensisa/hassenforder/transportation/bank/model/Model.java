package fr.ensisa.hassenforder.transportation.bank.model;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author hassenforder
 */
public class Model {

	static long ACCOUNT = 876543210;
	static long CARD = 0;
	private Map<Long, Account> accounts;

	private void add (Account account) {
		accounts.put(account.getCardId(), account);
	}

	private void loadAccounts() {
		accounts = new TreeMap<>();
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.ALLWAYS_CANCEL, "Jonathan Weber", 5000));
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.ALLWAYS_VALID, "Germain Forestier", 5000));
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.REAL, "Michel Hassenforder", 50000));
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.REAL, "Laurent Thiry", 50));
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.REAL, "Philippe Studer", 100));
		add (new Account(ACCOUNT++, ++CARD, Account.Behaviour.REAL, "Jean-Marc Perronne", 500));
	}

	public Map<Long, Account> getAccounts() {
		if (accounts == null) loadAccounts();
		return accounts;
	}

	public Account getAccountByCardId (long cardId) {
		if (getAccounts().containsKey(cardId)) {
			return getAccounts().get(cardId);
		}
		return null;
	}

}
