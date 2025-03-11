package lk.ac.cmb.ucsc.customer.dtos;

import lk.ac.cmb.ucsc.customer.enums.AccountStatus;

import java.util.concurrent.TimeUnit;

public class CASAAccount {
    private static final int MAX_FAILED_ATTEMPTS = 3;

    private static long disableDuration = TimeUnit.HOURS.toMillis(3);

    private final String nic;
    private final String passportNumber;
    private final String accountNumber;
    private final double balance;
    private final String mobileNumber;
    private final String email;
    private OtpData otpData;
    private long disabledAt;
    private int failedAttempts;
    private AccountStatus status = AccountStatus.INITIATED;

    public CASAAccount(String nic, String passportNumber, String accountNumber, double balance, String mobileNumber,
                       String email) {
        this.nic = nic;
        this.passportNumber = passportNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public static void setDisableDuration(long disableDuration, TimeUnit timeUnit) {
        CASAAccount.disableDuration = timeUnit.toMillis(disableDuration);
    }

    public String getNic() {
        return nic;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public OtpData getOtpData() {
        return otpData;
    }

    public void setOtpData(OtpData otpData) {
        this.otpData = otpData;
    }

    public long getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(long disabledAt) {
        this.disabledAt = disabledAt;
    }

    public AccountStatus getStatus() {
        if (status == AccountStatus.TEMPORARY_LOCKED) {
            if (System.currentTimeMillis() - disabledAt > disableDuration) {
                status = AccountStatus.INITIATED;
            }
        }
        return status;
    }

    public void setStatus(AccountStatus status) {
        if (status == AccountStatus.TEMPORARY_LOCKED) {
            setDisabledAt(System.currentTimeMillis());
        }
        this.status = status;
    }

    public boolean isLocked() {
        return getStatus() == AccountStatus.TEMPORARY_LOCKED;
    }

    public long getUnlockTimeRemaining(TimeUnit timeUnit) {
        return timeUnit.convert(disabledAt + disableDuration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public void incrementFailedAttempts() {
        failedAttempts++;
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            setStatus(AccountStatus.TEMPORARY_LOCKED);
            failedAttempts = 0;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "CASAAccount{nic='%s',passportNumber='%s',accountNumber='%s',balance=%s,mobileNumber='%s',email='%s'}",
                nic, passportNumber, accountNumber, balance, mobileNumber, email
        );
    }
}
