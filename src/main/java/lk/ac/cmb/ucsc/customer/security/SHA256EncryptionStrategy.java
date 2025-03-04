package lk.ac.cmb.ucsc.customer.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256EncryptionStrategy implements PasswordEncryptionStrategy {

    @Override
    public String encrypt(String password) {
        try {
            final var digest = MessageDigest.getInstance("SHA-256");
            final var hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while encrypting password: " + e.getMessage());
        }
    }

    @Override
    public boolean check(String password, String encryptedPassword) {
        return encrypt(password).equals(encryptedPassword);
    }
}
