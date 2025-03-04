package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class SMSNotificationDecorator extends NotificationDecorator {
    private static final Logger logger = FileLogger.getLogger();

    public SMSNotificationDecorator(NotificationService notificationService) {
        super(notificationService);
    }

    public void sendNotification(CASAAccount account, String message) {
        super.sendNotification(account, message);
        if (account.mobileNumber() == null || account.mobileNumber().isEmpty()) {
            logger.info("No mobile number set for '" + account.accountNumber() + "'");
            return;
        }
        logger.info("Sending SMS to '" + account.mobileNumber() + "' : '" + message + "'");
        System.out.println("- Mobile: '" + account.mobileNumber().replaceAll("(?<=.{3}).(?=.{4})", "*") + "'");
    }
}
