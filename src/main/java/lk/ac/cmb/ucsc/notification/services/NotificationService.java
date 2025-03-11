package lk.ac.cmb.ucsc.notification.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

public interface NotificationService {
    void sendOtp(CASAAccount account);
}
