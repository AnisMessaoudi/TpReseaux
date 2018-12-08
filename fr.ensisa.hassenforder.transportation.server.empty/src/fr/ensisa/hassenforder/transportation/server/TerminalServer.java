package fr.ensisa.hassenforder.transportation.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.server.network.TerminalSession;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class TerminalServer extends Thread {

	private ServerSocket server = null;
	private NetworkListener listener = null;

	public TerminalServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.TERMINAL_PORT);
			while (true) {
				Socket connection = server.accept();
				TerminalSession session = new TerminalSession (connection, listener);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
