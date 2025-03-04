package lk.ac.cmb.ucsc.otp.dtos;

public record OtpData(
        int otp,
        long expiryTime
) {
}
