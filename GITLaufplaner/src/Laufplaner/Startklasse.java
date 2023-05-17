package Laufplaner;


import java.awt.EventQueue;
import java.time.LocalDate;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Startklasse {

    public static void main(String[] args) {
        DatenbankVerbindung.verbinden();
        // GUI starten
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MIGLayout();
            }
        });
    }


    

}