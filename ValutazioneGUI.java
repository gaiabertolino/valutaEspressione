package Bertolino.valutaExp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EmptyStackException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
class FinestraGUI extends JFrame implements ActionListener{
	
	private JLabel title = new JLabel("Benvenuto! ", JLabel.CENTER);
	private JLabel track = new JLabel("Inserisci un'espressione da valutare >> ");
	private JTextField text = new JTextField(20);
	private JButton calcola = new JButton("Calcola");
	private String ris ="";

	public FinestraGUI() {
		setTitle("GaiaMath");
		setSize(600,100);
		setLocation(450,300);
		setBackground(Color.CYAN);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { 
			if (uscita()) System.exit(0); } } );
		text.addActionListener(this); calcola.addActionListener(this);
		
		JPanel pannelloInput = new JPanel(); pannelloInput.setBackground(Color.WHITE);
		pannelloInput.add(track, BorderLayout.WEST); pannelloInput.add(text, BorderLayout.CENTER); pannelloInput.add(calcola, BorderLayout.EAST);	
		
		JPanel pannelloInt = new JPanel(); pannelloInt.add(title, BorderLayout.NORTH); 
		
		add(pannelloInt, BorderLayout.NORTH); add(new JSeparator(SwingConstants.HORIZONTAL));  add(pannelloInput,BorderLayout.SOUTH);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calcola) {
			JFrame finestra = new FinestraRis();
			finestra.setVisible(true);
		}
		if (e.getSource() == text)
			checker();
	}
	
	void checker() {
		try {
			if (text.getText() == null)
				calcola.setEnabled(false);
			else if (text.getText().length() > 0 && Regex.corretta(text.getText())) {
				String exp = text.getText();
				StringTokenizer s = new StringTokenizer(exp,"+-/%*^()",true);
				ris = Algoritmo.valutaEspressione(s);
				calcola.setEnabled(true);
			}
			else
				calcola.setEnabled(false);
		}
		catch (IllegalArgumentException e) {
			calcola.setEnabled(false);
		}
		catch (EmptyStackException e) {
			calcola.setEnabled(false);
		}
		catch (NullPointerException e) {
			calcola.setEnabled(false);
		}
	}//checker

	private boolean uscita(){
		   int scelta=JOptionPane.showConfirmDialog( null, "Vuoi uscire?", "Exit", JOptionPane.YES_NO_OPTION);
		   return scelta==JOptionPane.YES_OPTION;
	}//uscita 
	
	private class FinestraRis extends JFrame {
		public FinestraRis() {
			setTitle("Risultato");
			setSize(300,80);
			setLocation(600,310);
			Font f = new Font("Helvetica", Font.PLAIN, 15);
			JLabel messaggio = new JLabel("La soluzione dell'espressione è " + ris); messaggio.setFont(f); messaggio.setForeground(Color.black); messaggio.setHorizontalAlignment(SwingConstants.CENTER);
			add(messaggio, BorderLayout.CENTER);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { setVisible(false);} } );
		}
	}//FinestraRis

}//FinestraGUI
	
public class ValutazioneGUI {
	public static void main(String[] args) {
		FinestraGUI finestra = new FinestraGUI();
		finestra.setVisible(true);
		for (;;)
			finestra.checker();
	}
}//ValutazioneGUI
