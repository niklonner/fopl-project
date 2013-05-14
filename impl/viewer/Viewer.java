package viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.java.ayatana.ApplicationMenu;

import util.TournamentParser;
import util.ContextException;

public class Viewer extends JFrame implements ActionListener {
    DisplayPanel display;
    CodeArea text;

    public Viewer() {
        super();
        setTitle("Swagger Viewer");

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        setSize(700, 300);
        setBackground(Color.white);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Viewer");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Parse");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        setJMenuBar(menuBar);

        text = new CodeArea();
        JScrollPane scrollPane = new JScrollPane(text);

        display = new DisplayPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                false, display, scrollPane);
        splitPane.setResizeWeight(0.6);

        getContentPane().add(splitPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        ApplicationMenu.tryInstall (this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = text.getText();
        TournamentParser tp = new TournamentParser();
        try {
            tp.parseString(s);
        } catch (ContextException exception) {
            text.setError(exception.getLine());

            System.err.println("Error near: " + exception.getContext());
            exception.printStackTrace();
        }
    }
}
