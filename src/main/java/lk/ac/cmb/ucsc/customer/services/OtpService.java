package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.OtpData;

public interface OtpService {
    OtpData generateOtp();

    boolean verifyOtp(OtpData optData, int otp);
}