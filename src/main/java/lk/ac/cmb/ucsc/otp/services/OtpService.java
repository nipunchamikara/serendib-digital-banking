package lk.ac.cmb.ucsc.otp.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.notification.services.NotificationService;

public interface OtpService {
    void generateOtp(CASAAccount account, NotificationService notificationService);

    boolean verifyOtp(CASAAccount account, int otp);
}
