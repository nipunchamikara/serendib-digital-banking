package lk.ac.cmb.ucsc.utils.gen;

import java.util.Random;
import java.util.function.Supplier;

public class RandomDataGenerator {
    private final Random random = new Random();

    public String generateOldNIC() {
        final var year = random.nextInt(100);
        final var days = random.nextInt(365) + (random.nextBoolean() ? 0 : 500);
        final var serial = random.nextInt(1000);
        final var check = random.nextInt(10);
        final var suffix = random.nextBoolean() ? "V" : "X";
        return String.format("%02d%03d%03d%01d%s", year, days, serial, check, suffix);
    }

    public String generateNewNIC() {
        final var year = 1950 + random.nextInt(100);
        final var days = random.nextInt(365) + (random.nextBoolean() ? 0 : 500);
        final var serial = random.nextInt(10000);
        final var check = random.nextInt(10);
        return String.format("%04d%03d%04d%01d", year, days, serial, check);
    }

    public String generateNIC() {
        return random.nextBoolean() ? generateOldNIC() : generateNewNIC();
    }

    public String generatePhoneNumber() {
        return String.format("+94%09d", random.nextInt(1000000000));
    }

    public String generatePassportNumber() {
        final var serial = random.nextInt(10000000);
        return String.format("N%07d", serial);
    }

    public double generateBalance() {
        return random.nextDouble() * 1000000;
    }

    public String generateAccountNumber() {
        final var prefix = random.nextInt(4);
        final var serial = random.nextInt(100000000);
        return String.format("%d%08d", prefix, serial);
    }

    public String generateEmail() {
        final var length = random.nextInt(5) + 5;
        final var domain = random.nextBoolean() ? "gmail.com" : "yahoo.com";
        return String.format("%s@%s", generateRandomString(length), domain);
    }

    private String generateRandomString(int length) {
        final var sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        return sb.toString();
    }

    public String generateOptional(Supplier<String> supplier) {
        return random.nextBoolean() ? supplier.get() : "";
    }

    public String generateOptional(Supplier<String> supplier, boolean isRequired) {
        return isRequired ? supplier.get() : generateOptional(supplier);
    }
}
