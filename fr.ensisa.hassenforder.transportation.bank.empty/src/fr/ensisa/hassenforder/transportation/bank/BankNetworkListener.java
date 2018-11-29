package fr.ensisa.hassenforder.transportation.bank;

public interface BankNetworkListener {

	boolean withdrawByCardId (long id, int amount);

}
