package interface_basededonnee;


 
 
import static javafx.concurrent.Worker.State.FAILED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
  
public class Interface_JavaFX_Swing extends JFrame{
 
   
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
    private static WebEngine engine;
    Timer t;
    private final JPanel panelswing = new JPanel(new BorderLayout());
    
    static Timer t1 ;
    public Interface_JavaFX_Swing() {
        super();
        initComponents();
    }

    
    private void initComponents() {
        createScene();
        Panel_interface interface_netbeans = new Panel_interface();
        Panel_interface.Panelbas.add(jfxPanel);
        getContentPane().add(interface_netbeans);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    
 
    
    private void createScene() {
 
        	Platform.runLater(new Runnable() {
            @Override 
            public void run() {
 
                WebView view = new WebView();
                engine = view.getEngine();
 
                engine.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override 
                            public void run() {
                                Interface_JavaFX_Swing.this.setTitle("Portefeuille d'actions de Alice");
                            }
                        });
                    }
                });
 
             

                engine.getLoadWorker()
                        .exceptionProperty()
                        .addListener(new ChangeListener<Throwable>() {
 
    public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                                if (engine.getLoadWorker().getState() == FAILED) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override public void run() {
                                            JOptionPane.showMessageDialog(
                                                    panelswing,
                                                    (value != null) ?
                                                    engine.getLocation() + "\n" + value.getMessage() :
                                                    engine.getLocation() + "\nUnexpected error.",
                                                    "Loading error...",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    });
                                }
                            }
                        });

                jfxPanel.setScene(new Scene(view));
            }
        });
    }
 
    public static void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
                String tmp = toURL(url);
 
                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }
 
                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
                return null;
        }
    }

   

    public static void main(String[] args) {
    	
   
   
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
            	Interface_JavaFX_Swing browser = new Interface_JavaFX_Swing();
                browser.setVisible(true);
                
                browser.loadURL("https://www.google.fr");
           }     
       });
    }


	
}