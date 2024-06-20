package main;
import javax.swing.*;
import java.awt.*;

////////// JFrame Window //////////
public class WindowFrame extends JFrame {

    protected JPanel panel;

    public WindowFrame() {
        // Loads panel //
        panel = new WindowPanel();
        InitFrame();    // Initialize the frame components
    }

    private void InitFrame() {
        JFrame frame = new JFrame("Taschenrechner");    // Frame with title
        frame.setSize(380, 380);    // size
        frame.setMinimumSize(new Dimension(330, 330)); // min. size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // closes Console when window is closed
        frame.setLocationRelativeTo(null);  // position from window in middle of the screen
        frame.setLayout(new BorderLayout());    // sets layout to Boarder layout
        frame.add(panel);   // adds panel to frame
        frame.setVisible(true); // sets panel visible
    }

}
