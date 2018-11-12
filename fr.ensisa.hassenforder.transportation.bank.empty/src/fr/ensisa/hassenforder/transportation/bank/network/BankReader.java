package fr.ensisa.hassenforder.transportation.bank.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

public class BankReader extends BasicAbstractReader {

	public BankReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case 0 : break;
		}
	}

}
