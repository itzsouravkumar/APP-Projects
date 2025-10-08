package com.studentregistration.ui;

import com.studentregistration.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StudentFormPanel extends JPanel {
    
    private JTextField txtStudentId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JSpinner spnAge;
    private JComboBox<String> cmbGender;
    private JComboBox<String> cmbCourse;
    private JTextArea txtAddress;
    
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    
    public StudentFormPanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Title
        JLabel lblTitle = new JLabel("Student Information");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(52, 73, 94));
        add(lblTitle, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Initialize components
        txtStudentId = createStyledTextField();
        txtFirstName = createStyledTextField();
        txtLastName = createStyledTextField();
        txtEmail = createStyledTextField();
        txtPhone = createStyledTextField();
        
        spnAge = new JSpinner(new SpinnerNumberModel(18, 16, 100, 1));
        spnAge.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cmbGender.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbCourse = new JComboBox<>(new String[]{
            "Computer Science", 
            "Information Technology", 
            "Electronics",
            "Mechanical Engineering", 
            "Civil Engineering", 
            "Business Administration"
        });
        cmbCourse.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        txtAddress = new JTextArea(3, 20);
        txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        txtAddress.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        
        // Add components to form
        int row = 0;
        addFormField(panel, gbc, "Student ID:", txtStudentId, row++);
        addFormField(panel, gbc, "First Name:", txtFirstName, row++);
        addFormField(panel, gbc, "Last Name:", txtLastName, row++);
        addFormField(panel, gbc, "Email:", txtEmail, row++);
        addFormField(panel, gbc, "Phone:", txtPhone, row++);
        addFormField(panel, gbc, "Age:", spnAge, row++);
        addFormField(panel, gbc, "Gender:", cmbGender, row++);
        addFormField(panel, gbc, "Course:", cmbCourse, row++);
        addFormField(panel, gbc, "Address:", scrollAddress, row++);
        
        return panel;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return textField;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, 
                              String labelText, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(52, 73, 94));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panel.setBackground(Color.WHITE);
        
        btnAdd = createStyledButton("Add", new Color(46, 204, 113));
        btnUpdate = createStyledButton("Update", new Color(52, 152, 219));
        btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        btnClear = createStyledButton("Clear", new Color(149, 165, 166));
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    /**
     * Get Student object from form inputs
     */
    public Student getStudentFromForm() {
        Student student = new Student();
        student.setStudentId(txtStudentId.getText().trim().toUpperCase());
        student.setFirstName(txtFirstName.getText().trim());
        student.setLastName(txtLastName.getText().trim());
        student.setEmail(txtEmail.getText().trim());
        student.setPhone(txtPhone.getText().trim());
        student.setAge((Integer) spnAge.getValue());
        student.setGender((String) cmbGender.getSelectedItem());
        student.setCourse((String) cmbCourse.getSelectedItem());
        student.setAddress(txtAddress.getText().trim());
        return student;
    }
    
    /**
     * Load Student data into form
     */
    public void loadStudentToForm(Student student) {
        if (student != null) {
            txtStudentId.setText(student.getStudentId());
            txtFirstName.setText(student.getFirstName());
            txtLastName.setText(student.getLastName());
            txtEmail.setText(student.getEmail());
            txtPhone.setText(student.getPhone());
            spnAge.setValue(student.getAge());
            cmbGender.setSelectedItem(student.getGender());
            cmbCourse.setSelectedItem(student.getCourse());
            txtAddress.setText(student.getAddress());
        }
    }
    
    /**
     * Clear all form fields
     */
    public void clearForm() {
        txtStudentId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        spnAge.setValue(18);
        cmbGender.setSelectedIndex(0);
        cmbCourse.setSelectedIndex(0);
        txtAddress.setText("");
        txtStudentId.requestFocus();
    }
    
    /**
     * Enable/disable Student ID field
     */
    public void setStudentIdEditable(boolean editable) {
        txtStudentId.setEditable(editable);
        txtStudentId.setBackground(editable ? Color.WHITE : new Color(240, 240, 240));
    }
    
    // Getters for buttons
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    
    // Getters for text fields
    public JTextField getTxtStudentId() { return txtStudentId; }
    public JTextField getTxtFirstName() { return txtFirstName; }
    public JTextField getTxtLastName() { return txtLastName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtPhone() { return txtPhone; }
}