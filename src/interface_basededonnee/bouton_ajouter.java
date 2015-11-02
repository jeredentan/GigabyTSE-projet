package interface_basededonnee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class bouton_ajouter extends AbstractAction {
	static  String nom_entreprise_ajouter;
	static  String nombre_actions_entreprise_a_ajouter;
	static  Double total;
	static double totalportefeuille;
	static  double totalportefeuilleaffiche;
	static  ArrayList<String> listeentreprise = Gestion_base_de_donnee.recupererlisteentrepriseajouter();
	static  ArrayList<String> listeentreprises1=listeentreprise;
	static  ArrayList<String> listeentreprises2;
	static  ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
	static  historique historique = new historique();
	static  int nb_actions;
	static Timer timer_refresh; 
	static JLabel label_total_portefeuille = new JLabel();
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> listeIDcorrespondant = Gestion_base_de_donnee.recupererlisteID();
		String IDcorrespondant = null;
		JComboBox jcb = new JComboBox(listeentreprises1.toArray());
		//jcb.setEditable(true);
		JOptionPane.showMessageDialog( null, jcb, "select or type a value", JOptionPane.QUESTION_MESSAGE);
		Object entreprise_a_ajouter = jcb.getSelectedItem();
		String entreprise_a_ajouter_string = entreprise_a_ajouter.toString();  
		// recuperer l'ID correspondant

		for( int i=0;i<listeIDcorrespondant.size();i++){
			if(i==listeentreprises1.indexOf(entreprise_a_ajouter)){
				IDcorrespondant = listeIDcorrespondant.get(i);
				//listeIDcorrespondant.remove(i);
			}
		}



		nombre_actions_entreprise_a_ajouter = (String)JOptionPane.showInputDialog(Panel_interface.bouton_ajouter, "Combien d'actions possedez vous dans cette entreprise ?",  "Nombre d'actions", JOptionPane.QUESTION_MESSAGE);
		nb_actions= Integer.parseInt(nombre_actions_entreprise_a_ajouter);
		Double total1= null;

		try {
			total1 =Portefeuille.floor( nb_actions*(API_YahooFinance.valeuraction(IDcorrespondant)),3);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {  			
			modele.addLigne(new Portefeuille(entreprise_a_ajouter_string, IDcorrespondant ,nb_actions  ,API_YahooFinance.valeuraction(IDcorrespondant)+" "+ "euros".toString(),total1.toString()+" "+"euros"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		try {

			Gestion_base_de_donnee.sauverlignedansBase(entreprise_a_ajouter_string,IDcorrespondant,nb_actions,API_YahooFinance.valeuraction(IDcorrespondant),total1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	

		Gestion_base_de_donnee.remplir_Jcombobox();
		for( int i=0;i<listeIDcorrespondant.size();i++){
			if(entreprise_a_ajouter==listeIDcorrespondant.get(i)){
				listeIDcorrespondant.remove(i);
			}
		}
		totalportefeuille = Gestion_base_de_donnee.calcultotalportefeuille();
		totalportefeuilleaffiche=Portefeuille.floor(totalportefeuille,3);
		// On retire l'ID et le nom de la liste pour garder la correspondance et ne plus les avoir dans laJcomboBox


		for( int i=0;i<listeIDcorrespondant.size();i++){
			if(i==listeentreprises1.indexOf(entreprise_a_ajouter)){
				listeIDcorrespondant.remove(i);
			}
		} for( int i=0;i<listeentreprises1.size();i++){
			if(i==listeentreprises1.indexOf(entreprise_a_ajouter)){
				listeentreprises1.remove(i);

			}
		}


		timer_refresh = Timer_rafraichissement.createTimer();
		timer_refresh.start();

		label_total_portefeuille.setText("Total du portefeuille : "+ totalportefeuilleaffiche  + " euros");
		
	}
}