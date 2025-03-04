package lk.ac.cmb.ucsc.otp.repositories;

import lk.ac.cmb.ucsc.otp.dtos.OtpData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public enum OtpStoreImpl implements OtpStore {
    INSTANCE;

    private final Map<String, OtpData> otpStore = new HashMap<>();
    private long expiryTimeInMinutes = 5;

    OtpStoreImpl() {
    }

    public void saveOtp(String accountNumber, int otp) {
        final var expiryTime = System.currentTimeMillis() + expiryTimeInMinutes * 60 * 1000;
        final var otpData = new OtpData(otp, expiryTime);
        otpStore.put(accountNumber, otpData);
    }

    public OtpData getOtpData(String accountNumber) {
        return otpStore.get(accountNumber);
    }

    public void removeOtpData(String accountNumber) {
        otpStore.remove(accountNumber);
    }

    public void setExpiryTime(long expiryTimeInMinutes, TimeUnit timeUnit) {
        this.expiryTimeInMinutes = timeUnit.toMinutes(expiryTimeInMinutes);
    }
}
