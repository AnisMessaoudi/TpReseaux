package fr.ensisa.hassenforder.transportation.kiosk.model;

/**
 *
 * @author Hassenforder
 */
public interface ModelListener {
   
	public void notifyStatusChanged(String text);
	public void notifyClientChanged(long passId);
    public void notifyPassChanged (Pass pass);

}
