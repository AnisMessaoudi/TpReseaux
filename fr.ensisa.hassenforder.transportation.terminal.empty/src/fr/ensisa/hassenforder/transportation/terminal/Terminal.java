package fr.ensisa.hassenforder.transportation.terminal;

import javax.swing.SwingUtilities;

import fr.ensisa.hassenforder.transportation.terminal.model.Model;
import fr.ensisa.hassenforder.transportation.terminal.model.ModelListener;
import fr.ensisa.hassenforder.transportation.terminal.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.model.Ticket;
import fr.ensisa.hassenforder.transportation.terminal.network.CommandSession;
import fr.ensisa.hassenforder.transportation.terminal.network.ISession;
import fr.ensisa.hassenforder.transportation.terminal.ui.GUIListener;
import fr.ensisa.hassenforder.transportation.terminal.ui.Terminal_UI;

/**
 *
 * @author hassenforder
 */
public class Terminal implements GUIListener {

    private Model model;
    private Terminal_UI gui;
    private ModelListener listener;
    private ISession session;

    public Terminal() {
        model = new Model ();
        gui = new Terminal_UI();
        gui.setListener(this);
        listener = gui;
        session = new CommandSession();
        session.open();
    }

    public Model getModel() {
        return model;
    }

    public Terminal_UI getGui() {
        return gui;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Terminal terminal = new Terminal ();
                terminal.getGui().setVisible(true);
            }
        });
    }

    @Override
    public void onClickFetch (int passId) {
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
                            listener.notifyStatusChanged ("onClickFetch : KO");
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onClickTicketUse (Ticket ticket, int count) {
        new Thread () {
            @Override
            public void run () {
                boolean result = session.useTicket(ticket.getId(), count);
                if (result) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onClickTicketUse : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("onClickTicketUse : KO");
                        }
                    });
                }
            }
        }.start();
    }

}
