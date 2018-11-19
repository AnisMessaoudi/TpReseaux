package fr.ensisa.hassenforder.transportation.terminal.network;

public class Protocol {

    public static final int TERMINAL_PORT = 8888;

    public static final int REQ_GET_PASS_BY_ID_T = 1;
    public static final int REQ_USE_TICKET = 2;
    
    public static final int REQ_CREATE_PASS = 1001;
    public static final int REQ_GET_PASS_BY_ID_K = 1002;
    public static final int REQ_BUY_ROUTE = 1003;
    public static final int REQ_BUY_URBAN = 1004;
    public static final int REQ_BUY_SUBSCRIPTION = 1005;
    public static final int REQ_PAY_TRANSACTION = 1006;
    public static final int REQ_CANCEL_TRANSACTION = 1007;
    
    public static final int REQ_BANK_WITHDRAW = 2001;
    
    
    public static final int REP_OK = 10001;
    public static final int REP_KO = 10002;

    public static final int REP_PASS_T = 11001;
    public static final int REP_NEW_PASS = 11002;
    public static final int REP_TRANSACTION = 11003;
    public static final int REP_PASS_K = 11004;
}
