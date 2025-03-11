package lk.ac.cmb.ucsc.customer.dtos;

public record OtpData(
        int otp,
        long expiryTime
) {
}
