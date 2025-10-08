import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class shoppingCart extends JFrame implements ActionListener {
    // Declare checkboxes
    JCheckBox cbLaptop, cbPhone, cbHeadphones;
    JButton btnGenerateBill;
    JTextArea billArea;

    public shoppingCart() {
        setTitle("Shopping Cart Simulation");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize checkboxes with item names and prices
        cbLaptop = new JCheckBox("Laptop - $800");
        cbPhone = new JCheckBox("Phone - $500");
        cbHeadphones = new JCheckBox("Headphones - $150");

        // Button
        btnGenerateBill = new JButton("Generate Bill");
        btnGenerateBill.addActionListener(this);

        // Text area for displaying bill
        billArea = new JTextArea(10, 30);
        billArea.setEditable(false);

        // Add components to frame
        add(cbLaptop);
        add(cbPhone);
        add(cbHeadphones);
        add(btnGenerateBill);
        add(new JScrollPane(billArea));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int total = 0;
        StringBuilder bill = new StringBuilder("Items Selected:\n");

        if (cbLaptop.isSelected()) {
            bill.append("Laptop - $800\n");
            total += 800;
        }
        if (cbPhone.isSelected()) {
            bill.append("Phone - $500\n");
            total += 500;
        }
        if (cbHeadphones.isSelected()) {
            bill.append("Headphones - $150\n");
            total += 150;
        }

        bill.append("\nTotal Price: $" + total);

        billArea.setText(bill.toString());
    }

    public static void main(String[] args) {
        new shoppingCart();
    }
}
