package fr.ensisa.hassenforder.transportation.terminal.network;

public class Protocol {

    public static final int TERMINAL_PORT = 8888;

    public static final int REQ_GET_PASS_BY_ID = 1;
    public static final int REQ_USE_TICKET = 2;
    
    
    public static final int REP_OK = 10001;
    public static final int REP_KO = 10002;
    public static final int REP_PASS = 11001;

}
