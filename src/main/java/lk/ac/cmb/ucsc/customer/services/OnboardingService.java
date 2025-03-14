package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.customer.exceptions.AccountLockedException;
import lk.ac.cmb.ucsc.customer.services.impl.CustomerServiceImpl;
import lk.ac.cmb.ucsc.customer.validator.*;
import lk.ac.cmb.ucsc.notification.services.EmailNotificationDecorator;
import lk.ac.cmb.ucsc.notification.services.NotificationService;
import lk.ac.cmb.ucsc.notification.services.NotificationServiceImpl;
import lk.ac.cmb.ucsc.notification.services.SMSNotificationDecorator;
import lk.ac.cmb.ucsc.utils.input.IntegerInput;
import lk.ac.cmb.ucsc.utils.input.PasswordInput;
import lk.ac.cmb.ucsc.utils.input.StringInput;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;
import lk.ac.cmb.ucsc.utils.selection.MenuSelectionBuilder;
import lk.ac.cmb.ucsc.verification.VerificationStrategy;

import java.util.Scanner;
import java.util.logging.Logger;

public class OnboardingService {
    private final static Logger logger = FileLogger.getLogger();
    private final CustomerService customerService;
    private final Scanner scanner;

    public OnboardingService(Scanner scanner) {
        this.customerService = CustomerServiceImpl.INSTANCE;
        this.scanner = scanner;
    }

    public void onboardCustomer() {
        final NotificationService notificationService = new SMSNotificationDecorator(
                new EmailNotificationDecorator(new NotificationServiceImpl())
        );

        new StringInput("Enter language").promptUser(scanner);

        final var idOption = new MenuSelectionBuilder()
                .setTitle("Select ID type")
                .addOption("NIC")
                .addOption("Passport")
                .build()
                .promptUser(scanner);

        String nic = null;
        String passport = null;

        if (idOption == 1)
            nic = new StringInput("Enter NIC", new NICValidator()).promptUser(scanner);
        else
            passport = new StringInput("Enter Passport", new PassportValidator()).promptUser(scanner);

        final var account = customerService.getAccount(
                new StringInput("Enter CASA account number", new CASAAccountNumberValidator()).promptUser(scanner),
                nic, passport
        );
        if (account == null) {
            System.out.println("Account not found");
            return;
        } else if (customerService.accountIsRegistered(account)) {
            System.out.println("Account is already registered");
            return;
        }

        try {
            customerService.sendOtp(account, notificationService);
            while (!account.isLocked()) {
                if (customerService.checkOtp(account, new IntegerInput("Enter OTP").promptUser(scanner)))
                    break;
                System.out.println("Invalid OTP");
            }
            if (account.isLocked()) {
                throw new AccountLockedException(account);
            }
        } catch (AccountLockedException e) {
            System.out.println(e.getMessage());
            return;
        }

        final var verifyOption = new MenuSelectionBuilder()
                .setTitle("Select verification method")
                .addOption("Contact Call Center")
                .addOption("At Serendib Branch")
                .build()
                .promptUser(scanner);
        final VerificationStrategy verificationStrategy = VerificationStrategy.values()[verifyOption - 1];
        verificationStrategy.verify(account);

        String username;
        while (true) {
            username = new StringInput("Enter username", new UsernameValidator()).promptUser(scanner);
            if (!customerService.isProfileExists(username))
                break;
            System.out.println("Username already exists");
        }

        final var password = new PasswordInput("Enter password", new PasswordValidator()).promptUser(scanner);
        final var displayName = new StringInput("Enter display name").promptUser(scanner);
        customerService.saveProfile(new Profile(account, username, password, displayName));

        System.out.println("\nAccount '" + username + "' created for CASA account '" + account.getAccountNumber() +
                "'");
        logger.info("Account '" + username + "' created for CASA account '" + account.getAccountNumber() + "'");

        new LoginService(scanner).login();
    }
}
