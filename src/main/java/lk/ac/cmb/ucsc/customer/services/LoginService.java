package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.customer.exceptions.AccountLockedException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidPasswordException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidUsernameException;
import lk.ac.cmb.ucsc.customer.exceptions.ProfileLockedException;
import lk.ac.cmb.ucsc.customer.services.impl.CustomerServiceImpl;
import lk.ac.cmb.ucsc.customer.validator.PasswordValidator;
import lk.ac.cmb.ucsc.notification.services.*;
import lk.ac.cmb.ucsc.utils.input.IntegerInput;
import lk.ac.cmb.ucsc.utils.input.PasswordInput;
import lk.ac.cmb.ucsc.utils.input.StringInput;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;
import lk.ac.cmb.ucsc.utils.selection.MenuSelectionBuilder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class LoginService {
    final private static Logger logger = FileLogger.getLogger();

    final CustomerService customerService;
    final Scanner scanner;

    public LoginService(Scanner scanner) {
        this.scanner = scanner;
        this.customerService = CustomerServiceImpl.INSTANCE;
    }

    public void login() {
        final var username = new StringInput("Enter username").promptUser(scanner);

        Profile profile;

        while (true) {
            try {
                profile = customerService.login(username, new PasswordInput("Enter password").promptUser(scanner));
                break;
            } catch (InvalidUsernameException e) {
                System.out.println(e.getMessage());
                logger.info("Login failed for '" + username + "': " + e.getMessage());
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
                logger.info("Login failed for '" + username + "': " + e.getMessage());

                final var opt = new MenuSelectionBuilder()
                        .setTitle("Select an option")
                        .addOption("Reset Password")
                        .addOption("Try Again")
                        .build()
                        .promptUser(scanner);

                if (opt == 2) continue;
                if (isOtpFailed(customerService.getProfile(username).getAccount()))
                    return;
                passwordReset(username);
                return;

            } catch (ProfileLockedException e) {
                System.out.println(e.getMessage());
                logger.info("Login failed for '" + username + "': " + e.getMessage());
                return;
            }
        }

        if (isOtpFailed(profile.getAccount()))
            return;

        logger.info("'" + username + "' logged in successfully");

        showDashboard(profile);

        System.out.println("\n\nPress enter to continue...");
        scanner.nextLine();
    }

    public void passwordReset(String username) {
        final var password = new PasswordInput("Enter new password", new PasswordValidator()).promptUser(scanner);
        customerService.updatePassword(username, password);
    }

    private boolean isOtpFailed(CASAAccount account) {
        final var menuSelectionBuilder = new MenuSelectionBuilder().setTitle("Select OTP notification method");
        final var notifyServiceList = new ArrayList<NotificationService>();

        if (!account.getEmail().isEmpty()) {
            menuSelectionBuilder.addOption("Email");
            notifyServiceList.add(new EmailNotificationDecorator(new NotificationServiceImpl()));
        }
        if (!account.getMobileNumber().isEmpty()) {
            menuSelectionBuilder.addOption("SMS");
            notifyServiceList.add(new SMSNotificationDecorator(new NotificationServiceImpl()));
        }
        notifyServiceList.add(new AuthenticatorNotificationDecorator(new NotificationServiceImpl()));

        final var notifyOpt = menuSelectionBuilder.addOption("Authenticator App").build().promptUser(scanner);
        final var notificationService = notifyServiceList.get(notifyOpt - 1);

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
            return true;
        }
        return false;
    }

    public void showDashboard(Profile profile) {
        final var account = profile.getAccount();
        System.out.println("\n\nWelcome " + profile.getDisplayName());
        System.out.println("Dashboard");
        System.out.println("CASA Account: " + account.getAccountNumber());
        System.out.printf("Balance: LKR %.2f%n", account.getBalance());
    }
}
