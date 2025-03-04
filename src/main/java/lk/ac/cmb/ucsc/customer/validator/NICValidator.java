package lk.ac.cmb.ucsc.customer.validator;

public class NICValidator implements Validator<String> {
    public static final String REGEX = "^((\\d{2}([0-3]|[5-8])\\d{6}[vVxX])|([12]\\d{3})([0-3]|[5-8])\\d{7})$";

    @Override
    public boolean validate(String input) {
        if (!input.matches(REGEX)) {
            System.out.println("Invalid NIC number");
            return false;
        }
        return true;
    }
}
