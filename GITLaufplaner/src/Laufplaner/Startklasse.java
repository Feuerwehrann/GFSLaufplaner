package Laufplaner;

import java.awt.EventQueue;


/**
 * Startklasse
 * @author Ann Manegold
 *
 */

public class Startklasse {
	/**
	 * main Methode
	 * erstellt ein neues Layout
	 * @param args String
	 */

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