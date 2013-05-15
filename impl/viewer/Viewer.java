package viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.java.ayatana.ApplicationMenu;
import java.io.*;

import util.TournamentParser;
import util.ContextException;

public class Viewer extends JFrame implements ActionListener, DisplayPanel.DisplayListener {
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

        JMenuItem parseMenuItem = new JMenuItem("Parse");
        parseMenuItem.addActionListener(this);
        menu.add(parseMenuItem);

        JMenuItem drawMenuItem = new JMenuItem("Draw");
        drawMenuItem.addActionListener(this);
        menu.add(drawMenuItem);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(".");
                int choice = fc.showOpenDialog(display);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    try {
                        display.loadSvg(f.toURL().toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        menu.add(loadMenuItem);

        setJMenuBar(menuBar);

        text = new CodeArea();
        JScrollPane scrollPane = new JScrollPane(text);

        display = new DisplayPanel();
        display.setDisplayListener(this);

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

        switch(e.getActionCommand()) {
            case "Parse":
                String s = text.getText();
                TournamentParser tp = new TournamentParser();
                try {
                     display.drawTournament(tp.parseString(s));
                } catch (ContextException exception) {
                    text.setError(exception.getLine());

                    System.err.println("Error near: " + exception.getContext());
                    exception.printStackTrace();
                }
                break;
            case "Load":
                break;
            default:
                System.out.println("Selected: " + e.getActionCommand());
                break;
        }
    }

    @Override
    public void onBuildComplete() {
    }
}
