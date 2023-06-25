package Laufplaner;

/**
 * Importieren von allen benötigten Bibliotheken
 */

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

/**
 * Klasse MIGLayout
 * 
 * @author Ann Manegold
 *
 */

public class MIGLayout {
	/**
	 * Initialisierung aller benötigten Variablen
	 */
	private static JLabel laeufe1 = new JLabel(" ");
	public static String letzteEintraege;
	double ziel;
	int clickZiel = 0;
	static int laufID;
	static double laufKilometer;
	static double laufZeit;
	static double laufpace;
	static Date laufdatum;
	static double zielpace;

	private JFrame frame;
	private JPanel panel;

	/**
	 * Konstruktor des MigLayouts
	 */
	public MIGLayout() {
		initialize();

	}

	/**
	 * Initialisierung der GUI
	 */
	public void initialize() {
		/**
		 * Erstellen des JFrames EInstellungen des Jframes
		 */
		frame = new JFrame();
		frame.setTitle("Laufplaner");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		/**
		 * Erstellung des Haupt-Panels mit dem MigLayout
		 */
		JPanel panel = new JPanel(new MigLayout());
		MigLayout layout = new MigLayout("wrap 3");

		/**
		 * Erstellung der einzelnen Panels mit JLabels
		 */
		JPanel panLetzteEintraege = new JPanel(new MigLayout("center"));
		JPanel panZeit = new JPanel(new MigLayout("center"));
		final JPanel panNeuerEintrag = new JPanel(new MigLayout("center"));
		final JPanel panZiel = new JPanel(new MigLayout("center"));
		JPanel panVorschlaege = new JPanel(new MigLayout("center"));

		/**
		 * Anpassung der Panels Schwarzer Rand
		 */
		panLetzteEintraege.setBorder(BorderFactory.createLineBorder(Color.black));
		panZeit.setBorder(BorderFactory.createLineBorder(Color.black));
		panNeuerEintrag.setBorder(BorderFactory.createLineBorder(Color.black));
		panZiel.setBorder(BorderFactory.createLineBorder(Color.black));
		panVorschlaege.setBorder(BorderFactory.createLineBorder(Color.black));

		panLetzteEintraege.setSize(1500, 200);

		/**
		 * Erstellung der JLabels und JButtons und Beschriftung
		 */
		JLabel labelLetzteEintraege = new JLabel("<html><body><h1>Letzte Einträge</h1></body></html>");
		JLabel labelZeit = new JLabel("Zeit");
		JLabel labelZiel = new JLabel("<html><body><h1>Ziel</h1></body></html>");
		JLabel labelneuerEintrag = new JLabel("<html><body><h1>neuer Eintrag<h1></body></html>");
		JLabel labelVorschlaege = new JLabel("Vorschläge");

		JLabel neuerEintragL1 = new JLabel("Bitte tragen Sie Ihren Lauf von heute ein!");
		JLabel neuerEintragL2 = new JLabel("<html><body><h3>gelaufene Strecke in Kilomter</h3></body></html>");
		final PositiveDecimalField neuerEintragTF1 = new PositiveDecimalField();
		JLabel neuerEintragL3 = new JLabel("<html><body><h3>ben�tigte Zeit in Minuten</h3></body></html>");
		final PositiveDecimalField neuerEintragTF2 = new PositiveDecimalField();
		JButton neuerEintragB1 = new JButton("<html><body><h3>eintragen</h3></body></html>");
		JLabel neuerEintragL4 = new JLabel("Daten wurden hinzugefuegt!");
		final JLabel zielL1 = new JLabel("Dein aktuelles Ziel lautet " + zielpace + " min/km");
		final JButton neuesZielB1 = new JButton("Neues Ziel");
		final PositiveDecimalField neuesZielTF1 = new PositiveDecimalField();
		final JLabel zielL2 = new JLabel("Trage dein neues Ziel in min/km ein!");

		JButton letzteEintraege = new JButton("letzten Lauf laden");
		
		final JLabel zielErreicht = new JLabel ("Das Ziel wurde erreciht! Herzlichen Glueckwunsch!");
		zielErreicht.setForeground(Color.red);

		/**
		 * Hinzufügen der Label zu den einzelnen Panels
		 */
		panLetzteEintraege.add(labelLetzteEintraege, "center, wrap");
		panZeit.add(labelZeit);
		panNeuerEintrag.add(labelneuerEintrag, "center, wrap");
		panNeuerEintrag.add(neuerEintragL1, "center, wrap");
		panNeuerEintrag.add(neuerEintragL2, "center, wrap");
		panNeuerEintrag.add(neuerEintragTF1, "center, wrap");
		panNeuerEintrag.add(neuerEintragL3, "center, wrap");
		panNeuerEintrag.add(neuerEintragTF2, "center, wrap");
		panNeuerEintrag.add(neuerEintragB1, "center, wrap");
		panZiel.add(labelZiel, "center, wrap");
		panZiel.add(zielL1, "center, wrap");
		panZiel.add(neuesZielB1, "center, wrap");
		panVorschlaege.add(labelVorschlaege);
		panLetzteEintraege.add(laeufe1, "center, wrap");


		/**
		 * Hinzufügen der Panel zum Hauptpanel
		 */
		panel.add(panLetzteEintraege, "w 25sp, h 100sp, dock west");
		panel.add(panVorschlaege, "w 25sp, h 60sp, dock east, dock south");
		panel.add(panNeuerEintrag, "w 25sp, h 20sp, dock east");
		panel.add(panZeit, "w 50sp, h 60sp, dock north");
		panel.add(panZiel, "w 50sp, h 30sp, dock north, dock south");

		/**
		 * Hintergrundfarbe der Panel anpassen
		 */
		panLetzteEintraege.setBackground(Color.white);
		panZeit.setBackground(Color.white);
		panNeuerEintrag.setBackground(Color.white);
		panZiel.setBackground(Color.white);
		panVorschlaege.setBackground(Color.white);

		/**
		 * Hinzufügen des Hauptpanels zum frame
		 */
		frame.add(panel, BorderLayout.CENTER);

		/**
		 * Jframe konfigurierren und anzeigen
		 */
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		/**
		 * Action listener zum eintargen eines neuen Laufes
		 */
		neuerEintragB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						double kilometer = (Double.parseDouble(neuerEintragTF1.getText()));
						double zeit = (Double.parseDouble(neuerEintragTF2.getText()));
						DatenbankVerbindung.insertLetzterLaufInDatabase(kilometer, zeit);
						laeufe1.getParent().removeAll();
						/**
						 * hinzufügen der Daten zur Datenbank
						 */
						JLabel neuerEintragPace = new JLabel("Deine pace lautet: " + zeit / kilometer + " min/km");
						panNeuerEintrag.add(neuerEintragPace, "center, wrap");
						neuerEintragTF1.setText(null);
						neuerEintragTF2.setText(null);
						DatenbankVerbindung.verbinden();
						panNeuerEintrag.revalidate();
						if ((zeit / kilometer)<=zielpace) {
							panZiel.add(zielErreicht);
						}

					}
				});
			}
		});

		/**
		 * Action listener zum anzeigen der letzten Läufe
		 */
		letzteEintraege.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		/**
		 * Action Listener zum eintragen eines neuen Ziels
		 */

		neuesZielB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickZiel++;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						neuesZielB1.setText("Neues Ziel speichern");

						zielL1.setText(" ");
						panZiel.add(zielL2, "center, wrap");
						panZiel.add(neuesZielTF1, "center, wrap");
						panNeuerEintrag.revalidate();
						if (clickZiel == 2) {
							ziel = (Double.parseDouble(neuesZielTF1.getText()));
							DatenbankVerbindung.insertZielInDatabase(ziel);
							DatenbankVerbindung.verbinden();

							zielL1.setText("Dein aktuelles Ziel lautet " + zielpace + " min/km");

							panZiel.remove(neuesZielTF1);
							neuesZielB1.setText("neues Ziel");
							panZiel.remove(neuesZielTF1);
							panZiel.remove(zielL2);
							panNeuerEintrag.revalidate();
							clickZiel = 0;

						}
					}
				});

			}

		});

	}

	public static void LetzteLaufe(String eintrag) {
	    if (letzteEintraege == null) {
	        letzteEintraege = "";
	    } else {
	        letzteEintraege += "<br>";
	    }
	    letzteEintraege += eintrag;

	    laeufe1.setText("<html><body>" + letzteEintraege + "</body></html>");
	}

	/**
	 * Methode zum uebergeben der Daten der letzten Läufe
	 * 
	 * @param id    id des Laufes
	 * @param km    kilometer des Laufes
	 * @param zeit  Zeit des Laufes
	 * @param datum Datum des Laufes
	 * @param pace  Pace des Laufes
	 */

	public static void ausgabeLetzteLaufe(int id, double km, double zeit, Date datum, double pace) {
		laufID = id;
		laufKilometer = km;
		laufZeit = zeit;
		laufpace = pace;
		laufdatum = datum;

	}

	/**
	 * Methode zum uebergeben des Ziel
	 * 
	 * @param ziel aktuelles ziel
	 */

	public static void ausgabeZiel(double ziel) {
		zielpace = ziel;

	}
}
