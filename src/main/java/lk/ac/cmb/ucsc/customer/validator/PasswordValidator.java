package lk.ac.cmb.ucsc.customer.validator;

public class PasswordValidator implements Validator<String> {

    @Override
    public boolean validate(String input) {
        if (input.length() < 8) {
            System.out.println("Password must be at least 8 characters long");
            return false;
        }
        if (!input.matches(".*[0-9].*")) {
            System.out.println("Password must contain at least one digit");
            return false;
        }
        if (!input.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter");
            return false;
        }
        if (!input.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter");
            return false;
        }
        return true;
    }
}
