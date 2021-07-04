package fr.unice.polytech.cookieFactory.store.kitchen;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Kitchen{
	private StateKitchen kitchenState;
	private PropertyChangeSupport support;


	public Kitchen(){
		support = new PropertyChangeSupport(this);
		kitchenState = new KitchenOk();
	}

	public void setState(StateKitchen state) {
		support.firePropertyChange("kitchenState", this.kitchenState, state);
		this.kitchenState = state;
	}

	public StateKitchen getState(){
		return kitchenState;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}


	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

}
