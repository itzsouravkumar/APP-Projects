package com.studentregistration.util;

import java.util.regex.Pattern;

public class InputValidator {
    
    // Validation Patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10}$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[A-Za-z][A-Za-z\\s]{1,49}$"
    );
    
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile(
        "^[A-Z0-9]{3,20}$"
    );
  
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ValidationResult(false, "Email is required");
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return new ValidationResult(false, 
                "Invalid email format. Example: student@example.com");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Validate phone number
     * @param phone Phone number to validate
     * @return ValidationResult with status and message
     */
    public static ValidationResult validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return new ValidationResult(false, "Phone number is required");
        }
        
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            return new ValidationResult(false, 
                "Phone must be exactly 10 digits");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    public static ValidationResult validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            return new ValidationResult(false, fieldName + " is required");
        }
        
        if (name.trim().length() < 2) {
            return new ValidationResult(false, 
                fieldName + " must be at least 2 characters");
        }
        
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            return new ValidationResult(false, 
                fieldName + " should contain only letters and spaces");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Validate student ID
     * @param studentId Student ID to validate
     * @return ValidationResult with status and message
     */
    public static ValidationResult validateStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return new ValidationResult(false, "Student ID is required");
        }
        
        if (!STUDENT_ID_PATTERN.matcher(studentId.trim().toUpperCase()).matches()) {
            return new ValidationResult(false, 
                "Student ID must be 3-20 characters (letters and numbers only)");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Validate age
     * @param age Age to validate
     * @return ValidationResult with status and message
     */
    public static ValidationResult validateAge(int age) {
        if (age < 16) {
            return new ValidationResult(false, 
                "Age must be at least 16 years");
        }
        
        if (age > 100) {
            return new ValidationResult(false, 
                "Age must be less than 100 years");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Validate address
     * @param address Address to validate
     * @return ValidationResult with status and message
     */
    public static ValidationResult validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return new ValidationResult(false, "Address is required");
        }
        
        if (address.trim().length() < 10) {
            return new ValidationResult(false, 
                "Address must be at least 10 characters");
        }
        
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Validate non-empty string
     * @param value Value to validate
     * @param fieldName Field name for error message
     * @return ValidationResult with status and message
     */
    public static ValidationResult validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return new ValidationResult(false, fieldName + " is required");
        }
        return new ValidationResult(true, "Valid");
    }
    
    /**
     * Inner class to hold validation result
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String message;
        
        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getMessage() {
            return message;
        }
    }
}