package viewer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class DisplayPanel extends JPanel {

    public DisplayPanel() {
        super();
        setBackground(Color.decode("#202020"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }
}
