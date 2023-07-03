package Laufplaner;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Klasse fuer das PositiveDecimalField
 * @author Ann Manegold
 *
 */
public class PositiveDecimalField extends JTextField {
	/**
	 * Methode um ein Field mit bestimmter Breite und dem Filter zu erstellen
	 */

    public PositiveDecimalField() {
        super();
        setColumns(15);
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
                super.insertString(offset, s, attributeSet);
            }
        });
        /**
         * Hinzufuegen des Filters
         */
        ((javax.swing.text.AbstractDocument) getDocument()).setDocumentFilter(new PositiveDecimalFilter());
    }
}