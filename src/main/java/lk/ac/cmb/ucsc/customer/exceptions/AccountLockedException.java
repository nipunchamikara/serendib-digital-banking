package lk.ac.cmb.ucsc.customer.exceptions;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

import java.util.concurrent.TimeUnit;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException(CASAAccount account) {
        super("Account has been locked. Please try again in " + account.getUnlockTimeRemaining(TimeUnit.MINUTES) + " " +
                "minutes");
    }
}
