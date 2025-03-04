package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class AuthenticatorNotificationDecorator extends NotificationDecorator {
    private static final Logger logger = FileLogger.getLogger();

    public AuthenticatorNotificationDecorator(NotificationService notificationService) {
        super(notificationService);
    }

    @Override
    public void sendNotification(CASAAccount account, String message) {
        super.sendNotification(account, message);
        logger.info("Setting up Authenticator for '" + account.accountNumber() + "'");
        System.out.println("Sent OTP to Authenticator App");
    }
}
