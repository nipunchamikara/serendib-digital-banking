package lk.ac.cmb.ucsc.verification;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;

import java.util.logging.Logger;

public enum VerificationStrategy {
    CALL_CENTER {
        @Override
        public void verify(CASAAccount account) {
            System.out.println("\nVerifying at Call Center...");
            logger.info("Account '" + account.accountNumber() + "' verified by call center agent.");
            System.out.println("Verification successful");
        }
    },
    BRANCH {
        @Override
        public void verify(CASAAccount account) {
            System.out.println("\nVerifying at Serendib Branch...");
            logger.info("Account '" + account.accountNumber() + "' verified at Serendib Branch.");
            System.out.println("Verification successful");
        }
    };

    final static Logger logger = FileLogger.getLogger();

    public abstract void verify(CASAAccount account);
}
