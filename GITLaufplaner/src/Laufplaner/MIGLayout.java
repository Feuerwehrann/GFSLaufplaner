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
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.*;
import javax.swing.*;
import java.awt.*;

/**
 * Klasse MIGLayout
 * 
 * @author Ann Manegold
 *
 */

public class MIGLayout implements Variablen {
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


		/**
		 * Erstellung der einzelnen Panels mit JLabels
		 */
		JPanel panLetzteEintraege = new JPanel(new MigLayout("center"));
		final JPanel panZeit = new JPanel(new MigLayout("center"));
		final JPanel panNeuerEintrag = new JPanel(new MigLayout("center"));
		final JPanel panZiel = new JPanel(new MigLayout("center"));
		JPanel panVorschlaege = new JPanel(new MigLayout("center"));
		JPanel panErklaerungen = new JPanel(new MigLayout("Center"));
		JPanel panVorschlaegeFragen = new JPanel(new MigLayout("center"));

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
		JLabel labelZiel = new JLabel("<html><body><h1>Ziel</h1></body></html>");
		JLabel labelneuerEintrag = new JLabel("<html><body><h1>neuer Eintrag<h1></body></html>");
		JLabel labelVorschlaege = new JLabel("<html><body><h1>Vorschlaege</h1></body></html>");
		JLabel labelErklaerungen = new JLabel("<html><body><h1>Erklaerungen</h1></body></html>");

		JLabel neuerEintragL1 = new JLabel("Bitte tragen Sie Ihren Lauf von heute ein!");
		JLabel neuerEintragL2 = new JLabel("<html><body><h3>gelaufene Strecke in Kilomter</h3></body></html>");
		final PositiveDecimalField neuerEintragTF1 = new PositiveDecimalField();
		JLabel neuerEintragL3 = new JLabel("<html><body><h3>benoetigte Zeit in Minuten</h3></body></html>");
		final PositiveDecimalField neuerEintragTF2 = new PositiveDecimalField();
		JButton neuerEintragB1 = new JButton("<html><body><h3>eintragen</h3></body></html>");
		final JLabel zielL1 = new JLabel("Dein aktuelles Ziel lautet " + zielpace + " min/km");
		final JButton neuesZielB1 = new JButton("Neues Ziel");
		final PositiveDecimalField neuesZielTF1 = new PositiveDecimalField();
		final JLabel zielL2 = new JLabel("Trage dein neues Ziel in min/km ein!");
		JLabel erklaerungen1 = new JLabel(
				"Lockerer Dauerlauf: Entspannter Lauf, man sollte beim Laufen noch ein Gespräch führen können");
		JLabel erklaerungen2 = new JLabel(
				"Intervall: schnell: 90 bis 100 Prozent des möglichen Tempos, langsam: schneller als lockerer Dauerlauf");
		JLabel erklaerungen3 = new JLabel("Fartlek: Pyramidenläufe");
		JLabel erklaerungen4 = new JLabel(
				"In and Outs: Läufe auf der 400 Meter Bahn, 50 Meter langsam, 50 Meter schnell");
		JLabel erklaerungen5 = new JLabel("Dehnen: Nach dem Einlaufen auf jeden Fall dehnen");
		JLabel erklaerungen6 = new JLabel("Stabis: Regelmäßig Stabilisationsübungen durchführen");
		JLabel erklaerungen7 = new JLabel("Kraft: Regelmäßig Kraftübungen durvhführen");

		final JLabel zielErreicht = new JLabel("Das Ziel wurde erreicht! Herzlichen Glueckwunsch!");
		zielErreicht.setForeground(Color.red);
		JLabel vorschlaege0 = new JLabel(
				"Bitte tragen Sie die Werte ein, um einen Lauf Vorschlag für Ihren heutigen Lauf zu erhalten!");
		JLabel vorschlaege1 = new JLabel("Wie viele Stunden haben Sie geschlafen?");
		JLabel vorschlaege2 = new JLabel("Wie fühlen Sie sich auf einer Skala von 1 - 10?");
		final PositiveDecimalField tfVorschlaege1 = new PositiveDecimalField();
		final PositiveDecimalField tfVorschlaege2 = new PositiveDecimalField();
		JButton btVorschlaege1 = new JButton("Vorschlag anzeigen");
		final JLabel vorschlaege3 = new JLabel("");

		JLabel letzteTitel = new JLabel ("ID|Kilometer|Zeit|Strecke|Datum|pace");
		/**
		 * Hinzufügen der Label zu den einzelnen Panels
		 */
		
