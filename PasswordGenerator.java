package Basic_Project;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordGenerator {

    public byte[] generateSecureRandomData(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomData = new byte[length];
        secureRandom.nextBytes(randomData);
        return randomData;
    }

    public byte[] hashData(byte[] randomData, String hashAlgorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
        return digest.digest(randomData);
    }

    public String encodePassword(byte[] hashedData, int length) {
        String encodedPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(hashedData);
        return encodedPassword.substring(0, Math.min(length, encodedPassword.length()));
    }

    public String generatePassword(int length) {
        try {
            byte[] randomData = generateSecureRandomData(32); // 32 bytes for entropy
            byte[] hashedData = hashData(randomData, "SHA-256");
            return encodePassword(hashedData, length);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not available", e);
        }
    }

    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(16);
        System.out.println("Generated Password: " + password);
    }
}