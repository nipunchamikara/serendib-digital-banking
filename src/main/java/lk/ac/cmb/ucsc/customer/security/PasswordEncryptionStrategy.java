package lk.ac.cmb.ucsc.customer.security;

public interface PasswordEncryptionStrategy {
    String encrypt(String password);

    boolean check(String password, String encryptedPassword);
}
