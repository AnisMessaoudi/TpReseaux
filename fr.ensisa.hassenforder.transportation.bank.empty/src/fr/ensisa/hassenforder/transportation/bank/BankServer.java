package fr.ensisa.hassenforder.transportation.bank;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.bank.network.Protocol;
import fr.ensisa.hassenforder.transportation.bank.network.BankSession;

public class BankServer extends Thread {

	private ServerSocket server = null;
	private BankNetworkListener listener = null;

	public BankServer(BankNetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.BANK_PORT);
			while (true) {
				Socket connection = server.accept();
				BankSession session = new BankSession (connection, listener);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
