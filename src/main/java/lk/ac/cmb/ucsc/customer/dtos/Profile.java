package lk.ac.cmb.ucsc.customer.dtos;

import lk.ac.cmb.ucsc.customer.enums.ProfileStatus;
import lk.ac.cmb.ucsc.customer.security.PasswordEncryptionStrategy;
import lk.ac.cmb.ucsc.customer.security.SHA256EncryptionStrategy;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class Profile {
    public static final int MAX_INCORRECT_ATTEMPTS = 3;
    private static final Logger logger = FileLogger.getLogger();
    private static final PasswordEncryptionStrategy encryptionStrategy = new SHA256EncryptionStrategy();

    private final CASAAccount account;
    private final String username;
    private final String displayName;
    private final String password;
    private ProfileStatus status;
    private int incorrectAttempts;

    public Profile(CASAAccount account, String username, String password, String displayName) {
        this.account = account;
        this.username = username;
        this.password = encryptionStrategy.encrypt(password);
        this.displayName = displayName;
        this.status = ProfileStatus.INITIATED;
        this.incorrectAttempts = 0;
    }

    public CASAAccount getAccount() {
        return account;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean checkPassword(String password) {
        if (!encryptionStrategy.check(password, this.password)) {
            incorrectAttempts++;
            if (incorrectAttempts >= MAX_INCORRECT_ATTEMPTS) {
                status = ProfileStatus.TEMPORARY_LOCKED;
                logger.info("Profile locked for '" + username + "'");
            }
            return false;
        }
        incorrectAttempts = 0;
        return true;
    }

    public boolean isLocked() {
        return getStatus() == ProfileStatus.TEMPORARY_LOCKED;
    }

}
