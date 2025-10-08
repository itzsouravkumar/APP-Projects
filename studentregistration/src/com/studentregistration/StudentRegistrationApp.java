package com.studentregistration;

import com.studentregistration.config.DatabaseConfig;
import com.studentregistration.model.Student;
import com.studentregistration.service.StudentService;
import com.studentregistration.service.StudentService.ServiceResponse;
import com.studentregistration.ui.StudentFormPanel;
import com.studentregistration.ui.StudentTablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class StudentRegistrationApp extends JFrame {
    
    private StudentService studentService;
    private StudentFormPanel formPanel;
    private StudentTablePanel tablePanel;
    
    public StudentRegistrationApp() {
        initializeApplication();
        initComponents();
        setupEventHandlers();
        loadAllStudents();
    }
    
    private void initializeApplication() {
        // Initialize database
        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        
        // Test connection
        if (!dbConfig.testConnection()) {
            showError("Failed to connect to database!\n" +
                     "Please check your database configuration.");
            System.exit(1);
        }
        
        // Initialize database tables
        dbConfig.initializeDatabase();
        
        // Initialize service
        studentService = new StudentService();
    }
    
    private void initComponents() {
        setTitle("Student Registration Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 245));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        formPanel = new StudentFormPanel();
        tablePanel = new StudentTablePanel();
        
        contentPanel.add(formPanel);
        contentPanel.add(tablePanel);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status Bar
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
        
        setSize(1200, 700);
        setLocationRelativeTo(null);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitle = new JLabel("🎓 Student Registration System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        panel.add(lblTitle);
        
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel lblStatus = new JLabel("Ready");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(Color.WHITE);
        panel.add(lblStatus);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Add Button
        formPanel.getBtnAdd().addActionListener(e -> addStudent());
        
        // Update Button
        formPanel.getBtnUpdate().addActionListener(e -> updateStudent());
        
        // Delete Button
        formPanel.getBtnDelete().addActionListener(e -> deleteStudent());
        
        // Clear Button
        formPanel.getBtnClear().addActionListener(e -> clearForm());
        
        // Refresh Button
        tablePanel.getBtnRefresh().addActionListener(e -> loadAllStudents());
        
        // Search Button
        tablePanel.getBtnSearch().addActionListener(e -> searchStudents());
        
        // Search on Enter key
        tablePanel.getTxtSearch().addActionListener(e -> searchStudents());
        
        // Table Row Selection
        tablePanel.getTblStudents().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
    }
    
    private void addStudent() {
        Student student = formPanel.getStudentFromForm();
        ServiceResponse response = studentService.registerStudent(student);
        
        if (response.isSuccess()) {
            showSuccess(response.getMessage());
            clearForm();
            loadAllStudents();
        } else {
            showError(response.getMessage());
        }
    }
    
    private void updateStudent() {
        String studentId = formPanel.getTxtStudentId().getText().trim();
        
        if (studentId.isEmpty()) {
            showError("Please select a student to update!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to update this student?",
            "Confirm Update",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Student student = formPanel.getStudentFromForm();
            ServiceResponse response = studentService.updateStudent(student);
            
            if (response.isSuccess()) {
                showSuccess(response.getMessage());
                clearForm();
                loadAllStudents();
            } else {
                showError(response.getMessage());
            }
        }
    }
    
    private void deleteStudent() {
        String studentId = formPanel.getTxtStudentId().getText().trim();
        
        if (studentId.isEmpty()) {
            showError("Please select a student to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this student?\nThis action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ServiceResponse response = studentService.deleteStudent(studentId);
            
            if (response.isSuccess()) {
                showSuccess(response.getMessage());
                clearForm();
                loadAllStudents();
            } else {
                showError(response.getMessage());
            }
        }
    }
    
    private void clearForm() {
        formPanel.clearForm();
        formPanel.setStudentIdEditable(true);
        tablePanel.clearSelection();
    }
    
    private void loadAllStudents() {
        List<Student> students = studentService.getAllStudents();
        tablePanel.loadStudents(students);
        updateStatusBar();
    }
    
    private void searchStudents() {
        String searchText = tablePanel.getTxtSearch().getText().trim();
        
        if (searchText.isEmpty()) {
            loadAllStudents();
            return;
        }
        
        List<Student> students = studentService.searchStudentsByName(searchText);
        tablePanel.loadStudents(students);
        
        if (students.isEmpty()) {
            showInfo("No students found matching: " + searchText);
        } else {
            showInfo("Found " + students.size() + " student(s)");
        }
    }
    
    private void loadSelectedStudent() {
        String studentId = tablePanel.getSelectedStudentId();
        
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                formPanel.loadStudentToForm(student);
                formPanel.setStudentIdEditable(false);
            }
        }
    }
    
    private void updateStatusBar() {
        int totalStudents = studentService.getTotalStudentCount();
        ((JLabel) ((JPanel) getContentPane().getComponent(2)).getComponent(0))
            .setText("Total Students: " + totalStudents);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Information",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run application on EDT
        SwingUtilities.invokeLater(() -> {
            StudentRegistrationApp app = new StudentRegistrationApp();
            app.setVisible(true);
        });
    }
}