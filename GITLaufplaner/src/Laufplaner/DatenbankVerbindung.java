package Laufplaner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatenbankVerbindung {
	
	public static void verbinden() {
		Connection connection = null;
	
	
    try {
        // Verbindung zur Datenbank herstellen
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        //PC Zuhause: 	"jdbc:ucanaccess://C:\\Users\\Ann Manegold\\git\\laufplanerGFS\\GFS\\LaufplanerDatabase.accdb"
        //Laptop:		"jdbc:ucanaccess://C:\\Users\\und\\OneDrive\\Desktop\\GITLaufplaner\\GFSLaufplaner\\GITLaufplaner\\LaufplanerDatabase.accdb"
        //Schule		"jdbc:ucanaccess://C:\\Users\\a.manegold.AD.000\\Desktop\\LaufplanerGit\\GFSLaufplaner\\GITLaufplaner\\LaufplanerDatabase.accdb"
        
      
        connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Ann Manegold\\git\\laufplanerGFS\\GFS\\LaufplanerDatabase.accdb");
    
        
     
        String sql = "SELECT * FROM Eintraege";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        

        while (result.next()) {
            int id = result.getInt("IdLauf");
            double km =result.getDouble("Kilometer");
            double zeit =result.getDouble("Zeit");
            Date datum = result.getDate("Datum");
            double pace = zeit/km;
            MIGLayout.ausgabeLetzteLaufe(id, km, zeit, datum, pace);
            System.out.println(id + ", " + km + ", " + zeit + ", " + datum+ ", "+ pace);
          
        }
        System.out.println("--------------------");
      
        String sqlLetzte = "SELECT *  FROM Eintraege ORDER BY IdLauf DESC LIMIT 10;";
        Statement statementLetzte = connection.createStatement();
        ResultSet resultLetzte = statementLetzte.executeQuery(sqlLetzte);
        
        while (resultLetzte.next()) {
        	int idLetzte = resultLetzte.getInt("IdLauf");
        	double kmLetzte = resultLetzte.getDouble("Kilometer");
        	double zeitLetzte = resultLetzte.getDouble("Zeit");
        	Date datumLetzte = resultLetzte.getDate("Datum");
        	double paceLetzte = zeitLetzte/kmLetzte;
        	System.out.println(idLetzte + ", " + kmLetzte + ", " + zeitLetzte + ", " + datumLetzte+ ", "+ paceLetzte);
        }
        
        
        String sqlZiel = "SELECT * FROM Ziel";
        Statement statementZiel = connection.createStatement();
        ResultSet resultZiel = statementZiel.executeQuery(sqlZiel);

        
        

        while (resultZiel.next()) {
            int idZiel = resultZiel.getInt("IdZiel");
            double ziel =resultZiel.getDouble("Zielpace");
            System.out.println(idZiel + ", " + ziel );
            MIGLayout.ausgabeZiel(ziel);
          
        }
        


    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        // Verbindung zur Datenbank schließen
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
	
	public static void insertLetzterLaufInDatabase(double km, double zeit) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Ann Manegold\\git\\laufplanerGFS\\GFS\\LaufplanerDatabase.accdb");

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
	
	public static void insertZielInDatabase(double ziel) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Ann Manegold\\git\\laufplanerGFS\\GFS\\LaufplanerDatabase.accdb");

            LocalDate currentDate = LocalDate.now();
            java.sql.Date date = java.sql.Date.valueOf(currentDate);

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
