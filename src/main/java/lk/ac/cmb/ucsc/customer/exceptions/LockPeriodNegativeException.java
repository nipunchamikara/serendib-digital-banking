package lk.ac.cmb.ucsc.customer.exceptions;

public class LockPeriodNegativeException extends RuntimeException {
    public LockPeriodNegativeException(long lockPeriod) {
        super("Lock period cannot be negative: '" + lockPeriod + "'");
    }
}
