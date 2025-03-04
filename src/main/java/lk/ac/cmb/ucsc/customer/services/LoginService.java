package lk.ac.cmb.ucsc.customer.services;

import lk.ac.cmb.ucsc.customer.dtos.Profile;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidPasswordException;
import lk.ac.cmb.ucsc.customer.exceptions.InvalidUsernameException;
import lk.ac.cmb.ucsc.customer.exceptions.ProfileLockedException;
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

    public LoginService() {
        this.customerService = CustomerServiceImpl.INSTANCE;
    }

    public void login(Scanner scanner) {
        final var username = new StringInput("Enter username").promptUser(scanner);
        final var password = new PasswordInput("Enter password").promptUser(scanner);

        Profile profile;
        try {
            profile = customerService.login(username, password);
        } catch (InvalidUsernameException | InvalidPasswordException | ProfileLockedException e) {
            System.out.println(e.getMessage());
            logger.info("Login failed for '" + username + "': " + e.getMessage());
            return;
        }

        final var menuSelectionBuilder = new MenuSelectionBuilder().setTitle("Select OTP notification method");
        final var notifyServiceList = new ArrayList<NotificationService>();

        if (!profile.getAccount().email().isEmpty()) {
            menuSelectionBuilder.addOption("Email");
            notifyServiceList.add(new EmailNotificationDecorator(new NotificationServiceImpl()));
        }
        if (!profile.getAccount().mobileNumber().isEmpty()) {
            menuSelectionBuilder.addOption("SMS");
            notifyServiceList.add(new SMSNotificationDecorator(new NotificationServiceImpl()));
        }
        notifyServiceList.add(new AuthenticatorNotificationDecorator(new NotificationServiceImpl()));

        final var notifyOpt = menuSelectionBuilder.addOption("Authenticator App").build().promptUser(scanner);

        final var notificationService = notifyServiceList.get(notifyOpt - 1);

        customerService.sendOtp(profile.getAccount(), notificationService);

        if (customerService.isOtpInvalid(profile.getAccount(), new IntegerInput("Enter OTP").promptUser(scanner))) {
            System.out.println("Invalid OTP");
            return;
        }

        logger.info("'" + username + "' logged in successfully");

        showDashboard(profile);

        System.out.println("\n\nPress enter to continue...");
        scanner.nextLine();
    }

    public void showDashboard(Profile profile) {
        final var account = profile.getAccount();
        System.out.println("\n\nWelcome " + profile.getDisplayName());
        System.out.println("Dashboard");
        System.out.println("CASA Account: " + account.accountNumber());
        System.out.printf("Balance: LKR %.2f%n", account.balance());
    }
}
