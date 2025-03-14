package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

public abstract class NotificationDecorator implements NotificationService {
    protected NotificationService notificationService;

    public NotificationDecorator(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void sendOtp(CASAAccount account) {
        notificationService.sendOtp(account);
    }
}
