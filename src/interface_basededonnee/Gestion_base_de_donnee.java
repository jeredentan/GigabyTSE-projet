package interface_basededonnee;



import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Gestion_base_de_donnee {

	//Infos sur la base de donnée( à changer si ce n'est pas bon).
	static double achat;
	/*static String url="jdbc:mysql://localhost:3306/base_projet_info_gigabytse";
	 static String login="root";
	 static String passwd="root";*/
	static String url="jdbc:mysql://localhost:3306/base_projet_info_gigabytse";
	static String login="root";
	static String passwd="root";
	static Connection cn=null;
	static
	Statement st=null;
	static ResultSet rs=null;

	public static void connexionbase(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn=DriverManager.getConnection(url,login,passwd);
			st=cn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void creerbase(){


		connexionbase();
		try{

			//String creationbase="CREATE DATABASE IF NOT EXISTS `base_projet_info`";
			//String creation_listeentrepriseCAC40="CREATE TABLE IF NOT EXISTS`Liste_Entreprises_Yahoo_CAC40(`Entreprises` varchar(255) DEFAULT NULL,`ID` varchar(255) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1";
			String sq10="CREATE TABLE IF NOT EXISTS `Tableau_Alice`(`Entreprises` VARCHAR(255) NULL DEFAULT NULL , `ID` VARCHAR(255) NULL DEFAULT NULL , `nombre_actions` VARCHAR(255) NULL DEFAULT NULL , `valeur_action` VARCHAR(255) NULL DEFAULT NULL , `total` VARCHAR(255) NULL DEFAULT NULL )";
			String sql0="DELETE FROM `Tableau_Alice`";
			st.executeUpdate(sq10);
			st.executeUpdate(sql0);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{

				cn.close();
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}


	public static void sauverlignedansBase(String Entreprise, String ID, int nb_actions, Double valeur_action,Double total){

		connexionbase();
		try{

			String sql = "INSERT INTO `Tableau_Alice`(`Entreprises`,`ID`,`nombre_actions`,`valeur_action`,`total`) VALUES ('"+ Entreprise +"','" + ID +"','" + nb_actions + "','"+ valeur_action + "','"+total+"')";

			st.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{

				cn.close();
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}

	public static double calcultotalportefeuille(){


		connexionbase();
		Double totalportefeuille=null;
		ArrayList<Double> listetotaux = new ArrayList<Double>(); 

		double total = 0;
		try{

			String sql ="SELECT * from `Tableau_Alice`";
			rs=st.executeQuery(sql);


			while(rs.next()){

				String total_ligne =rs.getString("total");
				double total_ligne_converti= Double.parseDouble(total_ligne);
				listetotaux.add(total_ligne_converti);
			}

			for (Double note : listetotaux) {
				total += note;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{	
				cn.close();
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}


		return total;	
	}
	public static void supprimerlignedelabase(String ID){

		connexionbase();
		try{

			String sql = "DELETE FROM `Tableau_Alice` WHERE `Tableau_Alice`.`ID` = '"+ ID +"' ";
			st.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				cn.close();
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}

		}

	}

	public static void recupererBase(){


		connexionbase();
		try{
			String sql ="SELECT * from `Tableau_Alice`";
			rs=st.executeQuery(sql);
			// On nettoie la table avant de la remplir avec le contenu de la BDD

			ModeleDynamiqueObjet.Portefeuille.clear();

			while(rs.next()){
				int nb_actions =rs.getInt("nombre_actions");
				Double val_actions = Portefeuille.floor(API_YahooFinance.valeuraction(rs.getString("ID")),3);
				Double total = Portefeuille.floor((nb_actions*val_actions),3)- achat;
				String Total1= total.toString();
				ModeleDynamiqueObjet.Portefeuille.add(new  Portefeuille(rs.getString("Entreprises"),rs.getString("ID"),nb_actions,val_actions.toString()+"$",Total1+"$")); 
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try{
				cn.close();
				st.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}


	}
	public static ArrayList<String> recupererlisteID(){

		connexionbase();
		ArrayList<String> listefinale = null;
		int i=1;
		ArrayList<String> liste = new ArrayList<String>();
		try{

			String sql ="SELECT * from `Liste_Entreprises_Yahoo_CAC40`";
			rs=st.executeQuery(sql);
			// On nettoie la table avant de la remplir avec le contenu de la BDD		
			while(rs.next()){
				String ligne = rs.getString("ID");
				liste.add(ligne);
				i=i+1;	
			}
			listefinale = liste;


		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				cn.close();
				st.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return listefinale;
	}

	public static ArrayList<String> recupererlisteentrepriseajouter(){
		connexionbase();

		ArrayList<String> listefinale = null;
		int i=1;
		ArrayList<String> liste = new ArrayList<String>();
		try{

			String sql ="SELECT * from `Liste_Entreprises_Yahoo_CAC40`";
			rs=st.executeQuery(sql);
			// On nettoie la table avant de la remplir avec le contenu de la BDD		
			while(rs.next()){
				String ligne = rs.getString("Entreprises");
				liste.add(ligne);
				i=i+1;	
			}
			listefinale = liste;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				cn.close();
				st.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listefinale;
	}

	@SuppressWarnings("deprecation")
	public static void rafraichirtableau(){

		ModeleDynamiqueObjet.Portefeuille.clear();

		Panel_interface.tableau.setVisible(false);
		Panel_interface.tableau.removeAll();
		//Mettre fonction rafraichir tableau

		connexionbase();
		try{
			String sql ="SELECT * from `Tableau_Alice`";
			rs=st.executeQuery(sql);
			// On nettoie la table avant de la remplir avec le contenu de la BDD

			ModeleDynamiqueObjet.Portefeuille.clear();

			while(rs.next()){
				int nb_actions =rs.getInt("nombre_actions") + bouton_acheter.nombre_actions_a_acheter_int - bouton_vendre.nombre_actions_a_vendre_int;
				Double val_actions = Portefeuille.floor(API_YahooFinance.valeuraction(rs.getString("ID")),3);
				Double total = Portefeuille.floor((nb_actions*val_actions),3);
				String Total1= total.toString();
				ModeleDynamiqueObjet.Portefeuille.add(new  Portefeuille(rs.getString("Entreprises"),rs.getString("ID"),nb_actions,val_actions.toString()+"euros",Total1+"euros")); 
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try{
				cn.close();
				st.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
		bouton_ajouter.totalportefeuille = Gestion_base_de_donnee.calcultotalportefeuille();

		Panel_interface.tableau.setVisible(true);
		Panel_interface.label_total_portefeuille.setText("Total du portefeuille : "+ bouton_ajouter.totalportefeuilleaffiche  + " euros");

	}

	public static void remplir_Jcombobox() {
		connexionbase();
		try {

			String sql ="SELECT * from `Tableau_Alice`";
			st = cn.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next()){
				Panel_interface.Liste_choix_entreprises.addItem(rs.getString("Entreprises"));
				Panel_interface.Liste_choix_ID.addItem(rs.getString("ID"));

			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
