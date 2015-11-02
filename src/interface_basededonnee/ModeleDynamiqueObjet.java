package interface_basededonnee;




import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;


public class ModeleDynamiqueObjet extends AbstractTableModel {

	static int ligne_cochée;
	static String ID_ligne_cochée;
	private static final long serialVersionUID = 1L;

	final static ArrayList< Portefeuille> Portefeuille = new ArrayList< Portefeuille>();

	private final String[] entetes = {"Entreprises","ID YahooFinance", "Quantité", "valeur d'une action","Total"};



	public ModeleDynamiqueObjet() {
		super();
		Gestion_base_de_donnee.creerbase();
		Gestion_base_de_donnee.recupererBase();
		calculTotalPortefeuille();
	}



	public int getRowCount() {
		return Portefeuille.size();
	}

	public int getColumnCount() {
		return entetes.length;
	}

	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}




	public void addLigne(Portefeuille value) {
		Portefeuille.add(value);
		int rowIndex = Portefeuille.size()-1;
		fireTableRowsInserted(rowIndex, rowIndex);
	}





	public   void removePortefeuille(int rowIndex) {

		Portefeuille.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);

	}

	public String calculTotalPortefeuille(){

		for (int i=0;i<Portefeuille.size();i++){
			System.out.println(getValueAt(i,4));
		}

		return null;

	}





	/* Remplir le tableau */

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return Portefeuille.get(rowIndex).getNom();
		case 1:
			return Portefeuille.get(rowIndex).getID();
		case 2:
			return Portefeuille.get(rowIndex).getnombre_actions();
		case 3:
			return Portefeuille.get(rowIndex).getvaleur_action();
		case 4 : 
			return Portefeuille.get(rowIndex).getTotal();

		default:
			return null; //Ne devrait jamais arriver
		}
	}





	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			Portefeuille.get(rowIndex).setNom((String) value);
			break;
		case 1:
			Portefeuille.get(rowIndex).setID((String) value);
			break;
		case 2:
			Portefeuille.get(rowIndex).setnombre_actions((int) value);
			break;
		case 3:
			Portefeuille.get(rowIndex).setvaleur_action((String) value);
			break;
		case 4 :
			Portefeuille.get(rowIndex).setTotal((String) value);



		}

	}


}