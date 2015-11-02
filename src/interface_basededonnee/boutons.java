package interface_basededonnee;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;


/*--------------------------------------------------------------------------------------------------------------
 * 													Bouton Rafraichir
 */
class bouton_rafraichir extends AbstractAction {
	private static ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
		private static final long serialVersionUID = 1L;

		
		@Override
		public void actionPerformed(ActionEvent e) {
					//Gestion_base_de_donnee.recupererBase();
				Gestion_base_de_donnee.rafraichirtableau();
			
		}
   }



/*--------------------------------------------------------------------------------------------------------------
 * 													Bouton supprimer
 */

class bouton_supprimer extends AbstractAction {
	
	private static ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
		private static final long serialVersionUID = 1L;

	
		@Override
		public void actionPerformed(ActionEvent e) {
			  new JOptionPane(); 
	 			int option = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir supprimer l'enreprise du tableau ?(Selectionnez une ligne pour la supprimer)", "Suppression de l'entreprise", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	 			if(option == JOptionPane.OK_OPTION){
	 				int[] selection = Panel_interface.tableau.getSelectedRows();	 
		            for(int i = selection.length - 1; i >= 0; i--){
		            	String IDselection = Panel_interface.tableau.getValueAt(selection[i],2).toString();
		                modele.removePortefeuille(selection[i]);
		              Gestion_base_de_donnee.supprimerlignedelabase(IDselection);
					}     
	 			}
		}
   }



/*--------------------------------------------------------------------------------------------------------------
 * 													Bouton Ajouter
 */



/*--------------------------------------------------------------------------------------------------------------
 * 													Bouton acheter
 */

class bouton_acheter extends AbstractAction {
	private static ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
		private static final long serialVersionUID = 1L;
static  String nombre_actions_a_acheter;
static  int nombre_actions_a_acheter_int;
	
		@Override
		public void actionPerformed(ActionEvent e) {
			nombre_actions_a_acheter = Panel_interface.Champ_achat_vente.getText();
			nombre_actions_a_acheter_int=Integer.parseInt(nombre_actions_a_acheter) ;
	        
	      String ID_entreprise_a_acheter = null;
	      String valeur_une_action =null;
	       int position_liste_entreprise = Panel_interface.Liste_choix_entreprises.getSelectedIndex();

   }
}
/*--------------------------------------------------------------------------------------------------------------
 * 													Bouton vendre
 */

class bouton_vendre extends AbstractAction {
	//private static final ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
		private static final long serialVersionUID = 1L;
		static  String nombre_actions_a_vendre;
		static  int nombre_actions_a_vendre_int;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			   nombre_actions_a_vendre= Panel_interface.Champ_achat_vente.getText();
			  nombre_actions_a_vendre_int=Integer.parseInt(nombre_actions_a_vendre) ;
			  
		}
		}

   

