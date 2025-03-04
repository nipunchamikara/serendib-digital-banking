package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(CASAAccount account, String message) {
        System.out.println("Sending OTP notification");
    }
}
