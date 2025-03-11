package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.notification.services.NotificationService;

public interface CustomerService {
    CASAAccount getAccount(String accountNumber, String nic, String passportNumber);

    boolean accountIsRegistered(CASAAccount account);

    void saveAccount(CASAAccount CASAAccount);

    Profile getProfile(String username);

    void saveProfile(Profile profile);

    boolean isProfileExists(String username);

    Profile login(String username, String password);

    void sendOtp(CASAAccount account, NotificationService notificationService);

    boolean checkOtp(CASAAccount account, int otp);

    void updatePassword(String username, String password);
}
