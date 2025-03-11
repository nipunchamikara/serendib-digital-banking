package lk.ac.cmb.ucsc.customer.services.impl;

import lk.ac.cmb.ucsc.customer.dtos.OtpData;
import lk.ac.cmb.ucsc.customer.services.OtpService;

import java.util.concurrent.TimeUnit;

public enum OtpServiceImpl implements OtpService {
    INSTANCE;

    private final static int OTP_DIGITS = 4;
    private final static long OTP_EXPIRY_DURATION = TimeUnit.SECONDS.toMillis(30);

    @Override
    public OtpData generateOtp() {
        final var pow = Math.pow(10, OTP_DIGITS - 1);
        final var otp = (int) (Math.random() * 9 * pow + pow);
        return new OtpData(otp, System.currentTimeMillis() + OTP_EXPIRY_DURATION);
    }

    @Override
    public boolean verifyOtp(OtpData optData, int otp) {
        if (optData == null) return false;
        if (optData.expiryTime() < System.currentTimeMillis()) return false;
        return optData.otp() == otp;
    }
}
