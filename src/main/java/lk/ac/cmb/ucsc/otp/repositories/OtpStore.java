package lk.ac.cmb.ucsc.otp.repositories;

import lk.ac.cmb.ucsc.otp.dtos.OtpData;

import java.util.concurrent.TimeUnit;

public interface OtpStore {
    OtpData getOtpData(String accountNumber);

    void removeOtpData(String accountNumber);

    void saveOtp(String accountNumber, int otp);

    void setExpiryTime(long expiryTime, TimeUnit timeUnit);
}
