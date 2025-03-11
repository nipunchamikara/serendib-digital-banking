package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class SMSNotificationDecorator extends NotificationDecorator {
    private static final Logger logger = FileLogger.getLogger();

    public SMSNotificationDecorator(NotificationService notificationService) {
        super(notificationService);
    }

    public void sendOtp(CASAAccount account) {
        super.sendOtp(account);
        if (account.getMobileNumber() == null || account.getMobileNumber().isEmpty()) {
            logger.info("No mobile number set for '" + account.getAccountNumber() + "'");
            return;
        }
        logger.info("Sending SMS to '" + account.getMobileNumber() + "'. OTP: '" + account.getOtpData().otp() + "'");
        System.out.println("- Mobile: '" + account.getMobileNumber().replaceAll("(?<=.{3}).(?=.{4})", "*") + "'");
    }
}
