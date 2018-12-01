package fr.ensisa.hassenforder.transportation.kiosk;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.ensisa.hassenforder.transportation.kiosk.model.Model;
import fr.ensisa.hassenforder.transportation.kiosk.model.ModelListener;
import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Ticket;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;
import fr.ensisa.hassenforder.transportation.kiosk.network.KioskSession;
import fr.ensisa.hassenforder.transportation.kiosk.network.ISession;
import fr.ensisa.hassenforder.transportation.kiosk.ui.GUIListener;
import fr.ensisa.hassenforder.transportation.kiosk.ui.Kiosk_UI;

/**
 *
 * @author hassenforder
 */
public class Kiosk implements GUIListener {

    private Model model;
    private Kiosk_UI gui;
    private ModelListener listener;
    private ISession session;

    public Kiosk() {
        model = new Model ();
        gui = new Kiosk_UI ();
        gui.setListener(this);
        listener = gui;
        session = new KioskSession();
        session.open();
    }

    public Model getModel() {
        return model;
    }

    public Kiosk_UI getGui() {
        return gui;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Kiosk shopping = new Kiosk();
                shopping.getGui().setVisible(true);
            }
        });
    }
    
	@Override
	public void onClickCreatePass() {
        new Thread () {
            @Override
            public void run () {
            	long passId = session.createPass ();
            	if (passId == -1L) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyClientChanged(-1L);
                            listener.notifyStatusChanged ("PassCreated : OK");
                        }
                    });
            	} else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyClientChanged(passId);
                            listener.notifyStatusChanged ("PassCreated : FAIL");
                        }
                    });
            	}
            }
        }.start();
	}

	@Override
	public void onClickFetch(long passId) {
        new Thread () {
            @Override
            public void run () {
                Pass pass = session.getPassById(passId);
                if (pass != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyPassChanged(pass);
                            listener.notifyStatusChanged ("onClickFetch : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyPassChanged(null);
                            listener.notifyStatusChanged ("onClickFetch : FAIL");
                        }
                    });
                }
            }
        }.start();
	}

	private void displayCardSimulator (Transaction transaction) {
    	String cardId = (String) JOptionPane.showInputDialog(
                gui,
	                "You will be charged "+transaction.getAmount()+" €\n"+
	                "Only give the card Id\n"+
	        		"to simulate recognition and pin code validation",
                "Card Reader Simulator",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "");
    	if (cardId == null || cardId.isEmpty()) {
			this.onCancelTransaction (transaction);
    	} else {
			this.onValidTransaction (transaction, cardId);
		}
	}
	
	@Override
	public void onClickBuyRoute(long passId, String from, String to, int count) {
        new Thread () {
            @Override
            public void run () {
                Transaction transaction = session.buyRoute(passId, from, to, count);
                if (transaction != null) {
                	displayCardSimulator (transaction);
                	SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuyRoute : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuyRoute : FAIL");
                        }
                    });
                }
            }
        }.start();
	}

	@Override
	public void onClickBuyUrban(long passId, int count) {
        new Thread () {
            @Override
            public void run () {
            	Transaction transaction = session.buyUrban(passId, count);
                if (transaction != null) {
                	displayCardSimulator (transaction);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuyUrban : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuyUrban : FAIL");
                        }
                    });
                }
            }
        }.start();
	}

	@Override
	public void onClickBuySubscription(long passId, int month) {
        new Thread () {
            @Override
            public void run () {
            	Transaction transaction = session.buySubscription(passId, month);
                if (transaction != null) {
                	displayCardSimulator (transaction);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuySubscription : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onBuySubscription : FAIL");
                        }
                    });
                }
            }
        }.start();
	}

	@Override
	public void onClickTicketUse(Ticket ticket, int count) {
		//do nothing not relevant in this case
	}

	@Override
	public void onValidTransaction(Transaction transaction, String cardIdString) {
        new Thread () {
            @Override
            public void run () {
            	long cardId = 0;
            	try { cardId = Long.parseLong(cardIdString); } catch (Exception e) {}
            	long result = session.payTransaction(transaction.getId(), cardId);
                if (result == -1) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("Transaction validation : KO");
                        }
                    });
                } else if (result == 0) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("Transaction validation : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("Transaction validation : OK&CREATED");
                            listener.notifyClientChanged(result);
                        }
                    });
                }
            }
        }.start();
	}

	@Override
	public void onCancelTransaction(Transaction transaction) {
        new Thread () {
            @Override
            public void run () {
            	boolean result= session.cancelTransaction(transaction.getId());
                if (result) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("Transaction cancelation : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("Transaction cancelation : FAIL");
                        }
                    });
                }
            }
        }.start();
	}

}
