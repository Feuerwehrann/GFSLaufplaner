package Laufplaner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Klasse um die Datenbank zu verbinden
 * 
 * @author Ann Manegold
 *
 */

public class DatenbankVerbindung implements Variablen {
	public static String vorschlaegeArray[] = new String[30];

	/**
	 * Methode um Verbindung zur Datenbank herzustellen
	 */
	public static void verbinden() {
		Connection connection = null;
		String letzteLaeufe = "";

		try {
			/**
			 * Verbindung zur Datenbakn herstellen
			 */
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			/**
			 * Verbindungs URL, je nach dem wo die Datenbank gespeichert wird
			 */

			connection = DriverManager.getConnection(URL);

			/**
			 * Auslesen der Daten aus der Tabelle "Einträge" und ausgeben aller Daten
			 */
			String sql = "SELECT * FROM Eintraege";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				int id = result.getInt("IdLauf");
				double km = result.getDouble("Kilometer");
				double zeit = result.getDouble("Zeit");
				Date datum = result.getDate("Datum");
				double pace = zeit / km;
				MIGLayout.ausgabeLetzteLaufe(id, km, zeit, datum, pace);
				System.out.println(id + ", " + km + ", " + zeit + ", " + datum + ", " + pace);

			}
			String sqlVorschlag = "SELECT * FROM Vorschlaege";
			Statement statementVorschlag = connection.createStatement();
			ResultSet resultVorschlag = statementVorschlag.executeQuery(sqlVorschlag);

			while (resultVorschlag.next()) {
				int idVorschlaege = resultVorschlag.getInt("ID");
				String beschreibungVorschlag = resultVorschlag.getString("Beschreibung");
				vorschlaegeArray[idVorschlaege] = beschreibungVorschlag;

			}
			for (int i = 1; i < 22; i++) {
				System.out.println(vorschlaegeArray[i]);
			}

			System.out.println("--------------------");

			/**
			 * Auslesen der letzten 10 Daten aus der Tabelle "Einträge"
			 */
			String sqlLetzte = "SELECT *  FROM Eintraege ORDER BY IdLauf DESC ;";
			Statement statementLetzte = connection.createStatement();
			ResultSet resultLetzte = statementLetzte.executeQuery(sqlLetzte);
			

			while (resultLetzte.next()) {

				int idLetzte = resultLetzte.getInt("IdLauf");
				double kmLetzte = resultLetzte.getDouble("Kilometer");
				double zeitLetzte = resultLetzte.getDouble("Zeit");
				Date datumLetzte = resultLetzte.getDate("Datum");
				double paceLetzte = zeitLetzte / kmLetzte;
				letzteLaeufe = String.format("%d\t| %.2f\t| %.2f\t| %s\t| %.2f", idLetzte, kmLetzte, zeitLetzte,
						datumLetzte, paceLetzte);
				MIGLayout.LetzteLaufe(letzteLaeufe);
				System.out.println(
						idLetzte + ", " + kmLetzte + ", " + zeitLetzte + ", " + datumLetzte + ", " + paceLetzte);
			}

			/**
			 * Auslesen der letzten Daten aus der Tabelle "Ziel"
			 */
			String sqlZiel = "SELECT * FROM Ziel";
			Statement statementZiel = connection.createStatement();
			ResultSet resultZiel = statementZiel.executeQuery(sqlZiel);

			while (resultZiel.next()) {
				int idZiel = resultZiel.getInt("IdZiel");
				double ziel = resultZiel.getDouble("Zielpace");
				System.out.println(idZiel + ", " + ziel);
				MIGLayout.ausgabeZiel(ziel);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			/**
			 * Verbindung zur Datenbank schließen
			 */
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Methode zum Einfügen eines neuen Laufs in die Datenbank
	 * 
	 * @param km   gelaufene kilometer
	 * @param zeit die benötigte Zeit
	 */

	public static void insertLetzterLaufInDatabase(double km, double zeit) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(URL);

			LocalDate currentDate = LocalDate.now();
			java.sql.Date date = java.sql.Date.valueOf(currentDate);

			pstmt = connection.prepareStatement("INSERT INTO Eintraege (Kilometer, Zeit, Datum) VALUES (?, ?, ?)");
			pstmt.setDouble(1, km);
			pstmt.setDouble(2, zeit);
			pstmt.setDate(3, date);

			int numRowsAffected = pstmt.executeUpdate();
			System.out.println(numRowsAffected + " rows affected.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String laufVorschlagen(double energieWert) {
		String Vorschlag = null;
		Vorschlag = vorschlaegeArray[(int) energieWert];
		return Vorschlag;

	}

	/**
	 * Methode zum einfügen eines Ziels in die Datenbank
	 * 
	 * @param ziel das eingestellte Ziel
	 */
	public static void insertZielInDatabase(double ziel) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(URL);

			pstmt = connection.prepareStatement("INSERT INTO Ziel (Zielpace) VALUES (?)");
			pstmt.setDouble(1, ziel);

			int numRowsAffected = pstmt.executeUpdate();
			System.out.println(numRowsAffected + " rows affected.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
