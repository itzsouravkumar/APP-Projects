package com.studentregistration.ui;

import com.studentregistration.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentTablePanel extends JPanel {
    
    private JTable tblStudents;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private JTextField txtSearch;
    private JButton btnSearch;
    
    public StudentTablePanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Header Panel with title and search
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Table Panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Bottom Panel with buttons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("Registered Students");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(52, 73, 94));
        panel.add(lblTitle, BorderLayout.WEST);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(lblSearch);
        
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        searchPanel.add(txtSearch);
        
        btnSearch = createStyledButton("Search", new Color(52, 152, 219));
        searchPanel.add(btnSearch);
        
        panel.add(searchPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        String[] columns = {
            "Student ID", "First Name", "Last Name", 
            "Email", "Phone", "Age", "Gender", "Course"
        };
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblStudents = new JTable(tableModel);
        tblStudents.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblStudents.setRowHeight(30);
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStudents.setSelectionBackground(new Color(52, 152, 219));
        tblStudents.setSelectionForeground(Color.WHITE);
        tblStudents.setGridColor(new Color(220, 220, 220));
        
        // Header styling
        tblStudents.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblStudents.getTableHeader().setBackground(new Color(52, 73, 94));
        tblStudents.getTableHeader().setForeground(Color.WHITE);
        tblStudents.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        // Column widths
        tblStudents.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblStudents.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblStudents.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblStudents.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblStudents.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblStudents.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblStudents.getColumnModel().getColumn(6).setPreferredWidth(80);
        tblStudents.getColumnModel().getColumn(7).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(tblStudents);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        btnRefresh = createStyledButton("Refresh", new Color(41, 128, 185));
        panel.add(btnRefresh);
        
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
     * Load students into table
     */
    public void loadStudents(List<Student> students) {
        tableModel.setRowCount(0); // Clear existing rows
        
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getAge(),
                student.getGender(),
                student.getCourse()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Get selected student ID from table
     */
    public String getSelectedStudentId() {
        int selectedRow = tblStudents.getSelectedRow();
        if (selectedRow >= 0) {
            return tableModel.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }
    
    /**
     * Clear table selection
     */
    public void clearSelection() {
        tblStudents.clearSelection();
    }
    
    /**
     * Get row count
     */
    public int getRowCount() {
        return tableModel.getRowCount();
    }
    
    // Getters
    public JTable getTblStudents() { return tblStudents; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTextField getTxtSearch() { return txtSearch; }
}