		panLetzteEintraege.add(labelLetzteEintraege, "center, wrap");
		panLetzteEintraege.add(letzteTitel, "center, wrap");
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
		panVorschlaegeFragen.add(labelVorschlaege, "center, wrap");
		panLetzteEintraege.add(laeufe1, "center, wrap");
		panZeit.add(DiagramErstellen(), "w 100%, h 60sp, span 3");
		panErklaerungen.add(labelErklaerungen, "center, wrap");
		panErklaerungen.add(erklaerungen1, "center, wrap");
		panErklaerungen.add(erklaerungen2, "center, wrap");
		panErklaerungen.add(erklaerungen3, "center, wrap");
		panErklaerungen.add(erklaerungen4, "center, wrap");
		panErklaerungen.add(erklaerungen5, "center, wrap");
		panErklaerungen.add(erklaerungen6, "center, wrap");
		panErklaerungen.add(erklaerungen7, "center, wrap");
		panVorschlaegeFragen.add(vorschlaege0, "center, wrap");
		panVorschlaegeFragen.add(vorschlaege1, "center, wrap");
		panVorschlaegeFragen.add(tfVorschlaege1, "center, wrap");
		panVorschlaegeFragen.add(vorschlaege2, "center, wrap");
		panVorschlaegeFragen.add(tfVorschlaege2, "center, wrap");
		panVorschlaegeFragen.add(btVorschlaege1, "center, wrap");
		panVorschlaegeFragen.add(vorschlaege3, "center, wrap");

		/**
		 * Hinzufügen der Panel zum Hauptpanel
		 */
		panel.add(panLetzteEintraege, "w 25sp, h 100sp, dock west");
		panel.add(panVorschlaege, "w 25sp, h 60sp, dock east, dock south");
		panel.add(panNeuerEintrag, "w 25sp, h 20sp, dock east");
		panel.add(panZeit, "w 50sp, h 60sp, dock north");
		panel.add(panZiel, "w 50sp, h 30sp, dock north, dock south");
		panVorschlaege.add(panErklaerungen, "w 50sp, h 100sp, dock east");
		panVorschlaege.add(panVorschlaegeFragen, "w 50sp, h 100sp, dock west");

		/**
		 * Hintergrundfarbe der Panel anpassen
		 */
		panLetzteEintraege.setBackground(Color.white);
		panZeit.setBackground(Color.white);
		panNeuerEintrag.setBackground(Color.white);
		panZiel.setBackground(Color.white);
		panVorschlaege.setBackground(Color.white);
		panErklaerungen.setBackground(Color.white);
		panVorschlaegeFragen.setBackground(Color.white);

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

		btVorschlaege1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						double schlaf = (Double.parseDouble(tfVorschlaege1.getText()));
						double energie = (Double.parseDouble(tfVorschlaege2.getText()));
						double energieWert = ((int) ((schlaf + energie) + 0.5));
				
						String vorschlag = DatenbankVerbindung.laufVorschlagen(energieWert);
						vorschlaege3.setText (vorschlag);
						

					}
				});
			}
		});

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
						DatenbankVerbindung.verbinden();
						laeufe1.getParent().removeAll();
						/**
						 * hinzufügen der Daten zur Datenbank
						 */
						JLabel neuerEintragPace = new JLabel("Deine pace lautet: " + zeit / kilometer + " min/km");
						panNeuerEintrag.add(neuerEintragPace, "center, wrap");
						DiagramErstellen();
						neuerEintragTF1.setText(null);
						neuerEintragTF2.setText(" ");
						neuerEintragTF1.revalidate();
						neuerEintragTF1.repaint();
						

						panNeuerEintrag.revalidate();
						if ((zeit / kilometer) <= zielpace) {
							panZiel.add(zielErreicht);
						}

					}
				});
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

	public JComponent DiagramErstellen() {
	    // Erstellung der Zeitreihen-Dataset
	    TimeSeriesCollection dataset = new TimeSeriesCollection();

	    try {
	        // Verbindung zur Access-Datenbank herstellen
	       
	        Connection conn = DriverManager.getConnection(URL);

	        // SQL-Abfrage ausführen, um die Einträge abzurufen
	        String query = "SELECT * FROM Eintraege";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        // Zeitformat für die Achse
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        // Erstellung der Zeitreihe für den Pace
	        TimeSeries paceSeries = new TimeSeries("Pace");

	        // Erstellung der Zeitreihe für die Strecke
	        TimeSeries distanceSeries = new TimeSeries("Strecke");

	        // Daten aus der Abfrage dem Dataset hinzufügen
	        while (rs.next()) {
	            double km = rs.getDouble("Kilometer");
	            double zeit = rs.getDouble("Zeit");
	            String dateString = rs.getString("Datum");
	            java.util.Date date = dateFormat.parse(dateString);
	            double pace = zeit / km;
	            Day day = new Day(date);
	            
	            paceSeries.addOrUpdate(day, pace);
	            distanceSeries.addOrUpdate(day, km);
	        }

	        // Hinzufügen der Zeitreihen zum Dataset
	        dataset.addSeries(paceSeries);
	        dataset.addSeries(distanceSeries);

	        // Verbindung schließen
	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Erstellung des Diagramms
	    JFreeChart chart = ChartFactory.createTimeSeriesChart(
	            "Pace- und Streckenverlauf",
	            "Datum",
	            "Pace (Min/km) / Strecke (km)",
	            dataset,
	            true,
	            true,
	            false
	    );

	    // Anpassung der Achsen
	    XYPlot plot = (XYPlot) chart.getPlot();
	    DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
	    dateAxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
	    NumberAxis paceAxis = (NumberAxis) plot.getRangeAxis();
	    paceAxis.setAutoRangeIncludesZero(false);

	    // Erstellung des ChartPanels zur Anzeige des Diagramms
	    ChartPanel chartPanel = new ChartPanel(chart);

	    return chartPanel;
	}


}
