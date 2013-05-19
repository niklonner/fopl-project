package viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class CodeArea extends JTextArea implements DocumentListener {
    int errorLine = 0;

    public static final Color ERROR = Color.decode("#FFE0E0");
    public static final Color BG = Color.decode("#FAFAFA");

    Highlighter.HighlightPainter errorPainter = new DefaultHighlighter.DefaultHighlightPainter(ERROR);

    public CodeArea() {
        super();
        setBackground(BG);
        setTabSize(4);
        setFont(new Font("Monospaced",Font.PLAIN,15));

        Document doc = getDocument();
        doc.addDocumentListener(this);
        errorPainter = new DefaultHighlighter.DefaultHighlightPainter(ERROR);
    }

    public void setError(int line) {
        getHighlighter().removeAllHighlights();
        line--; //lines are 0-indexed
        try {
            int start = getLineStartOffset(line);
            int end = getLineEndOffset(line);
            getHighlighter().addHighlight(start, end, errorPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        getHighlighter().removeAllHighlights();
    }

    @Override
    public void changedUpdate(DocumentEvent e) { }
    @Override
    public void removeUpdate(DocumentEvent e) { }
}
