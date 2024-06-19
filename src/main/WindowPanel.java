package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowPanel extends JPanel {

    private JLabel sum_L;

    private float sum = 0;
    private String op = "=";
    private String num = "";
    private String znum = "";

    private final String[] objects = {
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            ".", "0", "/", "=",
            "C"};

    public WindowPanel() {
        setLayout(new BorderLayout());
        InitPanel();
    }

    private void InitPanel() {
        sum_L = new JLabel(String.valueOf(sum), SwingConstants.RIGHT);
        sum_L.setFont(new Font("Arial", Font.BOLD, 24));
        sum_L.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(sum_L, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < objects.length; i++) {
            JButton button = new JButton(objects[i]);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());

            if (objects[i].equals("C")) {
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 4;
            } else {
                int row = i / 4;
                gbc.gridx = i % 4;
                gbc.gridy = row;
                gbc.gridwidth = 1;
            }
            gbc.gridheight = 1;
            panel.add(button, gbc);
        }

        add(panel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            switch (buttonText) {
                case "+":
                case "-":
                case "*":
                case "/":
                    if (!num.isEmpty()) {
                        if (!znum.isEmpty()) {
                            sum = calculate(Float.parseFloat(znum), Float.parseFloat(num), op);
                            znum = String.valueOf(sum);
                        } else {
                            znum = num;
                        }
                        num = "";
                    }
                    op = buttonText;
                    break;
                case "=":
                    if (!num.isEmpty() && !znum.isEmpty()) {
                        sum = calculate(Float.parseFloat(znum), Float.parseFloat(num), op);
                        num = String.valueOf(sum);
                        znum = "";
                    }
                    op = "=";
                    break;
                case "C":
                    num = "";
                    sum = 0f;
                    znum = "";
                    break;
                default:
                    num += buttonText;
                    break;
            }

            sum_L.setText(num.isEmpty() ? String.valueOf(sum) : num);
            // System.out.println("Number: " + num + " | Sum: " + sum);
        }
    }

    private float calculate(float x, float y, String op) {
        return switch (op) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> {
                if (y == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    yield 0;
                }
                yield x / y;
            }
            default -> y;
        };
    }
}
