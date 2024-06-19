package main;

import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {

    protected JFrame frame;
    protected JPanel panel;

    public WindowFrame() {
        panel = new WindowPanel();
        InitFrame();
    }

    private void InitFrame() {
        frame = new JFrame("Taschenrechner");
        frame.setSize(380, 380);
        frame.setMinimumSize(new Dimension(330, 330));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setVisible(true);
    }

}
