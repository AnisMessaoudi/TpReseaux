package fr.ensisa.hassenforder.transportation.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.network.KioskSession;


public class KioskServer extends Thread {

	private ServerSocket server = null;
	private NetworkListener listener = null;

	public KioskServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.KIOSK_PORT);
			while (true) {
				Socket connection = server.accept();
				KioskSession session = new KioskSession (connection, listener);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
