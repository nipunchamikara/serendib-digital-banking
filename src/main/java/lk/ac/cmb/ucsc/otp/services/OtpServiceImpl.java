package lk.ac.cmb.ucsc.otp.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.notification.services.NotificationService;
import lk.ac.cmb.ucsc.otp.repositories.OtpStore;
import lk.ac.cmb.ucsc.otp.repositories.OtpStoreImpl;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public enum OtpServiceImpl implements OtpService {
    INSTANCE;

    private final static int DIGITS = 4;
    private final static int EXPIRY_TIME_IN_MINUTES = 5;
    private final static Logger logger = FileLogger.getLogger();

    private final OtpStore otpStore;

    OtpServiceImpl() {
        this.otpStore = OtpStoreImpl.INSTANCE;
        this.otpStore.setExpiryTime(EXPIRY_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    public void generateOtp(CASAAccount account, NotificationService notificationService) {
        final var pow = Math.pow(10, DIGITS - 1);
        final var otp = (int) (Math.random() * 9 * pow + pow);
        otpStore.saveOtp(account.accountNumber(), otp);
        notificationService.sendNotification(account, "Your OTP is '" + otp + "'");
        logger.info("Generated OTP for account '" + account.accountNumber() + "' : '" + otp + "'");
    }

    public boolean verifyOtp(CASAAccount account, int otp) {
        final var otpData = otpStore.getOtpData(account.accountNumber());
        if (otpData == null)
            return false;
        if (otpData.otp() != otp || otpData.expiryTime() < System.currentTimeMillis())
            return false;
        otpStore.removeOtpData(account.accountNumber());
        return true;
    }
}
