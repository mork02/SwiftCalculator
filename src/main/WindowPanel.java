package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

////////// JPanel in Window //////////
public class WindowPanel extends JPanel {

    private JLabel sum_L; // Label to display the current sum/result

    private float sum = 0; // Variable to store the sum/result
    private String op = "="; // Variable to store the current operator
    private String num = ""; // Variable to store the current number input
    private String znum = ""; // Variable to store the previous number input

    // Array of strings representing the calculator buttons
    private final String[] objects = {
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            ".", "0", "/", "=",
            "C"};

    public WindowPanel() {
        setLayout(new BorderLayout()); // Set the layout of the panel to BorderLayout
        InitPanel(); // Initialize the panel components
    }

    private void InitPanel() {
        // Initialize and configure the label
        sum_L = new JLabel(String.valueOf(sum), SwingConstants.RIGHT);
        sum_L.setFont(new Font("Arial", Font.BOLD, 24));
        sum_L.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(sum_L, BorderLayout.NORTH); // Add the label to the top (north) of the panel

        // Create a new panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.insets = new Insets(5, 5, 5, 5); // Set padding
        gbc.weightx = 1.0; // Equal horizontal space for all buttons
        gbc.weighty = 1.0; // Equal vertical space for all buttons

        // Loop through the array of button labels and create buttons
        for (int i = 0; i < objects.length; i++) {
            JButton button = new JButton(objects[i]);
            button.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for buttons
            button.addActionListener(new ButtonClickListener()); // Add action listener to buttons

            // Special layout for the "C" button
            if (objects[i].equals("C")) {
                gbc.gridx = 0; // Position in the first column
                gbc.gridy = 4; // Position in the fifth row
                gbc.gridwidth = 4; // Span all columns
            } else {
                int row = i / 4; // Calculate row position
                gbc.gridx = i % 4; // Calculate column position
                gbc.gridy = row; // Set row position
                gbc.gridwidth = 1; // Span one column
            }
            gbc.gridheight = 1; // Span one row
            panel.add(button, gbc); // Add the button to the panel with the specified constraints
        }

        add(panel, BorderLayout.CENTER); // Add the button panel to the center of the main panel
    }

    // Inner class to handle button click events
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource(); // Get the source button
            String buttonText = button.getText(); // Get the text of the button

            switch (buttonText) {
                case "+": // If the button is an operator
                case "-":
                case "*":
                case "/":
                    if (!num.isEmpty()) { // If there arent a current number input
                        if (!znum.isEmpty()) { // If there arent a previous number input
                            // Calculate the result of the previous operation
                            sum = calculate(Float.parseFloat(znum), Float.parseFloat(num), op);
                            znum = String.valueOf(sum); // Store the result as the previous number
                        } else {
                            znum = num; // Store the current number as the previous number
                        }
                        num = ""; // Reset the current number input
                    }
                    op = buttonText; // Set the current operator
                    break;
                case "=": // If the button is "="
                    if (!num.isEmpty() && !znum.isEmpty()) { // If there arent both current and previous number inputs
                        // Calculate the result of the operation
                        sum = calculate(Float.parseFloat(znum), Float.parseFloat(num), op);
                        num = String.valueOf(sum); // Store the result as the current number
                        znum = ""; // Reset the previous number input
                    }
                    op = "="; // Set the operator to "="
                    break;
                case "C": // If the button is "C"
                    num = ""; // Reset the current number input
                    sum = 0f; // Reset the sum/result
                    znum = ""; // Reset the previous number input
                    break;
                default: // If the button is a number or "."
                    num += buttonText; // Append the button text to the current number input
                    break;
            }

            // Update the label with the current number input or result
            sum_L.setText(num.isEmpty() ? String.valueOf(sum) : num);
        }
    }

    // Method to perform the arithmetic operation
    private float calculate(float x, float y, String op) {
        return switch (op) {
            case "+" -> x + y; // Addition
            case "-" -> x - y; // Subtraction
            case "*" -> x * y; // Multiplication
            case "/" -> { // Division
                if (y == 0) { // Check for division by zero
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    yield 0; // Return 0 if division by zero
                }
                yield x / y; // Return the result of the division
            }
            default -> y; // Return the second operand for any other case
        };
    }
}
