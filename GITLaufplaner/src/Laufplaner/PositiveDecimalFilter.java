package Laufplaner;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter für JTexTField
 * @author Ann Manegold
 *
 */
public class PositiveDecimalFilter extends DocumentFilter {
	/**
	 * Muster, dass die Zahlen haben dürfen wird festgelgt
	 */
    private final Pattern pattern = Pattern.compile("\\d*\\.?\\d*");

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
        /**
         * aktuellen Text als String erhalten
         */
    	String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset);
       /**
        * Ueberpruefen ob der Text gueltig ist
        */
       
        if (isValid(newText)) {
        	/**
        	 * Wenn gueltig den Text einsetzen
        	 */
            super.insertString(fb, offset, text, attrs);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
        if (isValid(newText)) {
        	/**
        	 * Wenn gueltig den Text ersethen
        	 */
            super.replace(fb, offset, length, text, attrs);
        }
    }
    /**
     * 
     * @param text Text der geprueft werden soll
     * @return boolean ob Text gueltig oder nicht
     */
    private boolean isValid(String text) {
        Matcher matcher = pattern.matcher(text);
        /**
         * Ueberpruefen ob der Text eine positive Dezimalzahl ist
         */
        return matcher.matches() && Double.parseDouble(text) >= 0;
    }
}