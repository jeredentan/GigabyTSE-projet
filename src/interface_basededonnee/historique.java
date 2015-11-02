package interface_basededonnee;

import javax.swing.AbstractListModel;


public class historique extends AbstractListModel{

	String[] strings = { "Vous avez acheté x actions de x" };


	public int getSize() { 
		return strings.length; 
	}


	public Object getElementAt(int i) { 
		return strings[i];
	}




}
