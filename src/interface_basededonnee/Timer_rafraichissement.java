package interface_basededonnee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Timer_rafraichissement {
	static Timer createTimer ()
	{
		// Création d'une instance de listener 
		// associée au timer
		ActionListener action = new ActionListener ()
		{
			// Méthode appelée à chaque tic du timer
			public void actionPerformed (ActionEvent event)
			{
				Gestion_base_de_donnee.rafraichirtableau();
			}
		};

		// Création d'un timer qui génère un tic
		// chaque 500 millième de seconde
		return new Timer (10000, action);
	} 
}
