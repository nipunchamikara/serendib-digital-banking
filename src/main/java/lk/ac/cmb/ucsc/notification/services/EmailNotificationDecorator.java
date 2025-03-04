package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public class EmailNotificationDecorator extends NotificationDecorator {
    private static final Logger logger = FileLogger.getLogger();

    public EmailNotificationDecorator(NotificationService notificationService) {
        super(notificationService);
    }

    public void sendNotification(CASAAccount account, String message) {
        super.sendNotification(account, message);
        if (account.email() == null || account.email().isEmpty()) {
            logger.info("No email set for '" + account.accountNumber() + "'");
            return;
        }
        logger.info("Sending Email to '" + account.email() + "' : '" + message + "'");
        System.out.println("- Email: '" + account.email().replaceAll("(?<=.{2}).(?=.@)", "*") + "'");
    }
}
