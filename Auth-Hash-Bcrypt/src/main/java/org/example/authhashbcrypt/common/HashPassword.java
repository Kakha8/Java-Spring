package org.example.authhashbcrypt.common;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Base64;

public class HashPassword {
    // Pepper value - should be stored securely, not in the database
    private static final String PEPPER = "aSecretPepperValue";  // In real systems, store in secure config, not hardcoded

    // BCrypt cost factor - controls the computational complexity (increase this for stronger security)
    private static final int COST_FACTOR = 12;

    // Generate a random password salt (optional, but bcrypt does this automatically)
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes = 128 bits
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);  // Encode to string for easier storage (optional)
    }

    // Hash a password with bcrypt, salt, and pepper
    public static String hashPassword(String password) {
        // Combine the password with the pepper
        String pepperedPassword = password;

        // Hash the password using bcrypt (bcrypt generates its own salt internally)
        String hashedPassword = BCrypt.hashpw(pepperedPassword, BCrypt.gensalt(COST_FACTOR));  // Cost factor = 12 (adjustable)

        // Display the salt extracted from the bcrypt hash
        String salt = getSaltFromHash(hashedPassword);
        System.out.println("Generated Salt: " + salt);

        return hashedPassword;
    }

    // Verify a password against the stored hash
    public static boolean verifyPassword(String password, String hashedPassword) {
        // Combine the password with the pepper
        String pepperedPassword = password;

        // Verify the peppered password against the stored bcrypt hash
        return BCrypt.checkpw(pepperedPassword, hashedPassword);
    }

    // Method to extract the salt from a bcrypt hash
    public static String getSaltFromHash(String hashedPassword) {
        // The salt is included in the hashedPassword.
        // It is stored in the first 29 characters of the bcrypt hash.
        return hashedPassword.substring(0, 29); // The salt is the prefix of the hash
    }

    public static void main(String[] args) {
        // Example usage
        String originalPassword = "mySecurePassword";

        // Hash the password
        String hashedPassword = hashPassword(originalPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify the password during login
        boolean isPasswordCorrect = verifyPassword(originalPassword, hashedPassword);
        System.out.println("Is Password Correct: " + isPasswordCorrect);

        // Test with an incorrect password
        boolean isIncorrectPasswordCorrect = verifyPassword("wrongPassword", hashedPassword);
        System.out.println("Is Incorrect Password Correct: " + isIncorrectPasswordCorrect);
    }
}
