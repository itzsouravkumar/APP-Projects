package com.studentregistration.service;

import com.studentregistration.dao.StudentDAO;
import com.studentregistration.model.Student;
import com.studentregistration.util.InputValidator;
import com.studentregistration.util.InputValidator.ValidationResult;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    
    private final StudentDAO studentDAO;
    
    public StudentService() {
        this.studentDAO = new StudentDAO();
    }
    

    public ServiceResponse registerStudent(Student student) {
        try {
            // Validate all inputs
            ServiceResponse validationResponse = validateStudent(student);
            if (!validationResponse.isSuccess()) {
                return validationResponse;
            }
            
            // Check for duplicate student ID
            if (studentDAO.studentIdExists(student.getStudentId())) {
                return new ServiceResponse(false, 
                    "Student ID already exists! Please use a different ID.");
            }
            
            // Check for duplicate email
            if (studentDAO.emailExists(student.getEmail())) {
                return new ServiceResponse(false, 
                    "Email already registered! Please use a different email.");
            }
            
            // Add student to database
            boolean success = studentDAO.addStudent(student);
            
            if (success) {
                return new ServiceResponse(true, 
                    "Student registered successfully!");
            } else {
                return new ServiceResponse(false, 
                    "Failed to register student. Please try again.");
            }
            
        } catch (SQLException e) {
            return new ServiceResponse(false, 
                "Database error: " + e.getMessage());
        }
    }

    public ServiceResponse updateStudent(Student student) {
        try {
            // Validate all inputs
            ServiceResponse validationResponse = validateStudent(student);
            if (!validationResponse.isSuccess()) {
                return validationResponse;
            }
            
            // Check if student exists
            Student existingStudent = studentDAO.getStudentById(student.getStudentId());
            if (existingStudent == null) {
                return new ServiceResponse(false, 
                    "Student not found!");
            }
            
            // Check if email is being changed and if new email exists
            if (!existingStudent.getEmail().equals(student.getEmail())) {
                if (studentDAO.emailExists(student.getEmail())) {
                    return new ServiceResponse(false, 
                        "Email already exists! Please use a different email.");
                }
            }
            
            // Update student in database
            boolean success = studentDAO.updateStudent(student);
            
            if (success) {
                return new ServiceResponse(true, 
                    "Student updated successfully!");
            } else {
                return new ServiceResponse(false, 
                    "Failed to update student. Please try again.");
            }
            
        } catch (SQLException e) {
            return new ServiceResponse(false, 
                "Database error: " + e.getMessage());
        }
    }
    
    public ServiceResponse deleteStudent(String studentId) {
        try {
            if (studentId == null || studentId.trim().isEmpty()) {
                return new ServiceResponse(false, 
                    "Student ID is required!");
            }
            
            // Check if student exists
            if (!studentDAO.studentIdExists(studentId)) {
                return new ServiceResponse(false, 
                    "Student not found!");
            }
            
            boolean success = studentDAO.deleteStudent(studentId);
            
            if (success) {
                return new ServiceResponse(true, 
                    "Student deleted successfully!");
            } else {
                return new ServiceResponse(false, 
                    "Failed to delete student. Please try again.");
            }
            
        } catch (SQLException e) {
            return new ServiceResponse(false, 
                "Database error: " + e.getMessage());
        }
    }
    
    /**
     * Get student by ID
     * @param studentId Student ID to search
     * @return Student object if found, null otherwise
     */
    public Student getStudentById(String studentId) {
        try {
            return studentDAO.getStudentById(studentId);
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            return List.of(); // Return empty list
        }
    }
    
    /**
     * Search students by name
     * @param name Name to search
     * @return List of matching students
     */
    public List<Student> searchStudentsByName(String name) {
        try {
            return studentDAO.searchStudentsByName(name);
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Get students by course
     * @param course Course name
     * @return List of students in the course
     */
    public List<Student> getStudentsByCourse(String course) {
        try {
            return studentDAO.getStudentsByCourse(course);
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            return List.of();
        }
    }
    

    public int getTotalStudentCount() {
        try {
            return studentDAO.getTotalStudentCount();
        } catch (SQLException e) {
            System.err.println("Error counting students: " + e.getMessage());
            return 0;
        }
    }

    private ServiceResponse validateStudent(Student student) {
        if (student == null) {
            return new ServiceResponse(false, "Student data is required!");
        }
        
        // Validate Student ID
        ValidationResult result = InputValidator.validateStudentId(student.getStudentId());
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate First Name
        result = InputValidator.validateName(student.getFirstName(), "First Name");
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Last Name
        result = InputValidator.validateName(student.getLastName(), "Last Name");
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Email
        result = InputValidator.validateEmail(student.getEmail());
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Phone
        result = InputValidator.validatePhone(student.getPhone());
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Age
        result = InputValidator.validateAge(student.getAge());
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Gender
        result = InputValidator.validateRequired(student.getGender(), "Gender");
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        // Validate Course
        result = InputValidator.validateRequired(student.getCourse(), "Course");
        if (!result.isValid()) {
            return new ServiceResponse(false, result.getMessage());
        }
        
        return new ServiceResponse(true, "Validation successful");
    }
    
    /**
     * Inner class for service response
     */
    public static class ServiceResponse {
        private final boolean success;
        private final String message;
        
        public ServiceResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
    }
}