package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class EmailNotificationDecorator extends NotificationDecorator {
    private static final Logger logger = FileLogger.getLogger();

    public EmailNotificationDecorator(NotificationService notificationService) {
        super(notificationService);
    }

    public void sendOtp(CASAAccount account) {
        super.sendOtp(account);
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            logger.info("No email set for '" + account.getAccountNumber() + "'");
            return;
        }
        logger.info("Sending Email to '" + account.getEmail() + "'. OTP: '" + account.getOtpData().otp() + "'");
        System.out.println("- Email: '" + account.getEmail().replaceAll("(?<=.{2}).(?=.@)", "*") + "'");
    }
}
