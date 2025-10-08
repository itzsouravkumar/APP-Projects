import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1 = 0, num2 = 0;

    public Calculator() {
        setTitle("Basic Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Buttons for digits and operators
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            display.setText("");
            operator = "";
            num1 = num2 = 0;
        } else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(display.getText());
                double result = 0;
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/":
                        if (num2 != 0) result = num1 / num2;
                        else {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                            display.setText("");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                operator = "";
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
                display.setText("");
            }
        } else {
            // Operator pressed
            try {
                num1 = Double.parseDouble(display.getText());
                operator = command;
                display.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a number first!");
            }
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
