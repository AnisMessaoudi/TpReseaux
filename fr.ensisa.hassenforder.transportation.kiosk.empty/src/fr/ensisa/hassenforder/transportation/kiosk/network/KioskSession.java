package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskSession implements ISession {

    private Socket connection;

    public KioskSession() {
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.KIOSK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	public long createPass() {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return -1L;
        } catch (IOException e) {
            return -1L;
        }
	}

	@Override
	public Pass getPassById(long passId) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buyRoute(long passId, String from, String to, int count) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buyUrban(long passId, int count) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public Transaction buySubscription(long passId, int month) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
            return null;
        } catch (IOException e) {
            return null;
        }
	}

	@Override
	public boolean cancelTransaction(long id) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
    		return false;
        } catch (IOException e) {
    		return false;
        }
	}

	@Override
	public long payTransaction(long id, long cardId) {
        try {
        	if (true != Boolean.TRUE) throw new IOException ();
    		return -1;
        } catch (IOException e) {
    		return -1;
        }
	}

 }
