package fr.ensisa.hassenforder.transportation.kiosk.network;

public class Protocol {

    public static final int KIOSK_PORT = 7777;

    public static final int REQ_NEW_PASS = 1001;
    public static final int REQ_GET_PASS_BY_ID = 1002;
    public static final int REQ_BUY_ROUTE = 1003;
    public static final int REQ_BUY_URBAN = 1004;
    public static final int REQ_BUY_SUBSCRIPTION = 1005;
    public static final int REQ_PAY_TRANSACTION = 1006;
    public static final int REQ_CANCEL_TRANSACTION = 1007;

    public static final int REP_OK = 10001;
    public static final int REP_KO = 10002;

    public static final int REP_NEW_PASS = 11001;
    public static final int REP_PASS = 11002;
    public static final int REP_TRANSACTION = 11003;
    public static final int REP_PASS_ID = 11004;
}
