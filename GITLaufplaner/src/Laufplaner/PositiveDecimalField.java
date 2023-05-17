package Laufplaner;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PositiveDecimalField extends JTextField {

    public PositiveDecimalField() {
        super();
        setColumns(15);
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
                super.insertString(offset, s, attributeSet);
            }
        });
        ((javax.swing.text.AbstractDocument) getDocument()).setDocumentFilter(new PositiveDecimalFilter());
    }
}