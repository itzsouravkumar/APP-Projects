package com.studentregistration.dao;

import com.studentregistration.config.DatabaseConfig;
import com.studentregistration.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    private final DatabaseConfig dbConfig;
    
    public StudentDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }
    
    /**
     * Add a new student to database
     * @param student Student object to add
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean addStudent(Student student) throws SQLException {
        String sql = """
            INSERT INTO students (student_id, first_name, last_name, email, phone, 
                                 age, gender, course, address) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setStudentParameters(pstmt, student);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                throw new SQLException("Student ID or Email already exists!", e);
            }
            throw e;
        }
    }
    
    /**
     * Update existing student information
     * @param student Student object with updated information
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean updateStudent(Student student) throws SQLException {
        String sql = """
            UPDATE students 
            SET first_name=?, last_name=?, email=?, phone=?, 
                age=?, gender=?, course=?, address=? 
            WHERE student_id=?
        """;
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhone());
            pstmt.setInt(5, student.getAge());
            pstmt.setString(6, student.getGender());
            pstmt.setString(7, student.getCourse());
            pstmt.setString(8, student.getAddress());
            pstmt.setString(9, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Delete a student from database
     * @param studentId Student ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Get student by ID
     * @param studentId Student ID to search
     * @return Student object if found, null otherwise
     * @throws SQLException if database error occurs
     */
    public Student getStudentById(String studentId) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id=?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractStudentFromResultSet(rs);
            }
            return null;
        }
    }
    
    /**
     * Get all students from database
     * @return List of all students
     * @throws SQLException if database error occurs
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY registration_date DESC";
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        }
        
        return students;
    }
    
    /**
     * Search students by name
     * @param name Name to search (first or last name)
     * @return List of matching students
     * @throws SQLException if database error occurs
     */
    public List<Student> searchStudentsByName(String name) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT * FROM students 
            WHERE first_name LIKE ? OR last_name LIKE ?
            ORDER BY first_name
        """;
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + name + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        }
        
        return students;
    }
    
    /**
     * Search students by course
     * @param course Course name to search
     * @return List of students in the course
     * @throws SQLException if database error occurs
     */
    public List<Student> getStudentsByCourse(String course) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE course=? ORDER BY first_name";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, course);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        }
        
        return students;
    }
    
    /**
     * Check if student ID exists
     * @param studentId Student ID to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean studentIdExists(String studentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE student_id=?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
    
    /**
     * Check if email exists
     * @param email Email to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE email=?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
    
    /**
     * Get total student count
     * @return Total number of students
     * @throws SQLException if database error occurs
     */
    public int getTotalStudentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students";
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    /**
     * Helper method to set student parameters in PreparedStatement
     */
    private void setStudentParameters(PreparedStatement pstmt, Student student) 
            throws SQLException {
        pstmt.setString(1, student.getStudentId());
        pstmt.setString(2, student.getFirstName());
        pstmt.setString(3, student.getLastName());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.setInt(6, student.getAge());
        pstmt.setString(7, student.getGender());
        pstmt.setString(8, student.getCourse());
        pstmt.setString(9, student.getAddress());
    }
    
    /**
     * Helper method to extract Student object from ResultSet
     */
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setAge(rs.getInt("age"));
        student.setGender(rs.getString("gender"));
        student.setCourse(rs.getString("course"));
        student.setAddress(rs.getString("address"));
        
        Timestamp timestamp = rs.getTimestamp("registration_date");
        if (timestamp != null) {
            student.setRegistrationDate(timestamp.toLocalDateTime());
        }
        
        return student;
    }
}