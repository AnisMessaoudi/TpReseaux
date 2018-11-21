package fr.ensisa.hassenforder.transportation.bank.network;

public interface Protocol {

    public static final int BANK_PORT = 6666;
    
    public static final int REQ_BANK_WITHDRAW = 2001;
    
    public static final int REP_OK = 10001;
    public static final int REP_KO = 10002;

}
