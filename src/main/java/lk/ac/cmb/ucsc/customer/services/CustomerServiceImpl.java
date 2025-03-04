package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidPasswordException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidUsernameException;
import lk.ac.cmb.ucsc.customer.exceptions.ProfileLockedException;
import lk.ac.cmb.ucsc.customer.repositories.CASAAccountStore;
import lk.ac.cmb.ucsc.customer.repositories.CASAAccountStoreImpl;
import lk.ac.cmb.ucsc.customer.repositories.ProfileStore;
import lk.ac.cmb.ucsc.customer.repositories.ProfileStoreImpl;
import lk.ac.cmb.ucsc.notification.services.NotificationService;
import lk.ac.cmb.ucsc.otp.services.OtpService;
import lk.ac.cmb.ucsc.otp.services.OtpServiceImpl;

import java.util.concurrent.TimeUnit;

public enum CustomerServiceImpl implements CustomerService {
    INSTANCE;

    private final static int PROFILE_LOCK_TIME_IN_MINUTES = 5;

    private final CASAAccountStore accountStore = CASAAccountStoreImpl.INSTANCE;
    private final ProfileStore profileStore = ProfileStoreImpl.INSTANCE;
    private final OtpService otpService = OtpServiceImpl.INSTANCE;

    CustomerServiceImpl() {
        Profile.setLockPeriod(PROFILE_LOCK_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public CASAAccount getAccount(String accountNumber, String nic, String passportNumber) {
        if (accountNumber == null) throw new IllegalArgumentException("Account number cannot be null");
        if (nic == null && passportNumber == null)
            throw new IllegalArgumentException("Either NIC or passport number must be provided");

        final var account = accountStore.getAccount(accountNumber);

        if (account == null) return null;
        if (nic != null && !nic.equals(account.nic())) return null;
        if (passportNumber != null && !passportNumber.equals(account.passportNumber())) return null;

        return account;
    }

    @Override
    public boolean accountIsRegistered(CASAAccount account) {
        return profileStore.getProfile(account) != null;
    }

    @Override
    public void saveAccount(CASAAccount CASAAccount) {
        accountStore.saveAccount(CASAAccount);
    }

    @Override
    public Profile getProfile(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        final var profile = profileStore.getProfile(username);
        if (profile == null) throw new InvalidUsernameException(username);
        return profile;
    }

    @Override
    public void saveProfile(Profile profile) {
        profileStore.saveProfile(profile);
    }

    @Override
    public boolean isProfileExists(String username) {
        return profileStore.getProfile(username) != null;
    }

    @Override
    public Profile login(String username, String password) {
        final var profile = profileStore.getProfile(username);

        if (profile == null) throw new InvalidUsernameException(username);
        if (profile.isLocked()) throw new ProfileLockedException(profile);
        if (!profile.checkPassword(password)) throw new InvalidPasswordException(profile);

        return profile;
    }

    @Override
    public void sendOtp(CASAAccount account, NotificationService notificationService) {
        otpService.generateOtp(account, notificationService);
    }

    public boolean isOtpInvalid(CASAAccount account, int otp) {
        return !otpService.verifyOtp(account, otp);
    }
}
