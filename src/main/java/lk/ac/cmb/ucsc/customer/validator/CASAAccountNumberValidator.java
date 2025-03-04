package lk.ac.cmb.ucsc.customer.validator;

public class CASAAccountNumberValidator implements Validator<String> {
    public static final String REGEX = "^[0-3]\\d{8}$";

    @Override
    public boolean validate(String input) {
        if (!(input.matches(REGEX))) {
            System.out.println("Invalid CASA account number");
            return false;
        }
        return true;
    }
}
