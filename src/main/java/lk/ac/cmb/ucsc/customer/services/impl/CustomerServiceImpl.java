package lk.ac.cmb.ucsc.customer.services.impl;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.customer.exceptions.AccountLockedException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidPasswordException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidUsernameException;
import lk.ac.cmb.ucsc.customer.exceptions.ProfileLockedException;
import lk.ac.cmb.ucsc.customer.repositories.CASAAccountStore;
import lk.ac.cmb.ucsc.customer.repositories.CASAAccountStoreImpl;
import lk.ac.cmb.ucsc.customer.repositories.ProfileStore;
import lk.ac.cmb.ucsc.customer.repositories.ProfileStoreImpl;
import lk.ac.cmb.ucsc.customer.services.CustomerService;
import lk.ac.cmb.ucsc.customer.services.OtpService;
import lk.ac.cmb.ucsc.notification.services.NotificationService;

public enum CustomerServiceImpl implements CustomerService {
    INSTANCE;

    private final CASAAccountStore accountStore = CASAAccountStoreImpl.INSTANCE;
    private final ProfileStore profileStore = ProfileStoreImpl.INSTANCE;
    private final OtpService otpService = OtpServiceImpl.INSTANCE;

    CustomerServiceImpl() {
    }

    @Override
    public CASAAccount getAccount(String accountNumber, String nic, String passportNumber) {
        if (accountNumber == null) throw new IllegalArgumentException("Account number cannot be null");
        if (nic == null && passportNumber == null)
            throw new IllegalArgumentException("Either NIC or passport number must be provided");

        final var account = accountStore.getAccount(accountNumber);

        if (account == null) return null;
        if (nic != null && !nic.equals(account.getNic())) return null;
        if (passportNumber != null && !passportNumber.equals(account.getPassportNumber())) return null;

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
        if (account == null) throw new IllegalArgumentException("Account not found");
        if (account.isLocked()) throw new AccountLockedException(account);
        account.setOtpData(otpService.generateOtp());
        saveAccount(account);
        notificationService.sendOtp(account);
    }

    @Override
    public boolean checkOtp(CASAAccount account, int otp) {
        if (account == null) throw new IllegalArgumentException("Account not found");
        if (account.isLocked()) throw new AccountLockedException(account);
        if (otpService.verifyOtp(account.getOtpData(), otp)) {
            account.setOtpData(null);
            saveAccount(account);
            return true;
        } else {
            account.incrementFailedAttempts();
            saveAccount(account);
            return false;
        }
    }

    @Override
    public void updatePassword(String username, String password) {
        final var profile = getProfile(username);
        profile.setPassword(password);
        saveProfile(profile);
    }
}
