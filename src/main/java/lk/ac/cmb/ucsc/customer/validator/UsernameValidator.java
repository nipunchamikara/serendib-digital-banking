package lk.ac.cmb.ucsc.customer.validator;

public class UsernameValidator implements Validator<String> {
    public static final String REGEX = "^[a-zA-Z0-9]{5,}$";

    @Override
    public boolean validate(String input) {
        if (!input.matches(REGEX)) {
            System.out.println("Invalid username");
            return false;
        }
        return true;
    }
}
