package fr.ensisa.hassenforder.transportation.terminal.model;

/**
 *
 * @author Hassenforder
 */
public interface ModelListener {
   
    public void notifyStatusChanged (String status);
    public void notifyPassChanged (Pass pass);
}
