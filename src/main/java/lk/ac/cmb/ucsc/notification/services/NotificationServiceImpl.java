package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendOtp(CASAAccount account) {
        System.out.println("Sending OTP notification");
    }
}
