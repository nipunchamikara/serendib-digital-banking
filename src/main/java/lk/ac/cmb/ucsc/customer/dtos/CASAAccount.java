package lk.ac.cmb.ucsc.customer.dtos;

public record CASAAccount(
        String nic,
        String passportNumber,
        String accountNumber,
        double balance,
        String mobileNumber,
        String email
) {

    @Override
    public String toString() {
        return String.format(
                "CASAAccount{nic='%s',passportNumber='%s',accountNumber='%s',balance=%s,mobileNumber='%s',email='%s'}",
                nic, passportNumber, accountNumber, balance, mobileNumber, email
        );
    }
}
