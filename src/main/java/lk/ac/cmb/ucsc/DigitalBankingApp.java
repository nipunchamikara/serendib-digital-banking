package lk.ac.cmb.ucsc;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.services.CustomerService;
import lk.ac.cmb.ucsc.customer.services.LoginService;
import lk.ac.cmb.ucsc.customer.services.OnboardingService;
import lk.ac.cmb.ucsc.customer.services.impl.CustomerServiceImpl;
import lk.ac.cmb.ucsc.utils.gen.RandomDataGenerator;
import lk.ac.cmb.ucsc.utils.logging.FileLogger;
import lk.ac.cmb.ucsc.utils.selection.MenuSelectionBuilder;

import java.util.Scanner;
import java.util.logging.Logger;

public class DigitalBankingApp {
    private static final Logger logger = FileLogger.getLogger();
    private static final int ACCOUNT_COUNT = 10;

    private static void app() {
        final var scanner = new Scanner(System.in);
        scanner.useDelimiter("[\r\n]+");

        final var onboardingService = new OnboardingService(scanner);
        final var loginService = new LoginService(scanner);

        do {
            final var option = new MenuSelectionBuilder()
                    .setTitle("Serendib Digital Banking")
                    .addOption("Customer Onboarding")
                    .addOption("Account Login")
                    .addOption("Exit")
                    .build()
                    .promptUser(scanner);

            switch (option) {
                case 1:
                    onboardingService.onboardCustomer();
                    break;
                case 2:
                    loginService.login();
                    break;
                case 3:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        } while (true);
    }

    private static void generateCASAAccounts() {
        final CustomerService customerService = CustomerServiceImpl.INSTANCE;
        final var gen = new RandomDataGenerator();

        for (int i = 0; i < ACCOUNT_COUNT; i++) {
            final var nic = gen.generateOptional(gen::generateNIC);
            final var phoneNumber = gen.generateOptional(gen::generatePhoneNumber);
            final var account = new CASAAccount(
                    nic,
                    gen.generateOptional(gen::generatePassportNumber, nic.isEmpty()),
                    gen.generateAccountNumber(),
                    gen.generateBalance(),
                    phoneNumber,
                    gen.generateOptional(gen::generateEmail, phoneNumber.isEmpty())
            );
            customerService.saveAccount(account);
            logger.info(String.format("Created account %s", account));
        }
    }

    public static void main(String[] args) {
        generateCASAAccounts();
        app();
    }
}
